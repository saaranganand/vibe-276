//search anouncements based on input stirng
function searchAnnouncements() {
    const searchString = document.getElementById('searchbar').value.toLowerCase();
    const announcements = document.querySelectorAll('.announcement');
    for (let i = 0; i < announcements.length; i++) {
        if (announcements[i].textContent.toLowerCase().includes(searchString)) {
            announcements[i].style.display = 'block';
        } else {
            announcements[i].style.display = 'none';
        }
    }
}