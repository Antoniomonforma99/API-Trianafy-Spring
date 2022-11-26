package com.monterogarcia.antonio.trianafyREST.controllers;

import com.monterogarcia.antonio.trianafyREST.DTOs.CreatePlaylistDTO;
import com.monterogarcia.antonio.trianafyREST.DTOs.CreatePlaylistResponse;
import com.monterogarcia.antonio.trianafyREST.DTOs.PlaylistDTOConverter;
import com.monterogarcia.antonio.trianafyREST.DTOs.PlaylistResponse;
import com.monterogarcia.antonio.trianafyREST.models.Playlist;
import com.monterogarcia.antonio.trianafyREST.services.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService service;
    private final PlaylistDTOConverter dtoConverter;

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
        }
    }
}
