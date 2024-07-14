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
public class Album {
    @FirebaseId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("songs")
    private List<Song> songs;

    @JsonProperty("description")
    private String description;

    public Album() {
    }

    public Album(String id, String title, List<Song> songs, String description) {
        this.id = id;
        this.title = title;
        this.songs = songs;
        this.description = description;
    }

    public Album(String title, List<Song> songs, String description) {
        this.title = title;
        this.songs = songs;
        this.description = description;
    }
}