package com.nakedquasar.gamecenter.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nakedquasar.gamecenter.core.domain.Player;

public interface PlayersRepository extends JpaRepository<Player, UUID> {
	public Player findByPlayerId(UUID playerId);
	
	@Query("SELECT ps FROM Player ps WHERE ps.playerUsername =:username AND ps.playerPassword =:password")
	public Player findByPlayerUsernamePass(@Param("username")String username, @Param("password") String password);
	
	@Query("SELECT ps FROM Player ps WHERE ps.playerUsername =:username")
	public Player findByPlayerUsername(@Param("username")String username);
	
	@Query(value = "select * from player p where p.playerid in(select distinct playerid from playerscore where leaderboardid in (select leaderboardid from leaderboard where gameid in (select gameid from game where gamename=?1)))", nativeQuery = true)
	public List<Player> getPlayersByGameName( String gameName);
	
	@Query(value = "select count(*) from player p where p.playerid in(select distinct playerid from playerscore where leaderboardid in (select leaderboardid from leaderboard where gameid in (select gameid from game where gamename=?1)))", nativeQuery = true)
	public int getPlayersCountByGameName( String gameName);
	
	@Query(value = "select count(*) from player where playerrole not in ('ROLE_ADMIN')", nativeQuery = true)
	public int getPlayersCount();
	
	@Query("SELECT pl FROM Player pl WHERE pl.playerRole != 'ROLE_ADMIN' ORDER BY pl.playerUsername ASC)")
	public Page<Player> findAllPlayers(Pageable page);
	
	@Query("SELECT pl FROM Player pl WHERE pl.playerRole != 'ROLE_ADMIN' AND pl.playerUsername LIKE CONCAT('%',:username,'%')")
	public Page<Player> findAllPlayers(Pageable page, @Param("username") String username);
}