package com.shima.kalah.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shima.kalah.model.Pit;

@Repository
public interface PitRepo extends JpaRepository<Pit,Long> {
}
