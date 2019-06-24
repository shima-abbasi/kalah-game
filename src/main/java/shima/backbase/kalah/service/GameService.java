package shima.backbase.kalah.service;

import shima.backbase.kalah.model.Game;
import shima.backbase.kalah.model.Player;

public interface GameService {
    Game createGame();

    Object move(int gameId, int pitId);

    Boolean checkEndOfGame(Player currentPlayer, Player opponentPlayer);
}
