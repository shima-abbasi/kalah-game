package shima.backbase.kalah.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shima.backbase.kalah.model.Pit;

@Repository
public interface PitRepo extends JpaRepository<Pit,Long> {
}
