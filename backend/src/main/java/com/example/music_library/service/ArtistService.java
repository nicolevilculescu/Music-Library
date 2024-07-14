package com.example.music_library.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import com.example.music_library.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ArtistService {
    private final DatabaseReference databaseReference;

    @Autowired
    public ArtistService(FirebaseApp firebaseApp) {
        if (FirebaseApp.getApps().isEmpty()) {
            throw new IllegalStateException("FirebaseApp with name \"Music Library\" doesn't exist.");
        }
        this.databaseReference = FirebaseDatabase.getInstance().getReference("artists");
    }

    public void addArtist(Artist artist) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValueAsync(artist);
    }

    public void updateArtist(String id, Artist artist) {
        Map<String, Object> artistUpdates = new HashMap<>();
        artistUpdates.put(id, artist);
        databaseReference.updateChildrenAsync(artistUpdates);
    }

    public void deleteArtist(String id) {
        databaseReference.child(id).removeValueAsync();
    }

    public DatabaseReference getAllArtists() {
        return databaseReference;
    }

    public Query searchArtists(String query) {
        return databaseReference.orderByChild("name").startAt(query).endAt(query + "\uf8ff");
    }
}
