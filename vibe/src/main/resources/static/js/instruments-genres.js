function fetchInstruments(query) {
    var value = event ? event.target.value : document.getElementById('instrument').value;

    if (query.length < 1) {
        document.getElementById('instrumentDropdown').innerHTML = '';
        return;
    }

    fetch(`https://musicbrainz.org/ws/2/instrument?query=${query}&fmt=json`)
        .then(response => response.json())
        .then(data => {
            let dropdown = document.getElementById('instrumentDropdown');
            dropdown.style.display = 'block';
            dropdown.innerHTML = '';
            data.instruments.forEach(instrument => {
                let option = document.createElement('div');
                option.innerText = instrument.name;
                option.onclick = function() {
                    document.getElementById('instrument').value = this.innerText;
                    dropdown.innerHTML = '';
                };
                dropdown.appendChild(option);
            });
        });
}

let allGenres = [];
let limit = 100;
let offset = 0;

function fetchAllGenres() {
    return new Promise((resolve, reject) => {
        function fetchPage() {
            fetch(`https://musicbrainz.org/ws/2/genre/all?limit=${limit}&offset=${offset}&fmt=json`)
                .then(response => response.json())
                .then(data => {
                    allGenres = allGenres.concat(data.genres);

                    if (data.genres.length === limit) {
                        // there might be more so fetch the next page
                        offset += limit;
                        fetchPage();
                    } else {
                        resolve();
                    }
                })
                .catch(reject);
        }

        fetchPage();
    });
}

//fetch all genres when page loads then add event listner
fetchAllGenres().then(() => {
    document.getElementById('genres').addEventListener('input', (event) => {
        fetchGenres(event.target.value);
    });
});

function fetchGenres(query) {
    var value = event ? event.target.value : document.getElementById('genres').value;

    if (query.length < 1) {
        document.getElementById('genreDropdown').innerHTML = '';
        return;
    }

    let matchingGenres = allGenres.filter(genre => genre.name.toLowerCase().includes(query.toLowerCase()));

    let dropdown = document.getElementById('genreDropdown');
    dropdown.style.display = 'block';
    dropdown.innerHTML = '';
    matchingGenres.forEach(genre => {
        let option = document.createElement('div');
        option.innerText = genre.name;
        option.onclick = function() {
            document.getElementById('genres').value = this.innerText;
            dropdown.innerHTML = '';
        };
        dropdown.appendChild(option);
    });
}