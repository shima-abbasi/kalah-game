package shima.backbase.kalah.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shima.backbase.kalah.service.GameService;
import shima.backbase.kalah.service.KalahService;

@Service
@Transactional
public class KalahServiceImpl implements KalahService {

    @Autowired
    private GameService gameService;

    @Override
    public Object createGame() {
        return gameService.createGame();
    }

    @Override
    public Object move(int gameId, int pitId) {
        return gameService.move(gameId, pitId);
    }

}
