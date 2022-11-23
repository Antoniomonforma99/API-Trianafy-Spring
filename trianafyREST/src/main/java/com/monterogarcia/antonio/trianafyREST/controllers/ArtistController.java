package com.monterogarcia.antonio.trianafyREST.controllers;

import com.monterogarcia.antonio.trianafyREST.models.Artist;
import com.monterogarcia.antonio.trianafyREST.services.ArtistService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@NoArgsConstructor
public class ArtistController {

    @Autowired
    private ArtistService service;

    @PostMapping("/country/")
    public ResponseEntity

    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
