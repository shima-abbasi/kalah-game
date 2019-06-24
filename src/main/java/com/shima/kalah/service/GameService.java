package com.shima.kalah.service;

import com.shima.kalah.model.Game;
import com.shima.kalah.model.Player;

public interface GameService {
    Game createGame();

    Object move(int gameId, int pitId);

    Boolean checkEndOfGame(Player currentPlayer, Player opponentPlayer);
}
