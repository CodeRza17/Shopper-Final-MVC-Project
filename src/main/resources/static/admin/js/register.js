document.addEventListener("DOMContentLoaded", function () {
    const roleSelect = document.getElementById("roleSelect");
    const companyField = document.getElementById("companyField");
    const ownerCodeField = document.getElementById("ownerCodeField");
    const ownerCodeInput = ownerCodeField.querySelector("input[name='brandOwnerCode']");

    function toggleFields() {
        const selectedRole = roleSelect.value;

        // Şirkət sahəsini göstər
        if (selectedRole === "ROLE_REQUEST" || selectedRole === "ROLE_SUPER_ADMIN") {
            companyField.style.display = "block";
        } else {
            companyField.style.display = "none";
        }

        // Brand Owner Code sahəsi yalnız SUPER_ADMIN üçün göstərilsin
        if (selectedRole === "ROLE_SUPER_ADMIN") {
            ownerCodeField.style.display = "block";
            ownerCodeInput.setAttribute("required", "required");
        } else {
            ownerCodeField.style.display = "none";
            ownerCodeInput.removeAttribute("required"); // burası önəmlidir!
        }
    }

    roleSelect.addEventListener("change", toggleFields);
    toggleFields(); // İlk yükləmədə yoxla
});
