package com.monterogarcia.antonio.trianafyREST.services;

import com.monterogarcia.antonio.trianafyREST.models.Playlist;
import com.monterogarcia.antonio.trianafyREST.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository repository;

    public Playlist add(Playlist playlist) {
        return repository.save(playlist);
    }

    public Optional<Playlist> findById(Long id) {
        return repository.findById(id);
    }

    public List<Playlist> findAll() {
        return repository.findAll();
    }

    public Playlist edit(Playlist playlist) {
        return repository.save(playlist);
    }

    public void delete(Playlist playlist) {
        repository.delete(playlist);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean exist(Long id){ return repository.existsById(id);}



}
