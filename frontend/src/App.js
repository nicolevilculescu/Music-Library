import React, { useState } from 'react';
import ArtistList from './components/ArtistList';

function App() {
    const [, setSelectedArtist] = useState(null);
    const [, setSelectedAlbum] = useState(null);

    return (
        <div>
            <ArtistList onArtistSelect={setSelectedArtist} onAlbumSelect={setSelectedAlbum} />
        </div>
    );
}

export default App;