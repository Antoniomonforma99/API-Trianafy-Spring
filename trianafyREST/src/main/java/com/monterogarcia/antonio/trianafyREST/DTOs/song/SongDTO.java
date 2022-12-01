package com.monterogarcia.antonio.trianafyREST.DTOs.song;


import com.fasterxml.jackson.annotation.JsonView;
import com.monterogarcia.antonio.trianafyREST.jackson.Views;

public class SongDTO {

    @JsonView({Views.Response.class})
    private Long id;

    @JsonView({Views.Response.class, Views.Request.class})
    private String title;

    @JsonView({Views.Request.class})
    private Long artistId;

    @JsonView({Views.Response.class})
    private String artistName;

    @JsonView({Views.Response.class, Views.Request.class})
    private String album;

    @JsonView({Views.Response.class, Views.Request.class})
    private String year;
}
