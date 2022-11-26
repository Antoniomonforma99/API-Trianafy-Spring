package com.monterogarcia.antonio.trianafyREST.controllers;

import com.monterogarcia.antonio.trianafyREST.DTOs.CreatePlaylistDTO;
import com.monterogarcia.antonio.trianafyREST.DTOs.CreatePlaylistResponse;
import com.monterogarcia.antonio.trianafyREST.DTOs.PlaylistDTOConverter;
import com.monterogarcia.antonio.trianafyREST.DTOs.PlaylistResponse;
import com.monterogarcia.antonio.trianafyREST.models.Playlist;
import com.monterogarcia.antonio.trianafyREST.services.PlaylistService;
import com.monterogarcia.antonio.trianafyREST.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService service;
    private final PlaylistDTOConverter dtoConverter;

    private final SongService songService;

    @PostMapping("/")
    public ResponseEntity<CreatePlaylistResponse> add(@RequestBody CreatePlaylistDTO p) {
        System.out.println(p.getName());
        if (p.getName() == null || p.getDescription() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        Playlist newPlaylist = dtoConverter.createPlaylistResponseToPlaylist(p);
        service.add(newPlaylist);

        CreatePlaylistResponse response = dtoConverter.playlistToCreatePlaylistResponse(newPlaylist);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<PlaylistResponse>> findAll() {
        List<Playlist> playlists = service.findAll();

        if (playlists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            List<PlaylistResponse> allPlaylist = playlists.stream()
                    .map(dtoConverter::playlistToPlaylistResponse)
                    .collect(Collectors.toList());
            return ResponseEntity
                    .ok()
                    .body(allPlaylist);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> findById(
            @PathVariable Long id
    ) {
        if (service.findById(id) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } else {
            return ResponseEntity.of(service.findById(id));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Playlist> delete (@PathVariable Long id) {
        if (service.exist(id)) {
            service.deleteById(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponse> update (
            @PathVariable Long id,
            @RequestBody CreatePlaylistDTO p) {
        if (!service.exist(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        Playlist oldPlaylist = service.findById(id).get();

        oldPlaylist.setName(p.getName());
        oldPlaylist.setDescription(p.getDescription());
        oldPlaylist.setSongs(oldPlaylist.getSongs());

        service.add(oldPlaylist);

        return ResponseEntity
                .ok()
                .body(dtoConverter.playlistToPlaylistResponse(oldPlaylist));
    }

    @PostMapping("/{id1}/song/{id2}")
    public ResponseEntity<Playlist> addSongToPlaylist(
            @PathVariable Long id1, Long id2) {
        if(service.findById(id1) == null
                || songService.findById(id2) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        service.findById(id1).get().addSong(songService.findById(id2).get());
        return ResponseEntity
                .ok()
                .body(service.findById(id1).get());

    }
}
