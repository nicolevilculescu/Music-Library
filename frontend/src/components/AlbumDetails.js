import React, { useState, useEffect } from 'react';
import axios from 'axios';

function AlbumDetails({ artistId, albumId }) {
    const [album, setAlbum] = useState(null);

    useEffect(() => {
        if (artistId && albumId) {
            axios.get(`/api/artists/${artistId}/albums/${albumId}`)
                .then(response => {
                    console.log('Album info:', response.data);
                    setAlbum(response.data);
                })
                .catch(error => console.error('Error fetching album info: ', error));
        }
    }, [artistId, albumId]);

    if (!album) return null;

    return (
        <div>
            <p>{album.description}</p>
            <ul>
                {album.songs.map(song => (
                    <li key={song.id}>
                        {song.title} - {song.length}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default AlbumDetails;