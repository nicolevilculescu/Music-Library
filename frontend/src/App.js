import React, { useState } from 'react';
import ArtistList from './components/ArtistList';
import Autocomplete from './components/Autocomplete';

function App() {
    const [, setSelectedArtist] = useState(null);
    const [, setSelectedAlbum] = useState(null);

    const handleSelect = (item) => {
        if (item.type === 'artist') {
            setSelectedArtist(item.id);
            setSelectedAlbum(null); // Reset selected album
        } else if (item.type === 'album') {
            setSelectedAlbum(item.id);
        }
    };

    return (
        <div>
            {/* <Autocomplete onSelect={handleSelect} /> */}
            <ArtistList onArtistSelect={setSelectedArtist} onAlbumSelect={setSelectedAlbum} />
        </div>
    );
}

export default App;