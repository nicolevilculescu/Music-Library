package com.example.music_library.service;

import com.example.music_library.model.Song;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class SongService {
    private final DatabaseReference databaseReference;

    @Autowired
    public SongService(FirebaseApp firebaseApp) {
        if (FirebaseApp.getApps().isEmpty()) {
            throw new IllegalStateException("FirebaseApp with name \"Music Library\" doesn't exist.");
        }
        this.databaseReference = FirebaseDatabase.getInstance().getReference("artists");
    }

    public void addSong(Song song) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValueAsync(song);
    }

    public void updateSong(String id, Song song) {
        Map<String, Object> songUpdates = new HashMap<>();
        songUpdates.put(id, song);
        databaseReference.updateChildrenAsync(songUpdates);
    }

    public void deleteSong(String id) {
        databaseReference.child(id).removeValueAsync();
    }

    public DatabaseReference getAllSongs(String artistId, String albumId) {
        return databaseReference.child(artistId).child("albums").child(albumId).child("songs");
    }

    public CompletableFuture<List<Song>> searchSongs(String query) {
        CompletableFuture<List<Song>> future = new CompletableFuture<>();
        Query songQuery = databaseReference.orderByChild("albums/songs/title")
                .startAt(query)
                .endAt(query + "\uf8ff");
        songQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Song> songList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot albumSnapshot : snapshot.child("albums").getChildren()) {
                        for (DataSnapshot songSnapshot : albumSnapshot.child("songs").getChildren()) {
                            songList.add(songSnapshot.getValue(Song.class));
                        }
                    }
                }
                future.complete(songList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }
}
