package com.shima.kalah.model;

import javax.persistence.*;

@Entity
@Table(name = "T_PIT")
public class Pit extends Basic {
    private Integer pitId;
    private Player player;
    private Board board;
    private Integer numberOfStone;
    private Boolean isKalahPit;

    @Column(name = "C_PIT_ID")
    public Integer getPitId() {
        return pitId;
    }

    public void setPitId(Integer pitId) {
        this.pitId = pitId;
    }

    @ManyToOne
    @JoinColumn(name = "FK_PLAYER")
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @ManyToOne
    @JoinColumn(name = "FK_BOARD")
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Column(name = "C_NUMBER_OF_STONE")
    public Integer getNumberOfStone() {
        return numberOfStone;
    }

    public void setNumberOfStone(Integer numberOfStone) {
        this.numberOfStone = numberOfStone;
    }

    @Column(name = "C_KALAH_PIT")
    public Boolean getKalahPit() {
        return isKalahPit;
    }

    public void setKalahPit(Boolean kalahPit) {
        isKalahPit = kalahPit;
    }
}
