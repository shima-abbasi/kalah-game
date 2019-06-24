package com.shima.kalah.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.shima.kalah.model.Game;

@Repository
public interface GameRepo extends JpaRepository<Game,Long> {
    @Query("select game from Game game where game.gameId =:gameId")
    Game findGameByGameId(@Param("gameId") int gameId);
}
