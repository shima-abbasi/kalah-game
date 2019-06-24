package com.shima.kalah.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shima.kalah.model.Board;

@Repository
public interface BoardRepo extends JpaRepository<Board,Long> {
}
