package shima.backbase.kalah.service;

import shima.backbase.kalah.enums.PlayerState;
import shima.backbase.kalah.model.Game;
import shima.backbase.kalah.model.Player;

import java.util.List;

public interface PlayerService {
    List<Player> initiatePlayers(Game game);

    void sortPlayers(List<Player> players);

    void changeTurn(Player player1, Player player2);

    void changeState(Player player, PlayerState playerState);

    void calculateWinner(Player player1, Player player2);
}

