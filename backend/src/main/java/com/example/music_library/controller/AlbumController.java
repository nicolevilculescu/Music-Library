package com.example.music_library.controller;

import com.example.music_library.model.Album;
import com.example.music_library.model.Artist;
import com.example.music_library.model.Song;
import com.example.music_library.service.AlbumService;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/artists/{artistId}/albums")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    private Album getAlbumInfo(DataSnapshot snapshot) {
        String id = snapshot.getKey();
        String title = snapshot.child("title").getValue(String.class);
        String description = snapshot.child("description").getValue(String.class);

        List<Song> songs = (List<Song>) snapshot.child("songs").getValue();

        return new Album(id, title, songs, description);
    }

    @GetMapping()
    public CompletableFuture<List<Album>> getAllAlbums(@PathVariable String artistId) {
        return albumService.getAllAlbums(artistId)
                .thenApply(dataSnapshot -> {
                    List<Album> albumList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Album album = getAlbumInfo(snapshot);
                        albumList.add(album);
                    }
                    return albumList;
                });
    }

    @GetMapping("/{albumId}")
    public CompletableFuture<Album> getAlbumInfo(@PathVariable String artistId, @PathVariable String albumId) {
        return albumService.getAlbum(artistId, albumId)
                .thenApply(dataSnapshot -> {
                    Album album = new Album();
                    if (dataSnapshot.exists()) {
                        album.setId(dataSnapshot.getKey());
                        album.setTitle(dataSnapshot.child("title").getValue(String.class));
                        album.setDescription(dataSnapshot.child("description").getValue(String.class));
                        album.setSongs((List<Song>) dataSnapshot.child("songs").getValue());
                    }
                    return album;
                });
    }

    @GetMapping("/search")
    public CompletableFuture<List<Album>> searchAlbums(@RequestParam String query) {
        return albumService.searchAlbums(query);
    }

    @PostMapping
    public void addAlbum(@RequestBody Album album) {
        albumService.addAlbum(album);
    }

    @PutMapping("/{id}")
    public void updateAlbum(@PathVariable String id, @RequestBody Album album) {
        albumService.updateAlbum(id, album);
    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable String id) {
        albumService.deleteAlbum(id);
    }
}
