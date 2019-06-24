package com.shima.kalah.service;

public interface KalahService {
    Object createGame();

    Object move(int gameId, int pitId);
}
