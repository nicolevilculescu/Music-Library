import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AlbumDetails from './AlbumDetails';

import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';

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
        <List component="div" disablePadding sx={{ borderRadius: '12px', backgroundColor: 'rgba(255, 255, 255, 0.5)' }}>
      {albums.map(album => (
        <React.Fragment key={album.id}>
          <ListItem disablePadding>
            <ListItemButton
              onClick={(event) => handleAlbumClick(album.id, event)}
              sx={{
                backgroundColor: 'transparent',
                borderRadius: '12px',
                transition: 'background-color 0.3s ease',
                '&:hover': {
                  backgroundColor: 'rgba(255, 255, 255, 0.7)'
                },
                ...(selectedAlbum === album.id && {
                  backgroundColor: 'rgba(255, 255, 255, 0.7)'
                })
              }}
            >
              <ListItemText primary={album.title} />
            </ListItemButton>
          </ListItem>
          {selectedAlbum === album.id && (
            <Box sx={{ pl: 4, ml: 2 }}>
              <List component="div" disablePadding sx={{ borderRadius: '12px', backgroundColor: 'rgba(255, 255, 255, 0.7)' }}>
                <AlbumDetails artistId={artistId} albumId={album.id} />
              </List>
            </Box>
          )}
        </React.Fragment>
      ))}
    </List>
    );
}

export default AlbumList;