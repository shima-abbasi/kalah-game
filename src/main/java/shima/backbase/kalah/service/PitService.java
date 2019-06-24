package shima.backbase.kalah.service;

import shima.backbase.kalah.model.Board;
import shima.backbase.kalah.model.Pit;
import shima.backbase.kalah.model.Player;

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
