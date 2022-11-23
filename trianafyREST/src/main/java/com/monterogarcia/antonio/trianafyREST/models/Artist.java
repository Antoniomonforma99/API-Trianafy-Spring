package com.monterogarcia.antonio.trianafyREST.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue
    private long id;

    private String name;
}
