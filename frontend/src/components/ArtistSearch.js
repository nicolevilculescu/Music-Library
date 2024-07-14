import React, { useState } from 'react';
import axios from 'axios';

const ArtistSearch = ({ setArtists }) => {
  const [query, setQuery] = useState('');

  const searchArtists = async (e) => {
    e.preventDefault();
    const response = await axios.get(`/api/artists/search?query=${query}`);
    setArtists(response.data);
  };

  return (
    <div>
      <h2>Search Artists</h2>
      <form onSubmit={searchArtists}>
        <input
          type="text"
          placeholder="Search by name"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <button type="submit">Search</button>
      </form>
    </div>
  );
};

export default ArtistSearch;