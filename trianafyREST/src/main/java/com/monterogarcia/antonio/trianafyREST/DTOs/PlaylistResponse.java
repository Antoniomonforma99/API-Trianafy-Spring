package com.monterogarcia.antonio.trianafyREST.DTOs;

import com.monterogarcia.antonio.trianafyREST.models.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class PlaylistResponse {

    private Long id;
    private String name;
    private Integer numberOfSongs;

    public static PlaylistResponse of (Playlist p) {
        return PlaylistResponse
                .builder()
                .id(p.getId())
                .name(p.getName())
                .numberOfSongs(p.getSongs().size())
                .build();
    }
}
