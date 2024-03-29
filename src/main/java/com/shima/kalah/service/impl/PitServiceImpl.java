package com.shima.kalah.service.impl;

import com.shima.kalah.exception.KalahException;
import com.shima.kalah.model.Board;
import com.shima.kalah.repository.PitRepo;
import com.shima.kalah.service.PitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shima.kalah.model.Pit;
import com.shima.kalah.model.Player;
import com.shima.kalah.service.PlayerService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PitServiceImpl implements PitService {
    private final static int First_PIT_INDEX_P1 = 0;
    private final static int LAST_PIT_INDEX_P1 = 6;
    private final static int First_PIT_INDEX_P2 = 7;
    private final static int LAST_PIT_INDEX_P2 = 13;
    private final static int NUMBER_OF_STONES = 6;
    private final static int MINIMUM_NUMBER_OF_STONE = 0;


    @Autowired
    private PitRepo pitRepo;
    @Autowired
    private PlayerService playerService;

    @Override
    public void initiatePits(List<Player> players, Board board) {
        List<Pit> pits = new ArrayList<>();
        Player currentPlayer = players.get(0);
        Player opponentPlayer = players.get(1);
        pits.addAll(initiatePits(currentPlayer, First_PIT_INDEX_P1, LAST_PIT_INDEX_P1, board));
        pits.addAll(initiatePits(opponentPlayer, First_PIT_INDEX_P2, LAST_PIT_INDEX_P2, board));
        pitRepo.saveAll(pits);
    }

    private List<Pit> initiatePits(Player player, int firstIndex, int lastIndex, Board board) {
        List<Pit> pits = new ArrayList<>();
        for (int index = firstIndex; index <= lastIndex; index++) {
            Pit pit = new Pit();
            pit.setPitId(index);
            pit.setBoard(board);
            if (index != lastIndex) {
                pit.setNumberOfStone(NUMBER_OF_STONES);
                pit.setKalahPit(false);
            } else {
                pit.setNumberOfStone(MINIMUM_NUMBER_OF_STONE);
                pit.setKalahPit(true);
            }
            pit.setPlayer(player);
            pits.add(pit);
        }
        return pits;
    }

    @Override
    public void checkPitValidity(int pitId) {
        if (pitId > LAST_PIT_INDEX_P2 || pitId < First_PIT_INDEX_P1) {
            throw new KalahException("Pit_Out_Of_Range");
        }
    }

    @Override
    public void checkPitAvailability(Pit pit, Player player) {
        if (pit.getKalahPit()) {
            throw new KalahException("Kalah_pit");
        } else if (pit.getNumberOfStone().equals(MINIMUM_NUMBER_OF_STONE)) {
            throw new KalahException("Pit_Is_Empty");
        } else if (pit.getPlayer() != player) {
            throw new KalahException("Opponent_Pit");
        }
    }

    @Override
    public List<Pit> moveStone(Player currentPlayer, Player opponentPlayer, List<Pit> pits, Pit currentPit) {
        int numberOfStonesOfPickedPit = currentPit.getNumberOfStone();
        changeNumberOfStone(currentPit, MINIMUM_NUMBER_OF_STONE);
        Boolean emptyPitRule;

        while (numberOfStonesOfPickedPit != MINIMUM_NUMBER_OF_STONE) {
            Pit nextPit = getNextPit(pits, currentPit);

            emptyPitRule = checkEmptyPitRule(numberOfStonesOfPickedPit, nextPit, currentPlayer);

            if (nextPit.getPlayer() == currentPlayer || (nextPit.getPlayer() == opponentPlayer && nextPit.getKalahPit() == Boolean.FALSE)) {
                changeNumberOfStone(nextPit, nextPit.getNumberOfStone() + 1);
                numberOfStonesOfPickedPit--;
            }

            if (emptyPitRule) {
                emptyPitRuleAction(currentPlayer, pits, nextPit);
            }
            currentPit = nextPit;
        }
        if (!getBonusTurn(currentPit, currentPlayer)) {
            playerService.changeTurn(currentPlayer, opponentPlayer);
        }
        pitRepo.saveAll(pits);
        return pits;
    }

    private Pit getNextPit(List<Pit> pits, Pit currentPit) {
        Pit nextPit;
        if (currentPit.getPitId() == LAST_PIT_INDEX_P2) {
            nextPit = pits.get(First_PIT_INDEX_P1);
        } else {
            nextPit = pits.get(currentPit.getPitId() + 1);
        }
        return nextPit;
    }

    private void changeNumberOfStone(Pit pit, int numberOfStones) {
        pit.setNumberOfStone(numberOfStones);
        pitRepo.save(pit);
    }

    private boolean checkEmptyPitRule(int numberOfStonesOfPickedPit, Pit nextPit, Player currentPlayer) {
        return (numberOfStonesOfPickedPit == 1 && nextPit.getPlayer() == currentPlayer && nextPit.getNumberOfStone() == MINIMUM_NUMBER_OF_STONE && !nextPit.getKalahPit());
    }

    private boolean getBonusTurn(Pit nextPit, Player currentPlayer) {
        return (nextPit.getPlayer() == currentPlayer && nextPit.getKalahPit() == Boolean.TRUE);
    }

    private void emptyPitRuleAction(Player player, List<Pit> pits, Pit pit) {
        Pit kalahPit = findPlayerKalahPit(player);
        Pit oppositePit = pits.get(LAST_PIT_INDEX_P2 - pit.getPitId() - 1);
        changeNumberOfStone(kalahPit, pit.getNumberOfStone() + oppositePit.getNumberOfStone() + kalahPit.getNumberOfStone());
        changeNumberOfStone(oppositePit, MINIMUM_NUMBER_OF_STONE);
        changeNumberOfStone(pit, MINIMUM_NUMBER_OF_STONE);
    }

    @Override
    public Pit findPlayerKalahPit(Player player) {
        Pit kaladPit = null;
        for (Pit pit : player.getPits()) {
            if (pit.getKalahPit() == Boolean.TRUE) {
                kaladPit = pit;
                break;
            }
        }
        return kaladPit;
    }

    @Override
    public Boolean playerHasStone(Player player) {
        List<Pit> pits = player.getPits();
        for (Pit pit : pits) {
            if (pit.getNumberOfStone() != MINIMUM_NUMBER_OF_STONE) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public void fillKalahWithAllRemainedStones(Player player) {
        List<Pit> pits = player.getPits();
        Pit kalahPit = findPlayerKalahPit(player);
        for (Pit pit : pits) {
            if (pit.getNumberOfStone() != MINIMUM_NUMBER_OF_STONE) {
                changeNumberOfStone(kalahPit, kalahPit.getNumberOfStone() + pit.getNumberOfStone());
                changeNumberOfStone(pit, MINIMUM_NUMBER_OF_STONE);
            }
        }
        pitRepo.saveAll(pits);
    }
}
