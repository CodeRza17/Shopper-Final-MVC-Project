// static/front/themes/js/product-detail.js
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form[action='/addToCart']");
    const sizeSelect = document.getElementById("size");

    form.addEventListener("submit", function (e) {
        if (!sizeSelect.value) {
            e.preventDefault();
            alert("Please select a size!");
        }
    });
});
