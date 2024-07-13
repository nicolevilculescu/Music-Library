package com.example.music_library.controller;

import com.example.music_library.model.Album;
import com.example.music_library.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    @PostMapping
    public void createAlbum(@RequestBody Album artist) {
        albumService.createAlbum(artist);
    }

    @GetMapping("/{id}")
    public Album getAlbumById(@PathVariable String id) {
        return albumService.getAlbumById(id);
    }

    @PutMapping("/{id}")
    public void updateAlbum(@PathVariable String id, @RequestBody Album artist) {
        albumService.updateAlbum(id, artist);
    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable String id) {
        albumService.deleteAlbum(id);
    }
}
