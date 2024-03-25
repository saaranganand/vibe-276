const form = document.getElementById('registerform');
const email = document.getElementById('email');
const password = document.getElementById('password');
const password2 = document.getElementById('password2');

form.addEventListener('submit', e => {
    e.preventDefault();

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

const validateInputs = () => {
    const passwordValue = password.value.trim();
    const password2Value = password2.value.trim();

    isValid = true;

    if(passwordValue === '') {
        setError(password, 'Password is required');
        isValid = false;
    } else if (passwordValue.length < 8 ) {
        setError(password, 'Password must be at least 8 characters');
        isValid = false;
    } else if (!/[a-z]/.test(passwordValue)) {
        setError(password, 'Password must contain at least one lowercase character');
        isValid = false;
    } else if (!/[A-Z]/.test(passwordValue)) {
        setError(password, 'Password must contain at least one uppercase character');
        isValid = false;
    } else if (!/[0-9]/.test(passwordValue)) {
        setError(password, 'Password must contain at least one number');
        isValid = false;
    } else {
        setSuccess(password);
    }

    if(password2Value === '') {
        setError(password2, 'Please confirm your password');
        isValid = false;
    } else if (password2Value !== passwordValue) {
        setError(password2, "Passwords do not match");
        isValid = false;
    } else {
        setSuccess(password2);
    }

    if(isValid === true) {
        // Hash the password before submitting the form
        fetch(`https://api.hashify.net/hash/sha256/hex?value=${passwordValue}`)
            .then(response => response.json())
            .then(data => {
                let hashedPassword = data.Digest;
                password.value = hashedPassword;
                password2.value = hashedPassword;
                document.getElementById('registerform').submit();
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
};