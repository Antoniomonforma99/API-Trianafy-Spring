package com.monterogarcia.antonio.trianafyREST.DTOs;

import com.monterogarcia.antonio.trianafyREST.models.Playlist;
import com.monterogarcia.antonio.trianafyREST.models.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistByIdResponse {

    private Long id;
    private String name;
    private String description;
    private List<Song> songs;

    /*
    public static PlaylistByIdResponse of (Playlist p) {
        return PlaylistByIdResponse
                .builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .songs.
    }
    */

}
