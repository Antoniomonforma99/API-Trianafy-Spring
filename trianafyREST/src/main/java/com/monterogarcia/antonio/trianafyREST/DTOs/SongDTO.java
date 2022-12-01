package com.monterogarcia.antonio.trianafyREST.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class SongDTO {


    private Long id;
    private String title;
    private Long artistId;
    private String artistName;
    private String album;
    private String year;

}

