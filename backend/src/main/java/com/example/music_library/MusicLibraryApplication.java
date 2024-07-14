package com.example.music_library;

import com.example.music_library.controller.AlbumController;
import com.example.music_library.controller.ArtistController;
import com.example.music_library.controller.SongController;
import com.example.music_library.model.Album;
import com.example.music_library.model.Artist;
import com.example.music_library.model.Song;
import com.example.music_library.service.ArtistService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MusicLibraryApplication implements CommandLineRunner {

	@Autowired
	private ArtistController artistController;
	@Autowired
	private AlbumController albumController;
	@Autowired
	private SongController songController;

	public static void main(String[] args) {
		SpringApplication.run(MusicLibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Song song1 = new Song("song_title1", "3:01");
//		Song song2 = new Song("song_title2", "3:02");
//
//		ArrayList<Song> songList = new ArrayList<>();
//		songList.add(song1);
//		songList.add(song2);
//
//		Album album1 = new Album("album_title1", songList, "blablablabla");
//
//		ArrayList<Album> albumList = new ArrayList<>();
//		albumList.add(album1);
//
//		Artist arist = new Artist("artist_name", albumList);
//
//		artistService.createArtist(arist);

//		artistService.deleteArtist("-O1hT55f4pFue1xlZ02n");

//		Artist artistToBeFound = artistService.getArtistById("1");
//		System.out.println(artistToBeFound.getName());

		// Test retrieving all artists and print the first one
//		System.out.println("Fetching all artists...");
//		List<Artist> artists = artistController.getAllArtists();
//		if (!artists.isEmpty()) {
//			Artist artist = artists.get(0);
//			System.out.println("First artist in the database: " + artist.getName());
//		} else {
//			System.out.println("No artists found in the database.");
//		}

//		System.out.println("Fetching all albums...");
//		List<Album> albums = albumController.getAllAlbums("0");
//		if (!albums.isEmpty()) {
//			Album album = albums.get(0);
//			System.out.println("First albums in the database: " + album.getTitle());
//		} else {
//			System.out.println("No albums found in the database.");
//		}

		System.out.println("Fetching all songs...");
		List<Song> songs = songController.getAllSongs("0", "0");
		if (!songs.isEmpty()) {
			Song song = songs.get(0);
			System.out.println("First albums in the database: " + song.getTitle());
		} else {
			System.out.println("No albums found in the database.");
		}
	}
}
