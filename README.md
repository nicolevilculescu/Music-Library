# Digital Music Library Project
This project is a digital music library built with Java Spring Boot for the backend, React.js for the frontend, and Firebase Realtime Database for data storage.

## Features
- View artists and their albums
- View album descriptions and lists of songs

## Prerequisites
Before running this project locally, ensure you have the following installed:

- Java Development Kit (JDK) version 11 or higher
- Node.js and npm (Node Package Manager)
- Firebase account

## Getting Started
Follow these steps to run the project locally:

### Backend (Java Spring Boot)
1. Clone the repository
<pre>
git clone https://github.com/yourusername/digital-music-library.git
cd digital-music-library/backend
</pre>
2. Get the serviceAccountKey.json file for the Firebase Realtime Database
3. Run the Spring Boot application
<pre>
./mvnw spring-boot:run
</pre>

or

run the application from inside your IDE

### Frontend (React.js)
1. Navigate to the frontend directory
<pre>
cd ../frontend
</pre>

2. Install dependencies
<pre>
npm install
npm install axios
</pre>

3. Start the React development server
<pre>
npm start
</pre>
The frontend development server will start and open your default web browser to http://localhost:3000.

## Usage
- Open your web browser and navigate to http://localhost:3000.
- Explore the digital music library.

## Future developments
- Autocomplete search for artist, album, and song names