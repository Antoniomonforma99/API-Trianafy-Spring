package com.monterogarcia.antonio.trianafyREST.services;

import com.monterogarcia.antonio.trianafyREST.models.Artist;
import com.monterogarcia.antonio.trianafyREST.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository repository;

    public Artist add(Artist a) {
        return repository.save(a);
    }

    public List<Artist> findAll(){
        return repository.findAll();
    }

    public Optional<Artist> findById(Long id) {
        return repository.findById(id);
    }

    public Artist update(Artist a) {
        return repository.save(a);
    }

    public void deletebyId(Long id) {
        repository.deleteById(id);
    }

    public void delete(Artist a) {
        repository.delete(a);
    }
}
