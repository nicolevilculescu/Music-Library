import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AlbumList from './AlbumList';

import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import Avatar from '@mui/material/Avatar';
import Divider from '@mui/material/Divider';
import Typography from '@mui/material/Typography';

const artistBackgrounds = {
    0: '/imgs/bgs/radiohead.jpg',
    1: '/imgs/bgs/portishead.jpg',
    2: '/imgs/bgs/rammstein.jpg',
    3: '/imgs/bgs/taylor_swift.jpg'
  };

  const artistAvatars = {
    0: '/imgs/bgs/radiohead-avatar.jpg',
    1: '/imgs/bgs/portishead-avatar.jpg',
    2: '/imgs/bgs/rammstein-avatar.jpg',
    3: '/imgs/bgs/taylor_swift_avatar.jpg'
  };  

function ArtistList({ onArtistSelect, onAlbumSelect }) {
    const [artists, setArtists] = useState([]);
    const [selectedArtist, setSelectedArtist] = useState(null);
    const [backgroundImage, setBackgroundImage] = useState('');

    const handleArtistClickWithBackground = (artistId) => {
        if (selectedArtist === artistId) {
            // Clicking again on the same artist clears the background image
            setBackgroundImage('');
            handleArtistClick(null); // Pass null or appropriate value to clear selection
          } else {
            setBackgroundImage(artistBackgrounds[artistId] || '');
            handleArtistClick(artistId);
          }
      };

    useEffect(() => {
        axios.get(`/api/artists`)
            .then(response => setArtists(response.data))
            .catch(error => console.error('Error fetching artists:', error));
    }, []);

    const handleArtistClick = (artistId) => {
      if (selectedArtist === artistId) {
          setSelectedArtist(null); // deselect if already selected
      } else {
          setSelectedArtist(artistId); // select if not selected
          onArtistSelect(artistId); // pass selected artist ID to parent component
      }
  };
  
    return (
        <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        height: '100vh',
        bgcolor: 'background.paper',
        backgroundImage: `url(${backgroundImage})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        padding: '1rem',
        borderRadius: '12px', // rounded corners for the outer box
        overflow: 'hidden' // rounded corners are visible
      }}
    >
      <Box sx={{ flex: '0 1 auto', p: 2, backgroundColor: 'rgba(255, 255, 255, 0.8)', borderRadius: '12px' }}>
        <h2>Artists</h2>
      </Box>
      <List sx={{ flex: '1 1 auto', overflowY: 'auto', borderRadius: '12px', backgroundColor: 'rgba(255, 255, 255, 0.3)' }}>
        {artists.map(artist => (
          <React.Fragment key={artist.id}>
            <ListItem disablePadding>
              <ListItemButton
                onClick={() => handleArtistClickWithBackground(artist.id)}
                sx={{
                  backgroundColor: 'transparent',
                  borderRadius: '12px',
                  transition: 'background-color 0.3s ease',
                  '&:hover': {
                    backgroundColor: 'rgba(255, 255, 255, 0.5)'
                  },
                  ...(selectedArtist === artist.id && {
                    backgroundColor: 'rgba(255, 255, 255, 0.7)'
                  })
                }}
              >
                <ListItemAvatar>
                  <Avatar alt={artist.name} src={artistAvatars[artist.id]} />
                </ListItemAvatar>
                <ListItemText
                  primary={
                    <Typography variant="h6" component="div" sx={{ fontWeight: selectedArtist === artist.id ? 'bold' : 'normal' }}>
                      {artist.name}
                    </Typography>
                  }
                />
              </ListItemButton>
            </ListItem>
            {selectedArtist === artist.id && (
              <Box sx={{ pl: 4, ml: 2 }}>
                <List component="div" disablePadding sx={{ borderRadius: '12px', backgroundColor: 'rgba(255, 255, 255, 0.7)' }}>
                  <AlbumList artistId={artist.id} onAlbumSelect={onAlbumSelect} />
                </List>
              </Box>
            )}
          </React.Fragment>
        ))}
      </List>
      <Divider/>
    </Box>
    );
}

export default ArtistList;