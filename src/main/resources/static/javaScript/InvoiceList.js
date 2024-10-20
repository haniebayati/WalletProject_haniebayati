document.addEventListener('DOMContentLoaded', function() {
	
    // Transaction List
    document.getElementById('transactionForm').addEventListener('submit', function(event) {
        event.preventDefault(); 

        const accountType = document.getElementById('accountType').value;
        const accountNumber = document.getElementById('accountNumber').value;
        
        let url = "";
    	let requestData = {};
    	
    	if (accountType === "iban") {
        url = "/invoice/invoice_list_by_iban";
        requestData = {
            iban: accountNumber,  // Send IBAN field
        };
    	} else if (accountType === "accountNumber") {
        url = "/invoice/invoice_list_by_accountNumber";
        requestData = {
            account_number: accountNumber,  // Send Account Number field
        };
    	}
    	
    	// Retrieve the token from localStorage
   		const token = localStorage.getItem('token');
    	
    // Send the invoice list request to the server
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
            alert('شما اجازه دسترسی به لیست تراکنش ها را ندارید.');
            document.getElementById("transactionForm").reset();
        }
        return response.json();
    })
    .then(data => {
        // Store data in session storage to access it on the new page
        sessionStorage.setItem('requestData', JSON.stringify(data));

        // Redirect to the results page
        window.location.href = 'invoiceListResult.html'; 
        document.getElementById("transactionForm").reset();
    })
    .catch(error => {
        console.error('Error:', error);
       // alert('خطا در ارسال درخواست لیست تراکنش ها.');
    });
        
    });
   
});
