import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AlbumList from './AlbumList';

function ArtistList({ onArtistSelect, onAlbumSelect }) {
    const [artists, setArtists] = useState([]);
    const [selectedArtist, setSelectedArtist] = useState(null);

    useEffect(() => {
        axios.get(`/api/artists`)
            .then(response => setArtists(response.data))
            .catch(error => console.error('Error fetching artists:', error));
    }, []);

    const handleArtistClick = (artistId) => {
      if (selectedArtist === artistId) {
          setSelectedArtist(null); // Deselect if already selected
      } else {
          setSelectedArtist(artistId); // Select if not selected
          onArtistSelect(artistId); // Pass selected artist ID to parent component
      }
  };
  
    return (
        <div>
            <h2>Artists</h2>
            <ul>
                {artists.map(artist => (
                    <li key={artist.id} onClick={() => handleArtistClick(artist.id)}>
                        {artist.name}
                        {selectedArtist === artist.id && <AlbumList artistId={artist.id} onAlbumSelect={onAlbumSelect} />}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ArtistList;