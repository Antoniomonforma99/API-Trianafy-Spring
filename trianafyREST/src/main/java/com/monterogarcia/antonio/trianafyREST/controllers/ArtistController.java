package com.monterogarcia.antonio.trianafyREST.controllers;

import com.monterogarcia.antonio.trianafyREST.models.Artist;
import com.monterogarcia.antonio.trianafyREST.services.ArtistService;
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
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> update (
            @PathVariable Long id,
            @RequestBody Artist a) {

        if (service.findById(id) == null) {
            return ResponseEntity.notFound().build();
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
