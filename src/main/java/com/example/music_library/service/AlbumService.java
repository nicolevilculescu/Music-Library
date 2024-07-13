package com.example.music_library.service;

import com.example.music_library.model.Album;
import com.example.music_library.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbumById(String id) {
        return albumRepository.findById(id);
    }

    public void createAlbum(Album album) {
        albumRepository.save(album);
    }

    public void updateAlbum(String id, Album album) {
        album.setId(id);
        albumRepository.save(album);
    }

    public void deleteAlbum(String id) {
        albumRepository.deleteById(id);
    }
}
