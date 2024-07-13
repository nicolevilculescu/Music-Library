package com.example.music_library.controller;

import com.example.music_library.model.Artist;
import com.example.music_library.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @PostMapping
    public void createArtist(@RequestBody Artist artist) {
        artistService.createArtist(artist);
    }

    @GetMapping("/{id}")
    public Artist getArtistById(@PathVariable String id) {
        return artistService.getArtistById(id);
    }

    @PutMapping("/{id}")
    public void updateArtist(@PathVariable String id, @RequestBody Artist artist) {
        artistService.updateArtist(id, artist);
    }

    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable String id) {
        artistService.deleteArtist(id);
    }
}