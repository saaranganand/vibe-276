const form = document.getElementById('loginform');
const nameoremail = document.getElementById('nameoremail');
const password = document.getElementById('password');

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
    const nameemailValue = nameoremail.value.trim();
    const passwordValue = password.value.trim();

    isValid = true;

    if(nameemailValue === '') {
        setError(nameoremail, 'Username or email is required');
        isValid = false;
    } else {
        setSuccess(nameoremail);
        isValid = true;
    }

    if(passwordValue === '') {
        setError(password, 'Password is required');
        isValid = false;
    } else if (passwordValue.length < 8 ) {
        setError(password, 'Password is invalid');
        isValid = false;
    } else if (!/[a-z]/.test(passwordValue)) {
        setError(password, 'Password is invalid');
        isValid = false;
    } else if (!/[A-Z]/.test(passwordValue)) {
        setError(password, 'Password is invalid');
        isValid = false;
    } else if (!/[0-9]/.test(passwordValue)) {
        setError(password, 'Password is invalid');
        isValid = false;
    } else {
        setSuccess(password);
        isValid = true;
    }

    if(isValid === true)
        document.getElementById('loginform').submit();
};