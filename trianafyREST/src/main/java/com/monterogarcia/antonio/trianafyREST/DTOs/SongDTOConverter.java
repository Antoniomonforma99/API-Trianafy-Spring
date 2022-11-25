package com.monterogarcia.antonio.trianafyREST.DTOs;

import com.monterogarcia.antonio.trianafyREST.models.Artist;
import com.monterogarcia.antonio.trianafyREST.models.Song;
import com.monterogarcia.antonio.trianafyREST.services.ArtistService;
import com.monterogarcia.antonio.trianafyREST.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SongDTOConverter {

    public Song createSongResponseToSong(CreateSongDTO c) {

        return new Song(
                c.getTitle(),
                c.getAlbum(),
                c.getYear()
        );
    }

    public SongResponse songToSongResponse(Song s){

        return SongResponse
                .builder()
                .title(s.getTitle())
                .album(s.getAlbum())
                .year(s.getYear())
                .artist(s.getArtist().getName())
                .build();
    }
}
