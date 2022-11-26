package com.monterogarcia.antonio.trianafyREST.controllers;

import com.monterogarcia.antonio.trianafyREST.DTOs.CreateSongDTO;
import com.monterogarcia.antonio.trianafyREST.DTOs.SongDTOConverter;
import com.monterogarcia.antonio.trianafyREST.DTOs.SongResponse;
import com.monterogarcia.antonio.trianafyREST.models.Artist;
import com.monterogarcia.antonio.trianafyREST.models.Song;
import com.monterogarcia.antonio.trianafyREST.services.ArtistService;
import com.monterogarcia.antonio.trianafyREST.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongController {

    private final SongService service;
    private final ArtistService artistService;
    private final SongDTOConverter dtoConverter;

    @PostMapping("/")
    public ResponseEntity<SongResponse> add (@RequestBody CreateSongDTO s) {
        if (s.getArtistId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Song newSong = dtoConverter.createSongResponseToSong(s);
        //Mirar el orElse
        Artist artist = artistService.findById(s.getArtistId()).orElse(null);

        newSong.setArtist(artist);
        service.add(newSong);

        SongResponse response = dtoConverter.songToSongResponse(newSong);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<SongResponse>> findAll() {
        List<Song> songs = service.findAll();

        if (songs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            List<SongResponse> allSongs = songs.stream()
                    .map(dtoConverter::songToSongResponse)
                    .collect(Collectors.toList());
            return ResponseEntity
                    .ok()
                    .body(allSongs);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> findById(
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
    public ResponseEntity<Song> delete (@PathVariable Long id) {
        if (service.exist(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongResponse> update (
            @PathVariable Long id,
            @RequestBody CreateSongDTO s) {

        Artist artist = artistService.findById(s.getArtistId()).get();

        if (artist == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (service.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Song oldSong = service.findById(id).get();
        oldSong.setTitle(s.getTitle());
        oldSong.setYear(s.getYear());
        oldSong.setAlbum(s.getAlbum());
        oldSong.setArtist(artist);

        service.add(oldSong);

        /*
        SongResponse songResponse = dtoConverter.songToSongResponse(oldSong);
         */
        return ResponseEntity
                .ok()
                .body(dtoConverter.songToSongResponse(oldSong));


        /*

        Song song = service.findById(id).map({
                song.setTitle(s.getTitle()),
                song.setAlbum(s.getAlbum()),
                song.setYear(s.getYear()),
                song.setArtist(s.getArtist())
        });

        Song song1 = service.findById(id).map({
            song.setTitle(s.getTitle()),
            song.setAlbum(s.getAlbum());
            song.setYear(s.getYear());
            song.setArtist(s.getArtist())});
            service.add(o);
                    return o;
                })
        );
        */

    }
}
