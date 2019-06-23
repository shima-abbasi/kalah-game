package shima.backbase.kalah.service;

import shima.backbase.kalah.model.Board;
import shima.backbase.kalah.model.Pit;
import shima.backbase.kalah.model.Player;

import java.util.List;

public interface PitService {

    List<Pit> initiatePits(List<Player> players, Board board);

    void checkPitValidity(Integer pitId);

    void checkPitAvailability(Pit pit, Player player);

    List<Pit> moveStone(Player player1, Player player2, List<Pit> pits, Pit currentPit);
}
