package com.shima.kalah.service;

import com.shima.kalah.enums.PlayerState;
import com.shima.kalah.model.Game;
import com.shima.kalah.model.Player;

import java.util.List;

public interface PlayerService {
    List<Player> initiatePlayers(Game game);

    void sortPlayers(List<Player> players);

    void changeTurn(Player currentPlayer, Player opponentPlayer);

    void changeState(Player player, PlayerState playerState);

    void calculateWinner(Player currentPlayer, Player opponentPlayer);
}

