package shima.backbase.kalah.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shima.backbase.kalah.enums.GameStatus;
import shima.backbase.kalah.exception.KalahException;
import shima.backbase.kalah.model.Board;
import shima.backbase.kalah.model.Game;
import shima.backbase.kalah.model.Pit;
import shima.backbase.kalah.model.Player;
import shima.backbase.kalah.repository.GameRepo;
import shima.backbase.kalah.service.BoardService;
import shima.backbase.kalah.service.GameService;
import shima.backbase.kalah.service.PitService;
import shima.backbase.kalah.service.PlayerService;

import java.util.List;
import java.util.Random;

@Service
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
        List<Pit> pits = pitService.initiatePits(players, board);
        game.setBoard(board);
        return gameRepo.save(game);
    }

    @Override
    public Object move(int gameId, int pitId) {
        String gameResult;
        Game game = checkGameAvailability(gameId);

        pitService.checkPitValidity(pitId);

        List<Player> players = game.getPlayers();
        playerService.sortPlayers(players);
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        Board board = game.getBoard();
        List<Pit> pits = board.getPits();
        Pit currentPit = pits.get(pitId - 1);

        pitService.checkPitAvailability(currentPit, player1);

        pits = pitService.moveStone(player1, player2, pits, currentPit);

        gameResult = " 1:" + pits.get(0) + " , 2: " + pits.get(1) + ", 3:" + pits.get(2) + ", 4:" + pits.get(3) + " , 5:" + pits.get(4) + " , 6:" + pits.get(5) + " , 7:" + pits.get(6) + " , 8:" + pits.get(7) + " , 9:" + pits.get(8) + " , 10:" + pits.get(9) + " , 11:" + pits.get(10) + " , 12:" + pits.get(11) + " , 13:" + pits.get(12) + " , 14:" + pits.get(13);
        return gameResult;
    }

    private Game initiateGame() {
        Game game = new Game(Math.abs(new Random().nextInt()), GameStatus.CREATED);
        game = gameRepo.save(game);
        return game;
    }

    private Game checkGameAvailability(Integer gameId) {
        Game game = gameRepo.findGameByGameId(gameId);
        if (game == null) {
            throw new KalahException("Game_Not_Exist");
        } else if (game.getGameStatus() == GameStatus.ENDED) {
            throw new KalahException("Game_Ended");
        }
        return game;
    }

    @Override
    public Boolean checkEndOfGame(Player player1, Player player2) {
        List<Pit> pitsOfPlayer1 = player1.getPits();
        List<Pit> pitsOfPlayer2 = player2.getPits();
        Boolean gameEnded = Boolean.TRUE;
        for (Pit pit : pitsOfPlayer1) {
            if (pit.getNumberOfStone() != 0) {
                gameEnded = Boolean.FALSE;
                break;
            }
        }
        for (Pit pit : pitsOfPlayer2) {
            if (pit.getNumberOfStone() != 0) {
                gameEnded = Boolean.FALSE;
                break;
            }
        }
        return gameEnded;
    }
}
