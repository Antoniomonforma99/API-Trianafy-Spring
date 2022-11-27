package com.monterogarcia.antonio.trianafyREST.DTOs;

import com.monterogarcia.antonio.trianafyREST.models.Playlist;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlaylistDTOConverter {

    static SongDTOConverter songDTOConverter;

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

    public SongFromPlaylistResponse playPlaylistToSomgFromPlaylistRespose(Playlist p) {
        return SongFromPlaylistResponse
                .builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .songs(p.getSongs().stream().map(songDTOConverter::songToSongResponse).collect(Collectors.toList()))
                .build();

    }
}
