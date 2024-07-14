import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AlbumDetails from './AlbumDetails';

function AlbumList({ artistId, onAlbumSelect }) {
    const [albums, setAlbums] = useState([]);
    const [selectedAlbum, setSelectedAlbum] = useState(null);

    useEffect(() => {
        if (artistId) {
            axios.get(`/api/artists/${artistId}/albums`)
                .then(response => {
                    console.log('Albums fetched:', response.data);
                    setAlbums(response.data);
                })
                .catch(error => console.error('Error fetching albums: ', error));
        }
    }, [artistId]);

    const handleAlbumClick = (albumId, event) => {
        event.stopPropagation(); // Prevent the click event from bubbling up
        if (selectedAlbum === albumId) {
            setSelectedAlbum(null); // Deselect if already selected
        } else {
            setSelectedAlbum(albumId); // Select if not selected
            onAlbumSelect(albumId); // Pass selected album ID to parent component
        }
    };

    return (
        <div>
            <ul>
                {albums.map(album => (
                    <li key={album.id}>
                        <span onClick={(event) => handleAlbumClick(album.id, event)}>
                            {album.title}
                        </span>
                        {selectedAlbum === album.id && (
                            <AlbumDetails artistId={artistId} albumId={album.id} />
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default AlbumList;