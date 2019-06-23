package shima.backbase.kalah.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shima.backbase.kalah.model.Board;

@Repository
public interface BoardRepo extends JpaRepository<Board,Long> {
}
