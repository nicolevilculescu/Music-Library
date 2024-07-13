package com.example.music_library.repository;

import com.example.music_library.model.Song;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepositoryImpl extends GenericRepositoryImpl<Song> implements SongRepository {

    public SongRepositoryImpl(FirebaseApp firebaseApp) {
        super(firebaseApp, "songs");
    }

    @Override
    protected String getId(Song entity) {
        return entity.getId();
    }

    public Song getEntity_(DataSnapshot dataSnapshot) {
        String title = dataSnapshot.child("title").getValue(String.class);
        String length = dataSnapshot.child("length").getValue(String.class);

        return new Song(title, length);
    }

    @Override
    protected Song getEntity(DataSnapshot dataSnapshot, DataSnapshot snapshot) {
        return getEntity_(snapshot);
    }

    @Override
    protected Song getEntity(DataSnapshot dataSnapshot) {
        return getEntity_(dataSnapshot);
    }

    @Override
    protected Map<String, Object> convertToMap(Song entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", entity.getTitle());
        map.put("songs", entity.getLength());

        return map;
    }
}