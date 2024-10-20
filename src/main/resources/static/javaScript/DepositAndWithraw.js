document.addEventListener("DOMContentLoaded", function () {

document.getElementById("depositForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const accountType = document.getElementById("accountType").value;
    const accountNumber = document.getElementById("accountNumber").value;
    const amount = document.getElementById("amount").value;

    let url = "";
    let requestData = {};

    if (accountType === "iban") {
        url = "/invoice/deposit_by_iban";
        requestData = {
            iban: accountNumber,  // Send IBAN field
            amount: amount
        };
    } else if (accountType === "accountNumber") {
        url = "/invoice/deposit_by_accountNumber";
        requestData = {
            account_number: accountNumber,  // Send Account Number field
            amount: amount
        };
    }
    
        // Retrieve the token from localStorage
   		const token = localStorage.getItem('token');

    // Send the deposit request to the server
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}` // Add the token here
        },
        body: JSON.stringify(requestData),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('خطا در برقراری ارتباط با سرور');
        }
        return response.json();
    })
    .then(data => {
        alert('واریز با موفقیت انجام شد.');
        // Store data in session storage to access it on the new page
        sessionStorage.setItem('requestData', JSON.stringify(data));
    //    document.getElementById("depositForm").reset(); 
        // Redirect to the results page
        window.location.href = 'invoiceResult.html'; 
        document.getElementById("deposittForm").reset();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('خطا در ارسال درخواست واریز.');
    });
});


// withdraw form
document.getElementById("withdrawForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const withdrawAccountType = document.getElementById("withdrawAccountType").value;
    const withdrawAccountNumber = document.getElementById("withdrawAccountNumber").value;
    const withdrawAmount = document.getElementById("withdrawAmount").value;

    let url = "";
    let requestData = {};

    // Determine the URL and request data based on the account type
    if (withdrawAccountType === "iban") {
        url = "/invoice/withdraw_by_iban";
        requestData = {
            iban: withdrawAccountNumber,  // Send IBAN field
            amount: withdrawAmount
        };
    } else if (withdrawAccountType === "accountNumber") {
        url = "/invoice/withdraw_by_accountNumber";
        requestData = {
            account_number: withdrawAccountNumber,  // Send Account Number field
            amount: withdrawAmount
        };
    }
    
        // Retrieve the token from localStorage
   		const token = localStorage.getItem('token');

    // Send the withdraw request to the server
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}` // Add the token here
        },
        body: JSON.stringify(requestData),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('خطا در برقراری ارتباط با سرور');
        }
        return response.json();
    })
    .then(data => {
        alert('برداشت با موفقیت انجام شد.');
        // Store data in session storage to access it on the new page
        sessionStorage.setItem('requestData', JSON.stringify(data));
     //   document.getElementById("withdrawForm").reset();
        // Redirect to the results page
        window.location.href = 'invoiceResult.html'; 
        document.getElementById("withdrawwForm").reset();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('خطا در ارسال درخواست برداشت.');
    });
});

});
