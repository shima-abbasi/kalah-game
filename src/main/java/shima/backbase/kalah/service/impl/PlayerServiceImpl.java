package shima.backbase.kalah.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shima.backbase.kalah.enums.PlayerState;
import shima.backbase.kalah.model.Game;
import shima.backbase.kalah.model.Pit;
import shima.backbase.kalah.model.Player;
import shima.backbase.kalah.repository.PlayerRepo;
import shima.backbase.kalah.service.PitService;
import shima.backbase.kalah.service.PlayerService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private PitService pitService;

    @Override
    public List<Player> initiatePlayers(Game game) {
        List<Player> players = new ArrayList<>();
        Player currentPlayer = new Player(game, PlayerState.TURN);
        Player opponentPlayer = new Player(game, PlayerState.WAITING);
        players.add(currentPlayer);
        players.add(opponentPlayer);
        playerRepo.saveAll(players);
        return players;
    }

    @Override
    public void sortPlayers(List<Player> players) {
        players.sort(Comparator.comparingInt(p -> p.getPlayerState().getValue()));
    }

    @Override
    public void changeTurn(Player currentPlayer, Player opponentPlayer) {
        List<Player> players = new ArrayList<>();
        currentPlayer.setPlayerState(PlayerState.WAITING);
        opponentPlayer.setPlayerState(PlayerState.TURN);
        players.add(currentPlayer);
        players.add(opponentPlayer);
        playerRepo.saveAll(players);
    }

    @Override
    public void changeState(Player player, PlayerState playerState) {
        player.setPlayerState(playerState);
        playerRepo.save(player);
    }

    @Override
    public void calculateWinner(Player currentPlayer, Player opponentPlayer) {
        pitService.fillKalahWithAllRemainedStones(currentPlayer);
        pitService.fillKalahWithAllRemainedStones(opponentPlayer);
        Pit kalahPitPlayer1 = pitService.findPlayerKalahPit(currentPlayer);
        Pit kalahPitPlayer2 = pitService.findPlayerKalahPit(opponentPlayer);

        if (kalahPitPlayer2.getNumberOfStone() > kalahPitPlayer1.getNumberOfStone()) {
            changeState(currentPlayer, PlayerState.EQUAL);
            changeState(opponentPlayer, PlayerState.EQUAL);
        }
        if (kalahPitPlayer1.getNumberOfStone() > kalahPitPlayer2.getNumberOfStone()) {
            changeState(currentPlayer, PlayerState.WINNER);
            changeState(opponentPlayer, PlayerState.LOOSER);
        }
        if (kalahPitPlayer2.getNumberOfStone() > kalahPitPlayer1.getNumberOfStone()) {
            changeState(currentPlayer, PlayerState.LOOSER);
            changeState(opponentPlayer, PlayerState.WINNER);
        }
    }
}
