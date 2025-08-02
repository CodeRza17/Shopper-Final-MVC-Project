document.addEventListener("DOMContentLoaded", function () {
    const roleSelect = document.getElementById("roleSelect");
    const companyField = document.getElementById("companyField");
    const brandSelect = document.getElementById("brandSelect");
    const ownerCodeField = document.getElementById("ownerCodeField");
    const ownerCodeInput = document.getElementById("ownerCodeInput");
    const registerForm = document.getElementById("registerForm");

    function toggleFields() {
        const selectedRole = roleSelect.value;

        // Company field toggle
        if (selectedRole === "ROLE_REQUEST" || selectedRole === "ROLE_SUPER_ADMIN") {
            companyField.style.display = "block";
            brandSelect.disabled = false;
            brandSelect.required = true;
        } else {
            companyField.style.display = "none";
            brandSelect.disabled = true;
            brandSelect.required = false;
            brandSelect.value = "";
        }

        // Owner code field toggle
        if (selectedRole === "ROLE_SUPER_ADMIN") {
            ownerCodeField.style.display = "block";
            ownerCodeInput.disabled = false;
            ownerCodeInput.required = true;
        } else {
            ownerCodeField.style.display = "none";
            ownerCodeInput.disabled = true;
            ownerCodeInput.required = false;
            ownerCodeInput.value = "";
        }
    }

    // Initialize fields on page load
    toggleFields();

    // Add event listener for role change
    roleSelect.addEventListener("change", toggleFields);

    // Handle form submission to prevent unwanted redirects
    registerForm.addEventListener("submit", function(event) {
        // You can add additional validation here if needed
        console.log("Form submitted");
    });
});