const form = document.getElementById('registerEmailForm');
const email = document.getElementById('email');

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

const isValidEmail = email => {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

const validateInputs = () => {
    const emailValue = email.value.trim();

    if(emailValue === '') {
        console.log("FAIL");
        setError(email, 'Email is required');
    } else if (!isValidEmail(emailValue)) {
        console.log("FAIL");
        setError(email, 'Provide a valid email address');
    } else {
        console.log("SUCCESS");
        setSuccess(email);
        document.getElementById('registerEmailForm').submit();
    }
};

const storeEmail = () => {
    const emailValue = email.value.trim();

    if(emailValue === '') {
        console.log("FAIL");
        setError(email, 'Email is required');
    } else if (!isValidEmail(emailValue)) {
        console.log("FAIL");
        setError(email, 'Provide a valid email address');
    } else {
        console.log("SUCCESS");
        setSuccess(email);
        document.getElementById('registerEmailForm').submit();
    }
};