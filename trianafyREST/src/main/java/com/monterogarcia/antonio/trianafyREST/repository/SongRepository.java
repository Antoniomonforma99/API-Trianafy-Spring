package com.monterogarcia.antonio.trianafyREST.repository;

import com.monterogarcia.antonio.trianafyREST.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
