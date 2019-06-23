package shima.backbase.kalah.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shima.backbase.kalah.model.Game;

@Repository
public interface GameRepo extends JpaRepository<Game,Long> {
    @Query("select game from Game game where game.gameId =:gameId")
    Game findGameByGameId(@Param("gameId") int gameId);
}
