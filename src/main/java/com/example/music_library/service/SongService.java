package com.example.music_library.service;

import com.example.music_library.model.Song;
import com.example.music_library.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SongService {
    @Autowired
    private SongRepository songRepository;

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song getSongById(String id) {
        return songRepository.findById(id);
    }

    public void createSong(Song song) {
        songRepository.save(song);
    }

    public void updateSong(String id, Song song) {
        song.setId(id);
        songRepository.save(song);
    }

    public void deleteSong(String id) {
        songRepository.deleteById(id);
    }
}
