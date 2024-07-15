import React, { useState } from 'react';
import axios from 'axios';

function Autocomplete({ onSelect }) {
    const [inputValue, setInputValue] = useState('');
    const [suggestions, setSuggestions] = useState([]);
    const [loading, setLoading] = useState(false);

    const fetchSuggestions = (query) => {
        setLoading(true);
        axios.get(`/api/search?query=${query}`)
            .then(response => {
                setSuggestions(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching suggestions:', error);
                setLoading(false);
            });
    };

    const handleInputChange = (event) => {
        const value = event.target.value;
        setInputValue(value);
        if (value.trim() !== '') {
            fetchSuggestions(value);
        } else {
            setSuggestions([]);
        }
    };

    const handleSelectSuggestion = (item) => {
        setInputValue('');
        onSelect(item); // Pass selected item to parent component
    };

    return (
        <div>
            <input
                type="text"
                value={inputValue}
                onChange={handleInputChange}
                placeholder="Search..."
            />
            {loading && <p>Loading...</p>}
            {suggestions.length > 0 && (
                <ul>
                    {suggestions.map((item, index) => (
                        <li key={index} onClick={() => handleSelectSuggestion(item)}>
                            {item.name}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default Autocomplete;
