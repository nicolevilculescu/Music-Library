import React, { useState } from 'react';
import axios from 'axios';
import Autosuggest from 'react-autosuggest';

const AlbumAutocomplete = ({ onSelectAlbum }) => {
    const [value, setValue] = useState('');
    const [suggestions, setSuggestions] = useState([]);

    const getSuggestions = async (value) => {
        try {
            const response = await axios.get(`/api/albums/search?query=${value}`);
            return response.data.map(album => ({ label: album.title, value: album.id }));
        } catch (error) {
            console.error('Error fetching suggestions:', error);
            return [];
        }
    };

    const onSuggestionsFetchRequested = async ({ value }) => {
        const suggestions = await getSuggestions(value);
        setSuggestions(suggestions);
    };

    const onSuggestionsClearRequested = () => {
        setSuggestions([]);
    };

    const onChange = (event, { newValue }) => {
        setValue(newValue);
    };

    const onSuggestionSelected = (event, { suggestion }) => {
        onSelectAlbum(suggestion.value);
    };

    const getSuggestionValue = (suggestion) => suggestion.label;

    const renderSuggestion = (suggestion) => (
        <div>
            {suggestion.label}
        </div>
    );

    const inputProps = {
        placeholder: 'Search for albums...',
        value,
        onChange: onChange
    };

    return (
        <Autosuggest
            suggestions={suggestions}
            onSuggestionsFetchRequested={onSuggestionsFetchRequested}
            onSuggestionsClearRequested={onSuggestionsClearRequested}
            getSuggestionValue={getSuggestionValue}
            renderSuggestion={renderSuggestion}
            onSuggestionSelected={onSuggestionSelected}
            inputProps={inputProps}
        />
    );
};

export default AlbumAutocomplete;