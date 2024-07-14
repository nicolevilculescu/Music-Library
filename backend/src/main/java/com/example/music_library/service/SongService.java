package com.example.music_library.service;

import com.example.music_library.model.Song;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public Query searchSongs(String query) {
        return databaseReference.orderByChild("name").startAt(query).endAt(query + "\uf8ff");
    }
}
