package com.example.music_library.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import com.example.music_library.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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

    public CompletableFuture<List<Artist>> searchArtists(String query) {
        CompletableFuture<List<Artist>> future = new CompletableFuture<>();
        Query artistQuery = databaseReference.orderByChild("name")
                .startAt(query)
                .endAt(query + "\uf8ff");
        artistQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Artist> artistList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    artistList.add(snapshot.getValue(Artist.class));
                }
                future.complete(artistList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }
}
