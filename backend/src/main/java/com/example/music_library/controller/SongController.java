package com.example.music_library.controller;

import com.example.music_library.model.Album;
import com.example.music_library.model.Song;
import com.example.music_library.service.SongService;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/artists/{artistId}/albums/{albumId}/songs")
public class SongController {
    @Autowired
    private SongService songService;

    private Song getSongInfo(DataSnapshot dataSnapshot) {
        String id = dataSnapshot.getKey();
        String title = dataSnapshot.child("title").getValue(String.class);
        String length = dataSnapshot.child("length").getValue(String.class);

        return new Song(id, title, length);
    }

    @GetMapping()
    public List<Song> getAllSongs(@PathVariable String artistId, @PathVariable String albumId) {
        DatabaseReference ref = songService.getAllSongs(artistId, albumId);
        List<Song> songList = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Song song = getSongInfo(snapshot);
                    songList.add(song);
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return songList;
    }

    @GetMapping("/search")
    public CompletableFuture<List<Song>> searchSongs(@RequestParam String query) {
        return songService.searchSongs(query);
    }

    @PostMapping
    public void addSong(@RequestBody Song song) {
        songService.addSong(song);
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
