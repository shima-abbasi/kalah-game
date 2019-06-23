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
        Player player1 = new Player(game, PlayerState.TURN);
        Player player2 = new Player(game, PlayerState.WAITING);
        players.add(player1);
        players.add(player2);
        playerRepo.saveAll(players);
        return players;
    }

    @Override
    public void sortPlayers(List<Player> players) {
        players.sort(Comparator.comparingInt(p -> p.getPlayerState().getValue()));
    }

    @Override
    public void changeTurn(Player player1, Player player2) {
        List<Player> players = new ArrayList<>();
        player1.setPlayerState(PlayerState.WAITING);
        player2.setPlayerState(PlayerState.TURN);
        players.add(player1);
        players.add(player2);
        playerRepo.saveAll(players);
    }

    @Override
    public void changeState(Player player, PlayerState playerState) {
        player.setPlayerState(playerState);
        playerRepo.save(player);
    }

    @Override
    public void calculateWinner(Player player1, Player player2) {
        pitService.fillKalahWithAllRemainedStones(player1);
        pitService.fillKalahWithAllRemainedStones(player2);
        Pit kalahPitPlayer1 = pitService.findPlayerKalahPit(player1);
        Pit kalahPitPlayer2 = pitService.findPlayerKalahPit(player2);

        if (kalahPitPlayer2.getNumberOfStone() > kalahPitPlayer1.getNumberOfStone()) {
            changeState(player1, PlayerState.EQUAL);
            changeState(player2, PlayerState.EQUAL);
        }
        if (kalahPitPlayer1.getNumberOfStone() > kalahPitPlayer2.getNumberOfStone()) {
            changeState(player1, PlayerState.WINNER);
            changeState(player2, PlayerState.LOOSER);
        }
        if (kalahPitPlayer2.getNumberOfStone() > kalahPitPlayer1.getNumberOfStone()) {
            changeState(player1, PlayerState.LOOSER);
            changeState(player2, PlayerState.WINNER);
        }
    }
}
