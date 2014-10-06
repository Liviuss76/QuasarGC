package com.nakedquasar.gamecenter.persistence.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nakedquasar.gamecenter.core.domain.Game;

public interface GamesRepository extends JpaRepository<Game, UUID> {
	public Game findByGameName(String gameName);
	
	@Query("SELECT g FROM Game g ORDER BY g.gameCreationDate ASC)")
	public Page<Game> findAllGames(Pageable page);
	
	@Query(value = "select count(*) from game", nativeQuery = true)
	public int getGamesCount();
}
