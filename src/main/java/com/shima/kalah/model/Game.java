package com.shima.kalah.model;

import com.shima.kalah.enums.GameStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_GAME")
public class Game extends Basic {
    private int gameId;
    private List<Player> players;
    private Board board;
    private GameStatus gameStatus;

    public Game() {
    }

    public Game(int gameId, GameStatus gameStatus) {
        this.gameId = gameId;
        this.gameStatus = gameStatus;
    }

    @Column(name = "C_GAME_ID")
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @OneToOne
    @JoinColumn(name = "FK_BOARD")
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Column(name = "C_GAME_STATUS")
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
