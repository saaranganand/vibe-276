// get image of random album
function getAlbumCover(artistName, element) {
  var encodedArtistName = artistName.split(" ").join("+");
  fetch(
    "https://musicbrainz.org/ws/2/release-group/?query=artist:" +
      encodedArtistName +
      "&fmt=json"
  )
    .then((response) => response.json())
    .then((data) => {
      var albums = data["release-groups"].filter(
        (album) => album["primary-type"] === "Album"
      );

      function fetchRandomAlbumCover() {
        if (albums.length === 0) {
          return;
        }

        // Get a random album from the list of studio albums
        var randomIndex = Math.floor(Math.random() * albums.length);
        var randomAlbum = albums[randomIndex];

        albums.splice(randomIndex, 1);

        var albumId = randomAlbum.id;

        fetch("http://coverartarchive.org/release-group/" + albumId)
          .then((response) => {
            if (!response.ok) {
              // If album cover not found, try another album
              fetchRandomAlbumCover();
            } else {
              return response.json();
            }
          })
          .then((data) => {
            if (data) {
              var frontCoverImages = data.images.filter(
                (image) => image.front === true
              );
              if (frontCoverImages.length > 0) {
                var coverUrl = frontCoverImages[0].image;
                var img = document.createElement("img");
                img.src = coverUrl;

                element.appendChild(img);
              }
            }
          });
      }

      fetchRandomAlbumCover();
    });
}

//get image of artist
function getArtistImage(artistName, element) {
    var encodedArtistName = artistName.split(" ").join("+");
    fetch(
      "https://musicbrainz.org/ws/2/artist/?query=" +
        encodedArtistName +
        "&fmt=json"
    )
      .then((response) => response.json())
      .then((data) => {
        var mbid = data.artists[0].id;
  
        fetch(
          "https://musicbrainz.org/ws/2/artist/" +
            mbid +
            "?fmt=json&inc=url-rels"
        )
          .then((response) => response.json())
          .then((data) => {
            var relation = data.relations.find((relation) => {
                return relation.type === "image" && 
                       (relation.url.resource.endsWith(".jpg") || 
                        relation.url.resource.endsWith(".jpeg") || 
                        relation.url.resource.endsWith(".png")) && 
                       !relation.url.resource.includes('wikimedia.org');
                       // permission issues with rendering wikimedia images
            });

                if (relation) {
                    var artistImage = relation["url"]["resource"];

                    var img = document.createElement("img");
                    img.src = artistImage;

                    element.appendChild(img);
                } else {
                    console.log('No image found for artist: ' + artistName);
                    getAlbumCover(artistName, element);
                }
            });
        });
    }
