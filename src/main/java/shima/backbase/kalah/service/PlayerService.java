package shima.backbase.kalah.service;

import shima.backbase.kalah.enums.PlayerState;
import shima.backbase.kalah.model.Game;
import shima.backbase.kalah.model.Player;

import java.util.List;

public interface PlayerService {
    List<Player> initiatePlayers(Game game);

    void sortPlayers(List<Player> players);

    void changeTurn(Player currentPlayer, Player opponentPlayer);

    void changeState(Player player, PlayerState playerState);

    void calculateWinner(Player currentPlayer, Player opponentPlayer);
}

