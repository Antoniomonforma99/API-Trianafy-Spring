package com.monterogarcia.antonio.trianafyREST.controllers;

import com.monterogarcia.antonio.trianafyREST.models.Artist;
import com.monterogarcia.antonio.trianafyREST.models.Song;
import com.monterogarcia.antonio.trianafyREST.services.ArtistService;
import com.monterogarcia.antonio.trianafyREST.services.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist")
@Tag(name = "Artist", description = "Controlador de entidad artist")
public class ArtistController {

    @Autowired
    private ArtistService service;
    
    @Autowired
    private SongService songService;

    @Operation(summary = "Crear un artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Se crea el artista",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "400", description = "Si se introduce mal un dato manda BadRequest",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Artist> add (@RequestBody Artist a) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.add(a));
    }

    @Operation(summary = "Mostrar todas los artistas de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devuelve todas los artistas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class)) }),
            @ApiResponse(responseCode = "404", description = "Artistas no encontrados",
                    content = @Content) })
    @GetMapping("/")
    public ResponseEntity<List<Artist>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Mostrar un artista por la id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se muestra el artista",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "404", description = "Si no se encuentra manda un notFound",
                    content = @Content)
    })
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

    @Operation(summary = "Eliminar un artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Se elimina un artista",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encuentra el id del artista",
                    content = @Content)
    })
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

    @Operation(summary = "Editar un artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edita el artista",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Algún dato se envia mal",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontra el id del artista",
                    content = @Content)
    })
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
