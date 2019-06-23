package shima.backbase.kalah.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shima.backbase.kalah.enums.PlayerState;
import shima.backbase.kalah.model.Game;
import shima.backbase.kalah.model.Player;
import shima.backbase.kalah.repository.PlayerRepo;
import shima.backbase.kalah.service.PlayerService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepo playerRepo;

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
    public List<Player> sortPlayers(List<Player> players) {
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p1.getPlayerState().getValue(), p2.getPlayerState().getValue());
            }
        });
        return players;
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
}
