package shima.backbase.kalah.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shima.backbase.kalah.model.Player;

@Repository
public interface PlayerRepo extends JpaRepository<Player,Long> {
}
