package com.shima.kalah.service;

import com.shima.kalah.model.Board;
import com.shima.kalah.model.Pit;
import com.shima.kalah.model.Player;

import java.util.List;

public interface PitService {

    void initiatePits(List<Player> players, Board board);

    void checkPitValidity(int pitId);

    void checkPitAvailability(Pit pit, Player player);

    List<Pit> moveStone(Player currentPlayer, Player opponentPlayer, List<Pit> pits, Pit currentPit);

    Pit findPlayerKalahPit(Player player);

    Boolean playerHasStone(Player player);

    void fillKalahWithAllRemainedStones(Player player);
}
