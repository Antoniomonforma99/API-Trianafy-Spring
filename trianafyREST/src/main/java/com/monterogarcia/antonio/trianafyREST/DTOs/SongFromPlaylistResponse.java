package com.monterogarcia.antonio.trianafyREST.DTOs;

import com.monterogarcia.antonio.trianafyREST.models.Playlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongFromPlaylistResponse {

    private Long id;
    private String name;
    private String description;
    private List<SongResponse> songs;

    @Autowired
    static
    SongDTOConverter dtoConverter;

    public static SongFromPlaylistResponse of (Playlist p) {


        List<SongResponse> songs = p.getSongs().stream()
                .map(dtoConverter::songToSongResponse)
                .collect(Collectors.toList());

        return SongFromPlaylistResponse
                .builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .songs(songs)
                .build();

    }
}
