package com.example.music_library;

import com.example.music_library.config.FirebaseConfig;
import com.example.music_library.model.Artist;
import com.example.music_library.repository.ArtistRepositoryImpl;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.google.firebase.database.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MusicLibraryApplicationTests {

	@Mock
	private DatabaseReference databaseReference;

	@Mock
	private DataSnapshot dataSnapshot;

	@InjectMocks
	private ArtistRepositoryImpl artistRepository;

	private List<Artist> testArtists;

//	@BeforeAll
//    static void setUpFirebase() throws IOException {
//		FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");
//
//		FirebaseOptions options = new FirebaseOptions.Builder()
//				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//				.setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com")
//				.build();
//
//		FirebaseApp.initializeApp(options);
//	}

	@BeforeEach
	void setUp() {
		testArtists = new ArrayList<>();
		Artist artist1 = new Artist();
		artist1.setName("Radiohead");
		testArtists.add(artist1);

		Artist artist2 = new Artist();
		artist2.setName("Portishead");
		testArtists.add(artist2);

		Artist artist3 = new Artist();
		artist3.setName("Rammstein");
		testArtists.add(artist3);

		Artist artist4 = new Artist();
		artist4.setName("Taylor Swift");
		testArtists.add(artist4);

		// Mock Firebase Database reference behavior
		when(databaseReference.child(anyString())).thenReturn(databaseReference);
		when(databaseReference.push()).thenReturn(databaseReference);
	}



//	@Test
//	void testFindAll() {
//		doAnswer(invocation -> {
//			ValueEventListener listener = invocation.getArgument(0);
//			when(dataSnapshot.getChildren()).thenReturn(new ArrayList<DataSnapshot>() {{
//				for (Artist artist : testArtists) {
//					DataSnapshot snapshot = mock(DataSnapshot.class);
//					when(snapshot.getValue(Artist.class)).thenReturn(artist);
//					add(snapshot);
//				}
//			}});
//			listener.onDataChange(dataSnapshot);
//			return null;
//		}).when(databaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));
//
//		List<Artist> artists = artistRepository.findAll();
//		assertEquals(4, artists.size());
//		assertEquals("Radiohead", artists.get(0).getName());
//	}
//
//	@Test
//	void testFindById() {
//		String artistId = "123";
//		Artist expectedArtist = new Artist();
//		expectedArtist.setId(artistId);
//		expectedArtist.setName("Radiohead");
//
//		doAnswer(invocation -> {
//			ValueEventListener listener = invocation.getArgument(0);
//			when(dataSnapshot.getValue(Artist.class)).thenReturn(expectedArtist);
//			listener.onDataChange(dataSnapshot);
//			return null;
//		}).when(databaseReference.child(artistId)).addListenerForSingleValueEvent(any(ValueEventListener.class));
//
//		Artist artist = artistRepository.findById(artistId);
//		assertEquals(expectedArtist.getName(), artist.getName());
//	}
//
//	@Test
//	void testSave() {
//		Artist newArtist = new Artist();
//		newArtist.setName("New Artist");
//
//		when(databaseReference.push().getKey()).thenReturn("new_key");
//
//		Artist savedArtist = artistRepository.save(newArtist);
//
//		assertEquals("new_key", savedArtist.getId());
//		verify(databaseReference.child("new_key")).setValueAsync(newArtist);
//	}
//
//	@Test
//	void testDeleteById() {
//		String artistId = "123";
//
//		artistRepository.deleteById(artistId);
//
//		verify(databaseReference.child(artistId)).removeValueAsync();
//	}
}
