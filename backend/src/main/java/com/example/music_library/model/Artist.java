package com.example.music_library.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Artist {
    @FirebaseId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("albums")
    private List<Album> albums;

    public Artist() {
    }

    public Artist(String id, String name, List<Album> albums) {
        this.id = id;
        this.name = name;
        this.albums = albums;
    }

    public Artist(String name, List<Album> albums) {
        this.name = name;
        this.albums = albums;
    }
}