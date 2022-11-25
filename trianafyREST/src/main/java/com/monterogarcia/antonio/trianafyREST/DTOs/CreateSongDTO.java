package com.monterogarcia.antonio.trianafyREST.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CreateSongDTO {

    private String title;
    private Long artistId;
    private String album;
    private String year;
}
