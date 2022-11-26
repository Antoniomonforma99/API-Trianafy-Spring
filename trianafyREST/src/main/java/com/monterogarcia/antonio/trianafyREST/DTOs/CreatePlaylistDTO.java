package com.monterogarcia.antonio.trianafyREST.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CreatePlaylistDTO {

    private String name;
    private String description;
}
