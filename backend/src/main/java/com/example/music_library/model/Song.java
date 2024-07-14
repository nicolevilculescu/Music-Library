package com.example.music_library.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Song {
    @FirebaseId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("length")
    private String length;

    public Song(String id, String title, String length) {
        this.id = id;
        this.title = title;
        this.length = length;
    }

    public Song(String title, String length) {
        this.title = title;
        this.length = length;
    }
}