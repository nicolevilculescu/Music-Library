package com.example.music_library.controller;

import com.example.music_library.model.Song;
import com.example.music_library.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class SongController {
    @Autowired
    private SongService songService;

    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @PostMapping
    public void createSong(@RequestBody Song song) {
        songService.createSong(song);
    }

    @GetMapping("/{id}")
    public Song getSongById(@PathVariable String id) {
        return songService.getSongById(id);
    }

    @PutMapping("/{id}")
    public void updateSong(@PathVariable String id, @RequestBody Song song) {
        songService.updateSong(id, song);
    }

    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable String id) {
        songService.deleteSong(id);
    }
}
