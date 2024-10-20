let phone;
let otp;
const messageDiv = document.getElementById('message');
const phoneSection = document.getElementById('phoneSection');
const otpSection = document.getElementById('otpSection');

//request for send otp code
document.getElementById('sendCodeButton').addEventListener('click', async () => {
    phone = document.getElementById('phone').value;

    if (phone) {
        try {
            const response = await fetch('/walletAuthentication/users_authentication', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ mobile: phone })
            });

            if (response.ok) {
                const otp = await response.json();
                messageDiv.textContent = 'کد تایید ارسال شد.';
                phoneSection.style.display = 'none';
                otpSection.style.display = 'block';
            } else {
                messageDiv.textContent = 'خطا در ارسال کد تایید. لطفا دوباره تلاش کنید.';
            }
        } catch (error) {
            messageDiv.textContent = 'خطا در برقراری ارتباط با سرور.';
        }
    } else {
        messageDiv.textContent = 'لطفا شماره تلفن همراه خود را وارد کنید.';
    }
});

// verify otp code and get jwt token
document.getElementById('loginButton').addEventListener('click', async () => {
    otp = document.getElementById('otp').value;

    if (otp) {
        try {
            const response = await fetch('/walletAuthentication/verify_authentication', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ mobile: phone, otpCode: otp })
            });

            if (response.ok) {
                const data = await response.json();
                 otpSection.style.display = 'none';

                 localStorage.setItem('token', data.token);
                 window.location.href = "/Menu.html";
                 
            } else {
                messageDiv.textContent = 'کد تایید نادرست یا اتمام انقضای کد تایید. لطفا دوباره تلاش کنید.';
                    setTimeout(function() {
      				  window.location.href = "/Login.html";
 						   }, 2000);
            }
        } catch (error) {
            messageDiv.textContent = 'خطا در برقراری ارتباط با سرور.';
        }
    } else {
        messageDiv.textContent = 'لطفا کد تایید را وارد کنید.';
    }
});
