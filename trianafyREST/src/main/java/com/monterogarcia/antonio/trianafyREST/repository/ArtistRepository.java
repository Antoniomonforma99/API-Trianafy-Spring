package com.monterogarcia.antonio.trianafyREST.repository;

import com.monterogarcia.antonio.trianafyREST.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
