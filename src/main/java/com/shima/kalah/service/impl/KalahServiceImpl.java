package com.shima.kalah.service.impl;

import com.shima.kalah.service.GameService;
import com.shima.kalah.service.KalahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
