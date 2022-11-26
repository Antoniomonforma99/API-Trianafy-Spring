package com.monterogarcia.antonio.trianafyREST.DTOs;

import com.monterogarcia.antonio.trianafyREST.models.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlaylistResponse {

    private Long id;
    private String name;
    private String description;

    public static CreatePlaylistResponse of (Playlist p){
        return CreatePlaylistResponse
                .builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .build();
    }
}
