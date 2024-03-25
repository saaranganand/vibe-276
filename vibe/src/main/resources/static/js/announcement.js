// get current session user
const sessionUser = // ???;

// get all delete buttons
const deleteButtons = document.querySelectorAll('.delete-button');

// get all uploader elements to detect which posts to show button on
const uploaders = document.querySelectorAll('#anno-uploader');

for (let i = 0; i < uploaders.length; i++) {
    //if session user is uploader of that post, reveal button
    if (uploaders[i].textContent === sessionUser) {
        deleteButtons[i].style.display = 'block';
    }
}