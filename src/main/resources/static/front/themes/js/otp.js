// 2 dəqiqə (120 saniyə) timer və resend button idarəsi

let waitTime = 120; // saniyə ilə

function startTimer() {
    const resendBtn = document.getElementById('resendBtn');
    const timerSpan = document.getElementById('timer');

    if (!resendBtn || !timerSpan) return;

    resendBtn.disabled = true;  // başda resend düyməsini bağla

    const interval = setInterval(() => {
        if (waitTime <= 0) {
            clearInterval(interval);
            resendBtn.disabled = false; // timer bitəndə düyməni aktiv et
            timerSpan.textContent = ''; // timer mesajını sil
        } else {
            timerSpan.textContent = `Yenidən OTP göndərmək üçün gözlə: ${waitTime} saniyə`;
            waitTime--;
        }
    }, 1000);
}

window.onload = startTimer;
