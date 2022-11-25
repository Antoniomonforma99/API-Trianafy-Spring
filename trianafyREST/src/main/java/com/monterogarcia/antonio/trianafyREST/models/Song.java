package com.monterogarcia.antonio.trianafyREST.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String album;
    @Column(name = "year_of_song")
    private String year;

    @ManyToOne(fetch = FetchType.EAGER)
    private Artist artist;

    public Song(String title, String album, String year) {
        this.title = title;
        this.album = album;
        this.year = year;
    }
}
