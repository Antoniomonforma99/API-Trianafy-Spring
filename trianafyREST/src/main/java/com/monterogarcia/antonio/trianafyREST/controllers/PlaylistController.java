package com.monterogarcia.antonio.trianafyREST.controllers;

import com.monterogarcia.antonio.trianafyREST.DTOs.*;
import com.monterogarcia.antonio.trianafyREST.models.Playlist;
import com.monterogarcia.antonio.trianafyREST.models.Song;
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

    private final SongDTOConverter songDTOConverter;
    private final SongService songService;

    @Operation(summary = "Crea una nueva playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado una playlist correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Error en los datos de la playlist",
                    content = @Content),
    })
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

    @Operation(summary = "Muestra todas las playlists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todas las playlists correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado playlists",
                    content = @Content),
    })
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

    @Operation(summary = "Muestra una playlist existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha mostrado una playlist  correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la playlist por el ID",
                    content = @Content),
    })
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

    @Operation(summary = "Borra una playlist existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado a la playlist correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
    })
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

    @Operation(summary = "Modifica una playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado la playlist correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Error en los datos de la playlist",
                    content = @Content),
    })
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

    @Operation(summary = "Agrega una cancion a una playlist existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la playlist o la cancion por el ID",
                    content = @Content),
    })
    @PostMapping("/{id1}/song/{id2}")
    public ResponseEntity<Playlist> addSongToPlaylist(
            @PathVariable Long id1,
            @PathVariable Long id2) {
        if(service.findById(id1) == null
        || songService.findById(id2) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        Playlist P = service.findById(id1).get();
        Song s = songService.findById(id2).get();

        P.addSong(s);
        service.add(P);

        return ResponseEntity
                .ok()
                .body(P);

    }
//Preguntar a luismi
    /*
    @GetMapping("/{id}/song")
    public ResponseEntity<SongFromPlaylistResponse> getAllSongsFromPlaylist(@PathVariable Long id) {
        if(service.findById(id) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        Playlist p = service.findById(id).get();

        List<SongResponse> songs = p.getSongs().stream()
                .map(songDTOConverter::songToSongResponse)
                .collect(Collectors.toList());

        SongFromPlaylistResponse response = dtoConverter.playPlaylistToSomgFromPlaylistRespose(p);
        return ResponseEntity
                .ok()
                .body(response);
    }

    */

    @Operation(summary = "Muestra las canciones de una playlist existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han mostrado las canciones de una playlist correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la playlist por el ID",
                    content = @Content),
    })
    @GetMapping("/{id}/song")
    public ResponseEntity<Playlist> getSongsFromPlaylist (@PathVariable Long id) {
        if (service.findById(id) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity
                .ok()
                .body(service.findById(id).get());

    }


    @Operation(summary = "Muestra una canción de una playlist existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han mostrado la canción de una playlist correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la canción",
                    content = @Content),
    })
    @GetMapping("/{id1}/song/{id2}")
    public ResponseEntity<Song> getSongFromPlaylistById(@PathVariable Long id2) {
        {
            if (songService.findById(id2) == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            } else {
                return ResponseEntity.of(songService.findById(id2));
            }
        }
    }

    @Operation(summary = "Borrado de una cancion de una playlist existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado una cancion de una playlist correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
    })
    @DeleteMapping("/{id1}/song/{id2}")
    public ResponseEntity<Playlist> deleteSongFromPlaylist(
            @PathVariable Long id1,
            @PathVariable Long id2
    ) {
        if (service.findById(id1) == null
        || songService.findById(id2) == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        Playlist p = service.findById(id1).get();
        Song s = songService.findById(id2).get();

        p.deleteSong(s);
        service.add(p);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
