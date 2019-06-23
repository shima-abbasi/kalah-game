package shima.backbase.kalah.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shima.backbase.kalah.enums.PlayerState;
import shima.backbase.kalah.exception.KalahException;
import shima.backbase.kalah.model.Board;
import shima.backbase.kalah.model.Pit;
import shima.backbase.kalah.model.Player;
import shima.backbase.kalah.repository.PitRepo;
import shima.backbase.kalah.service.GameService;
import shima.backbase.kalah.service.PitService;
import shima.backbase.kalah.service.PlayerService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PitServiceImpl implements PitService {
    private final static int First_PIT_INDEX_P1 = 1;
    private final static int LAST_PIT_INDEX_P1 = 7;
    private final static int First_PIT_INDEX_P2 = 8;
    private final static int LAST_PIT_INDEX_P2 = 14;
    private final static int NUMBER_OF_STONE = 6;
    private final static int MINIMUM_NUMBER_OF_STONE = 0;


    @Autowired
    private PitRepo pitRepo;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private GameService gameService;

    @Override
    public List<Pit> initiatePits(List<Player> players, Board board) {
        List<Pit> pits = new ArrayList<>();
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        pits.addAll(initiatePits(player1, First_PIT_INDEX_P1, LAST_PIT_INDEX_P1, board));
        pits.addAll(initiatePits(player2, First_PIT_INDEX_P2, LAST_PIT_INDEX_P2, board));
        pitRepo.saveAll(pits);
        return pits;
    }

    private List<Pit> initiatePits(Player player, int firsIndex, int lastIndex, Board board) {
        List<Pit> pits = new ArrayList<>();
        for (int index = firsIndex; index <= lastIndex; index++) {
            Pit pit = new Pit();
            pit.setPitId(index);
            pit.setBoard(board);
            pit.setNumberOfStone(NUMBER_OF_STONE);
            if (index != lastIndex) {
                pit.setKalahPit(false);
            } else {
                pit.setKalahPit(true);
            }
            pit.setPlayer(player);
            pits.add(pit);
        }
        return pits;
    }

    @Override
    public void checkPitValidity(Integer pitId) {
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
    public List<Pit> moveStone(Player player1, Player player2, List<Pit> pits, Pit currentPit) {
        int numberOfStoneOfPickedPit = currentPit.getNumberOfStone();
        changeNumberOfStone(currentPit, MINIMUM_NUMBER_OF_STONE);
        Boolean nextTurn = Boolean.TRUE;
        Boolean emptyPitRule = Boolean.FALSE;

        while (numberOfStoneOfPickedPit != MINIMUM_NUMBER_OF_STONE) {
            Pit nextPit;
            if (currentPit.getPitId() == LAST_PIT_INDEX_P2) {
                nextPit = pits.get(First_PIT_INDEX_P1 - 1);
            } else
                nextPit = pits.get(currentPit.getPitId());
            currentPit = nextPit;
            if (numberOfStoneOfPickedPit == 1 && nextPit.getPlayer() == player1 && nextPit.getNumberOfStone() == MINIMUM_NUMBER_OF_STONE) {
                emptyPitRule = Boolean.TRUE;
            }
            if (nextPit.getPlayer() == player1 || (nextPit.getPlayer() == player2 && nextPit.getKalahPit() == Boolean.FALSE)) {
                nextPit.setNumberOfStone(nextPit.getNumberOfStone() + 1);
                numberOfStoneOfPickedPit--;
            }
            if (numberOfStoneOfPickedPit == MINIMUM_NUMBER_OF_STONE && nextPit.getPlayer() == player1 && nextPit.getKalahPit() == Boolean.TRUE) {
                nextTurn = Boolean.FALSE;
            }
            if (numberOfStoneOfPickedPit == MINIMUM_NUMBER_OF_STONE && nextTurn.equals(Boolean.TRUE)) {
                playerService.changeTurn(player1, player2);
            }
            if (emptyPitRule.equals(Boolean.TRUE)) {
                emptyPitRule(player1, pits, currentPit);
            }
            if (gameService.checkEndOfGame(player1, player2)) {
                calculateWinner(player1, player2);
            }
        }
        pitRepo.saveAll(pits);
        return pits;
    }

    private void changeNumberOfStone(Pit pit, int numberOfStone) {
        pit.setNumberOfStone(numberOfStone);
        pitRepo.save(pit);
    }

    private void emptyPitRule(Player player, List<Pit> pits, Pit pit) {
        Pit kalahPit = findPlayerKaladPit(player);
        Pit oppositePit = pits.get(LAST_PIT_INDEX_P2 - pit.getPitId() - 1);
        changeNumberOfStone(kalahPit, pit.getNumberOfStone() + oppositePit.getNumberOfStone());
        changeNumberOfStone(oppositePit, MINIMUM_NUMBER_OF_STONE);
        changeNumberOfStone(pit, MINIMUM_NUMBER_OF_STONE);
    }

    private Pit findPlayerKaladPit(Player player) {
        Pit kaladPit = null;
        for (Pit pit : player.getPits()) {
            if (pit.getKalahPit() == Boolean.TRUE) {
                kaladPit = pit;
                break;
            }
        }
        return kaladPit;
    }

    private void calculateWinner(Player player1, Player player2) {
        List<Pit> pitsOfPlayer1 = player1.getPits();
        List<Pit> pitsOfPlayer2 = player2.getPits();
        Pit kaladPitPlayer1 = findPlayerKaladPit(player1);
        Pit kaladPitPlayer2 = findPlayerKaladPit(player2);

        for (Pit pit : pitsOfPlayer1) {
            if (pit.getNumberOfStone() != MINIMUM_NUMBER_OF_STONE) {
                changeNumberOfStone(pit, MINIMUM_NUMBER_OF_STONE);
                changeNumberOfStone(kaladPitPlayer1, kaladPitPlayer1.getNumberOfStone() + pit.getNumberOfStone());
            }
        }
        for (Pit pit : pitsOfPlayer2) {
            if (pit.getNumberOfStone() != MINIMUM_NUMBER_OF_STONE) {
                changeNumberOfStone(pit, MINIMUM_NUMBER_OF_STONE);
                changeNumberOfStone(kaladPitPlayer2, kaladPitPlayer2.getNumberOfStone() + pit.getNumberOfStone());
            }
        }
        pitRepo.saveAll(pitsOfPlayer1);
        pitRepo.saveAll(pitsOfPlayer2);

        if (kaladPitPlayer2.getNumberOfStone() > kaladPitPlayer1.getNumberOfStone()) {
            playerService.changeState(player1, PlayerState.EQUAL);
            playerService.changeState(player2, PlayerState.EQUAL);
        }
        if (kaladPitPlayer1.getNumberOfStone() > kaladPitPlayer2.getNumberOfStone()) {
            playerService.changeState(player1, PlayerState.WINNER);
            playerService.changeState(player2, PlayerState.LOOSER);
        }
        if (kaladPitPlayer2.getNumberOfStone() > kaladPitPlayer1.getNumberOfStone()) {
            playerService.changeState(player1, PlayerState.LOOSER);
            playerService.changeState(player2, PlayerState.WINNER);
        }
    }
}
