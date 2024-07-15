package com.example.music_library.controller;

import com.example.music_library.model.Album;
import com.example.music_library.model.Artist;
import com.example.music_library.service.ArtistService;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    private Artist getArtistInfo(DataSnapshot snapshot, DataSnapshot dataSnapshot) {
        String id = snapshot.getKey();
        String name = snapshot.child("name").getValue(String.class);

        GenericTypeIndicator<List<Album>> t = new GenericTypeIndicator<>() {};
        List<Album> albums = dataSnapshot.child("albums").getValue(t);

        return new Artist(id, name, albums);
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        DatabaseReference ref = artistService.getAllArtists();
        List<Artist> artistList = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Artist artist = getArtistInfo(snapshot, dataSnapshot);
                    artistList.add(artist);
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

        return artistList;
    }

    @GetMapping("/search")
    public CompletableFuture<List<Artist>> searchArtists(@RequestParam String query) {
        return artistService.searchArtists(query);
    }

    @PostMapping
    public void addArtist(@RequestBody Artist artist) {
        artistService.addArtist(artist);
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