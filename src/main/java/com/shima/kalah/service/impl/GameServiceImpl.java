package com.shima.kalah.service.impl;

import com.shima.kalah.model.Board;
import com.shima.kalah.service.BoardService;
import com.shima.kalah.service.PitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shima.kalah.enums.GameStatus;
import com.shima.kalah.exception.KalahException;
import com.shima.kalah.model.Game;
import com.shima.kalah.model.Pit;
import com.shima.kalah.model.Player;
import com.shima.kalah.repository.GameRepo;
import com.shima.kalah.service.GameService;
import com.shima.kalah.service.PlayerService;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private PitService pitService;
    @Autowired
    private GameRepo gameRepo;


    @Override
    @Transactional
    public Game createGame() {
        Game game = initiateGame();
        List<Player> players = playerService.initiatePlayers(game);
        Board board = boardService.initiateBoard();
        pitService.initiatePits(players, board);
        game.setBoard(board);
        return gameRepo.save(game);
    }

    @Override
    public Object move(int gameId, int pitId) {
        Game game = checkGameAvailability(gameId);

        pitService.checkPitValidity(pitId);

        List<Player> players = game.getPlayers();
        playerService.sortPlayers(players);
        Player currentPlayer = players.get(0);
        Player opponentPlayer = players.get(1);

        Board board = game.getBoard();
        List<Pit> pits = board.getPits();
        Pit currentPit = pits.get(pitId - 1);

        pitService.checkPitAvailability(currentPit, currentPlayer);

        pits = pitService.moveStone(currentPlayer, opponentPlayer, pits, currentPit);

        if (checkEndOfGame(currentPlayer, opponentPlayer)) {
            game.setGameStatus(GameStatus.ENDED);
            playerService.calculateWinner(currentPlayer, opponentPlayer);
        }
        return initiateResultString(pits);
    }

    private Game initiateGame() {
        Game game = new Game(Math.abs(new Random().nextInt()), GameStatus.CREATED);
        game = gameRepo.save(game);
        return game;
    }

    private Game checkGameAvailability(int gameId) {
        Game game = gameRepo.findGameByGameId(gameId);
        if (game == null) {
            throw new KalahException("Game_Not_Exist");
        } else if (game.getGameStatus() == GameStatus.ENDED) {
            throw new KalahException("Game_Ended");
        }
        return game;
    }

    @Override
    public Boolean checkEndOfGame(Player currentPlayer, Player opponentPlayer) {
        return (!pitService.playerHasStone(currentPlayer) || !pitService.playerHasStone(opponentPlayer));
    }

    private String initiateResultString(List<Pit> pits) {
        String gameResult = "";
        for (Pit pit : pits) {
            gameResult = gameResult + (pit.getPitId() + 1) + ":" + pit.getNumberOfStone() + " ,";
        }
        return gameResult;
    }
}
