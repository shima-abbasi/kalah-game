package com.shima.kalah.model;

import com.shima.kalah.enums.PlayerState;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_PLAYER")
public class Player extends Basic {
    private Game game;
    private List<Pit> pits;
    private PlayerState playerState;

    public Player() {
    }

    public Player(Game game, PlayerState playerState) {
        this.game = game;
        this.playerState = playerState;
    }

    @ManyToOne
    @JoinColumn(name = "FK_GAME")
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    public List<Pit> getPits() {
        return pits;
    }

    public void setPits(List<Pit> pits) {
        this.pits = pits;
    }

    @Column(name = "C_PLAYER_STATE")
    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }
}
