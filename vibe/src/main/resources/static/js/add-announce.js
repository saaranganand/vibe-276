const form = document.getElementById('announceform');
const title = document.getElementById('title');
const content = document.getElementById('hiddenContent');
const image = document.getElementById('image');

form.addEventListener('submit', e => {
    e.preventDefault();
    document.getElementById('hiddenContent').value = document.getElementById('content').innerText;

    validateInputs();
});

const setError = (element, message) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = message;
    inputControl.classList.add('error');
    inputControl.classList.remove('success')
}

const setSuccess = element => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = '';
    inputControl.classList.add('success');
    inputControl.classList.remove('error');
};

const isValidTitleLength = title => {
    return title.length <= 100;
}

const isValidContentLength = content => {
    return content.length <= 1000;
}

const isValidImageURL = image => {
    const re = /\.(jpg|jpeg|png)$/;
    return re.test(String(image).toLowerCase());
}

const validateInputs = () => {
    const titleValue = title.value.trim();
    const contentValue = content.value.trim();
    const imageValue = image.value.trim();

    isValid = true;

    if(titleValue === '') {
        setError(title, 'Title is required');
        isValid = false;
    } else if (!isValidTitleLength(titleValue)) {
        setError(title, 'Title must be 100 characters or less.');
        isValid = false;
    } else {
        setSuccess(title);
    }

    if(contentValue === '') {
        setError(content, 'Content is required');
        isValid = false;
    } else if (!isValidContentLength(contentValue)) {
        setError(content, 'Content must be 1000 characters or less.');
        isValid = false;
    } else {
        setSuccess(content);
    }

    if (imageValue !== '' && !isValidImageURL(imageValue)) {
        setError(image, 'Provide a valid image URL (must end with .jpg, .jpeg, or .png)');
        isValid = false;
    } else {
        setSuccess(image);
    }

    if(isValid) {
        form.submit();
    }
};