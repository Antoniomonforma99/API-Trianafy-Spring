package com.monterogarcia.antonio.trianafyREST.DTOs;

import com.monterogarcia.antonio.trianafyREST.models.Artist;
import com.monterogarcia.antonio.trianafyREST.models.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Stack;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class SongResponse {


    private Long id;

    private String title;
    private String artistName;
    private String album;
    private String year;

    public static SongResponse of (Song s) {
        return SongResponse
                .builder()
                .id(s.getId())
                .title(s.getTitle())
                .artistName(s.getArtist().getName())
                .album(s.getAlbum())
                .year(s.getYear())
                .build();
    }

}
