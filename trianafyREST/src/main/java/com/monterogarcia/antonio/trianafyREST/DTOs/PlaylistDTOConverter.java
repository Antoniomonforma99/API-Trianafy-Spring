package com.monterogarcia.antonio.trianafyREST.DTOs;

import com.monterogarcia.antonio.trianafyREST.models.Playlist;
import org.springframework.stereotype.Component;

@Component
public class PlaylistDTOConverter {

    public Playlist createPlaylistResponseToPlaylist(CreatePlaylistDTO p) {
        return new Playlist(
                p.getName(),
                p.getDescription()
        );
    }

    public CreatePlaylistResponse playlistToCreatePlaylistResponse(Playlist p) {
        return CreatePlaylistResponse
                .builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .build();
    }

    public PlaylistResponse playlistToPlaylistResponse(Playlist p) {
        return PlaylistResponse
                .builder()
                .id(p.getId())
                .name(p.getName())
                .numberOfSongs(p.getSongs().size())
                .build();
    }
}
