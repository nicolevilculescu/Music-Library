package com.example.music_library.service;

import com.example.music_library.model.Album;
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
public class AlbumService {
    private final DatabaseReference databaseReference;

    @Autowired
    public AlbumService(FirebaseApp firebaseApp) {
        if (FirebaseApp.getApps().isEmpty()) {
            throw new IllegalStateException("FirebaseApp with name \"Music Library\" doesn't exist.");
        }
        this.databaseReference = FirebaseDatabase.getInstance().getReference("artists");
    }

    public void addAlbum(Album album) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValueAsync(album);
    }

    public void updateAlbum(String id, Album album) {
        Map<String, Object> albumUpdates = new HashMap<>();
        albumUpdates.put(id, album);
        databaseReference.updateChildrenAsync(albumUpdates);
    }

    public void deleteAlbum(String id) {
        databaseReference.child(id).removeValueAsync();
    }

    public  CompletableFuture<DataSnapshot> getAllAlbums(String artistId) {
        CompletableFuture<DataSnapshot> future = new CompletableFuture<>();

        databaseReference.child(artistId).child("albums").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                future.complete(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return future;
    }

    public CompletableFuture<DataSnapshot> getAlbum(String artistId, String albumId) {
        CompletableFuture<DataSnapshot> future = new CompletableFuture<>();

        databaseReference.child(artistId).child("albums").child(albumId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                future.complete(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return future;
    }

    public CompletableFuture<List<Album>> searchAlbums(String query) {
        CompletableFuture<List<Album>> future = new CompletableFuture<>();
        Query queryRef = databaseReference.orderByChild("title").startAt(query).endAt(query + "\uf8ff");
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Album> albumList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    albumList.add(snapshot.getValue(Album.class));
                }
                future.complete(albumList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }
}
