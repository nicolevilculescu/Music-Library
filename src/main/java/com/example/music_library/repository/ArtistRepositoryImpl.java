package com.example.music_library.repository;

import com.example.music_library.model.Album;
import com.example.music_library.model.Artist;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArtistRepositoryImpl extends GenericRepositoryImpl<Artist> implements ArtistRepository {

    public ArtistRepositoryImpl(FirebaseApp firebaseApp) {
        super(firebaseApp, "artists");
    }

    @Override
    protected String getId(Artist entity) {
        return entity.getId();
    }

    @Override
    protected Artist getEntity(DataSnapshot dataSnapshot, DataSnapshot snapshot) {
        String name = snapshot.child("name").getValue(String.class);

        GenericTypeIndicator<List<Album>> t = new GenericTypeIndicator<>() {};
        List<Album> albums = dataSnapshot.child("albums").getValue(t);

        return new Artist(name, albums);
    }

    @Override
    protected Artist getEntity(DataSnapshot dataSnapshot) {
        String name = dataSnapshot.child("name").getValue(String.class);

        List<Album> albums = (List<Album>) dataSnapshot.child("albums").getValue();

        return new Artist(name, albums);
    }

    @Override
    protected Map<String, Object> convertToMap(Artist artist) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", artist.getName());
        map.put("albums", artist.getAlbums());

        return map;
    }
}