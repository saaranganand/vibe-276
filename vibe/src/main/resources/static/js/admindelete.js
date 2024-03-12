// Get the checkboxes
const checkboxes = document.querySelectorAll('input[type="checkbox"]');

// Get the delete button
const deleteButton = document.getElementById('deleteButton');

// Add event listener to the delete button
deleteButton.addEventListener('click', function(event) {
    // Check if any checkboxes are checked
    const checkedBoxes = Array.from(checkboxes).filter(checkbox => checkbox.checked);

    // If no checkboxes are checked, show an error message and prevent form submission
    if (checkedBoxes.length === 0) {
        event.preventDefault();
        alert('Please select at least one item to delete.');
    }
});