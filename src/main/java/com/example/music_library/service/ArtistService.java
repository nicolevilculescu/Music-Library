package com.example.music_library.service;

import com.example.music_library.model.Artist;
import com.example.music_library.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Artist getArtistById(String id) {
        return artistRepository.findById(id);
    }

    public void createArtist(Artist artist) {
        artistRepository.save(artist);
    }

    public void updateArtist(String id, Artist artist) {
        artist.setId(id);
        artistRepository.save(artist);
    }

    public void deleteArtist(String id) {
        artistRepository.deleteById(id);
    }
}
