package com.example.music_library.repository;

import com.example.music_library.model.Album;
import com.example.music_library.model.Song;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AlbumRepositoryImpl extends GenericRepositoryImpl<Album> implements AlbumRepository {

    public AlbumRepositoryImpl(FirebaseApp firebaseApp) {
        super(firebaseApp, "albums");
    }

    @Override
    protected String getId(Album entity) {
        return entity.getId();
    }

    @Override
    protected Album getEntity(DataSnapshot dataSnapshot, DataSnapshot snapshot) {
        String title = snapshot.child("title").getValue(String.class);
        String description = snapshot.child("description").getValue(String.class);

        GenericTypeIndicator<List<Song>> t = new GenericTypeIndicator<>() {};
        List<Song> songs = dataSnapshot.child("songs").getValue(t);

        return new Album(title, songs, description);
    }

    @Override
    protected Album getEntity(DataSnapshot dataSnapshot) {
        String title = dataSnapshot.child("title").getValue(String.class);
        String description = dataSnapshot.child("description").getValue(String.class);

        List<Song> songs = (List<Song>) dataSnapshot.child("songs").getValue();

        return new Album(title, songs, description);
    }

    @Override
    protected Map<String, Object> convertToMap(Album entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", entity.getTitle());
        map.put("songs", entity.getSongs());
        map.put("description", entity.getDescription());

        return map;
    }
}