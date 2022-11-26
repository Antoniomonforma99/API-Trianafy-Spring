package com.monterogarcia.antonio.trianafyREST.repository;

import com.monterogarcia.antonio.trianafyREST.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
