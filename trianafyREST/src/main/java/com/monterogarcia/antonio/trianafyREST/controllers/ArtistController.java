package com.monterogarcia.antonio.trianafyREST.controllers;

import com.monterogarcia.antonio.trianafyREST.models.Artist;
import com.monterogarcia.antonio.trianafyREST.models.Song;
import com.monterogarcia.antonio.trianafyREST.services.ArtistService;
import com.monterogarcia.antonio.trianafyREST.services.SongService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistService service;
    
    @Autowired
    private SongService songService;

    @PostMapping("/")
    public ResponseEntity<Artist> add (@RequestBody Artist a) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.add(a));
    }

    @GetMapping("/")
    public ResponseEntity<List<Artist>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> findById(
            @PathVariable Long id
    ){
        if (service.findById(id) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } else {
            return ResponseEntity.of(service.findById(id));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Artist> delete (@PathVariable Long id) {
        if (service.exist(id)) {
            List<Song> songs = songService.findAll();

            if (songs.isEmpty()) {
                service.deletebyId(id);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .build();
            }

            for (Song s: songs) {
                if (s.getArtist() != null) {
                    if (s.getArtist().getId() == id) {
                        s.setArtist(null);
                        songService.add(s);
                    }
                }

            }
            service.deletebyId(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
                

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> update (
            @PathVariable Long id,
            @RequestBody Artist a) {

        if (service.findById(id) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.of(
                service.findById(id).map(o -> {
                    o.setName(a.getName());
                    service.add(o);
                    return o;
                })
        );
    }
}
