import React, { useState, useEffect } from 'react';
import axios from 'axios';

import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';

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
        <Box sx={{ pl: 4, ml: 2 }}>
      <p>{album.description}</p>
      <List component="div" disablePadding>
        {album.songs.map(song => (
          <ListItem key={song.id} disablePadding>
            <ListItemText primary={`${song.title} - ${song.length}`} />
          </ListItem>
        ))}
      </List>
    </Box>
    );
}

export default AlbumDetails;