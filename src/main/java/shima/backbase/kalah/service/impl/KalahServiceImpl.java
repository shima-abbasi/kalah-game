package shima.backbase.kalah.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shima.backbase.kalah.service.GameService;
import shima.backbase.kalah.service.KalahService;

@Service
public class KalahServiceImpl implements KalahService {

    @Autowired
    private GameService gameService;

    @Override
    public Object createGame() {
        return gameService.createGame();
    }

    @Override
    public Object move(int gameId, int pitId){
        return gameService.move(gameId, pitId);
    }

}
