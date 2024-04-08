var accessToken = '';
const clientId = '44ed3d48f57e4994b209446a8e0e1d1e';
const clientSecret = '4d357bb057914479b7eb0578ec42ff69';

const url = 'https://accounts.spotify.com/api/token';

const payload = {
    method: 'POST',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: new URLSearchParams({
        grant_type: 'client_credentials',
        client_id: clientId,
        client_secret: clientSecret,
    }),
};

fetch(url, payload)
    .then((response) => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then((data) => {
        console.log('Access token:', data.access_token);
        accessToken = data.access_token;
    })
    .catch((error) => {
        console.error('Error fetching access token:', error.message);
});

// if getArtistId() doesnt work run below curl command to get temporary spotiyf access token - TEMPORARY FIX!!
/*
curl -X POST "https://accounts.spotify.com/api/token" \
     -H "Content-Type: application/x-www-form-urlencoded" \
     -d "grant_type=client_credentials&client_id=44ed3d48f57e4994b209446a8e0e1d1e&client_secret=4d357bb057914479b7eb0578ec42ff69"
*/

function getArtistId(artistName) {
    var encodedArtistName = encodeURIComponent(artistName);
    return fetch(
      "https://api.spotify.com/v1/search?q=" +
        encodedArtistName +
        "&type=artist", {
            headers: {
                'Authorization': 'Bearer ' + accessToken
            }
        }
    )
    .then((response) => {
        if (response.status === 401) {
            // Access token expired, refresh it
            return refreshAccessToken().then(() => {
                console.log('Current Access Token:', accessToken);
                // Retry with new access token
                return getArtistId(artistName);
            });
        }
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then((data) => {
        if (!data.artists.items.length) {
            throw new Error('Artist not found');
        }
        var artistId = data.artists.items[0].id;
        return artistId;
    });
}

function redirectToSpotifyArtist(artistName) {
    getArtistId(artistName).then(artistId => {
        var url = "https://open.spotify.com/artist/" + artistId;
        console.log('Current Spotify Access Token:', accessToken);
        console.log(url);
        window.open(url, '_blank');
    });
}