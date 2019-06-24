package com.shima.kalah.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shima.kalah.model.Player;

@Repository
public interface PlayerRepo extends JpaRepository<Player,Long> {
}
