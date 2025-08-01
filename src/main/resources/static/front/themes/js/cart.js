document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("rewardPointForm");
    const rewardInput = document.getElementById("rewardPointInput");
    const maxRewardSpan = document.getElementById("maxRewardPoint");
    const maxReward = maxRewardSpan ? parseInt(maxRewardSpan.textContent) : 0;

    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const rewardValue = parseInt(rewardInput.value);

        if (isNaN(rewardValue) || rewardValue <= 0) {
            alert("Please enter a valid reward point.");
            return;
        }

        if (rewardValue > maxReward) {
            alert("You cannot use more than " + maxReward + " reward points.");
            return;
        }

        // POST request
        fetch('/cart/use-reward', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'rewardPoint=' + rewardValue
        }).then(response => {
            if (response.ok) {
                return response.text(); // və ya response.json() əgər JSON-dursa
            } else {
                throw new Error("Something went wrong");
            }
        }).then(data => {
            // Məsələn, yenidən yüklə
            window.location.reload();
        }).catch(err => {
            alert("Error using reward point: " + err.message);
        });
    });
});
