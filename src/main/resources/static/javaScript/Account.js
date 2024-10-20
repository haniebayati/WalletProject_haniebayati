document.addEventListener("DOMContentLoaded", function () {

// Create account
document.getElementById("createAccountForm").addEventListener("submit", function (event) {
    event.preventDefault();
    
    // Retrieve values from form
    const nationalId = document.getElementById("national_id").value;
    const name = document.getElementById("name").value;
    const family = document.getElementById("family").value;
    const gender = document.getElementById("gender").value;
    const mobile = document.getElementById("mobile").value;
    const email = document.getElementById("email").value;
    const birthDate = document.getElementById("birthDate").value;
    const conscription = document.getElementById("conscription").checked;

    // Create account data
    const accountData = {
        person: {
            national_id: nationalId,
            name: name,
            family: family,
            gender: gender === "MALE" ? "Male" : "Female", // Convert to correct enum value
            mobile: mobile,
            email: email,
            birthDate: birthDate,
            conscription: conscription
        }
    };
    
   // Retrieve the token from localStorage
   const token = localStorage.getItem('token');

    // Send account data to the server
    fetch("/accounts/create_new_account", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}` // Add the token here
        },
        body: JSON.stringify(accountData),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('خطای سرور. لطفا مجدد تلاش کنید.' + response.statusText);
        }
        return response.json();
    })
	
	.then(data => {
    // Store data in session storage to access it on the new page
    sessionStorage.setItem('accountData', JSON.stringify(data));
    // Redirect to the results page
    window.location.href = 'createAccountResult.html'; 
    document.getElementById("createAccountForm").reset();
 
	})

	.catch(error => {
    console.error('مشکلی پیش آمده است:', error);
    document.getElementById("result").innerHTML = `<p>مشکلی پیش آمده است: ${error.message}</p>`;
	});
});


    // Delete account
    document.getElementById("deleteAccountForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const accountNumber = document.getElementById("delete_account_number").value;
        
        // Retrieve the token from localStorage
   		const token = localStorage.getItem('token');

        fetch("/accounts/delete_an_account", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}` // Add the token here
            },
            body: JSON.stringify({ account_number: accountNumber }),
        })
         .then(response => response.text())
         .then(data => {
			alert(`${data}`);
            document.getElementById("deleteAccountForm").reset();
        });
    });

    // Update account
    document.getElementById("updateAccountForm").addEventListener("submit", function (event) {
        event.preventDefault();

        const accountNumber = document.getElementById("update_account_number").value;
        const nationalId = document.getElementById("update_national_id").value;
        const name = document.getElementById("update_name").value;
        const family = document.getElementById("update_family").value;
        const gender = document.getElementById("update_gender").value;
        const mobile = document.getElementById("update_mobile").value;
        const email = document.getElementById("update_email").value;
        const birthDate = document.getElementById("update_birthDate").value;
        const conscription = document.getElementById("update_conscription").checked;
   
        const accountData = {
		invoiceRequest: { account_number: accountNumber },
        updatedAccount: {
			person: {
            national_id: nationalId,
            name: name,
            family: family,
            gender: gender === "MALE" ? "Male" : "Female", 
            mobile: mobile,
            email: email,
            birthDate: birthDate,
            conscription: conscription
            }
       }
       };
       
        // Retrieve the token from localStorage
   		const token = localStorage.getItem('token');

        fetch("/accounts/update_an_account", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}` // Add the token here
            },
        body: JSON.stringify(accountData),
        })
        .then(response => {
        if (!response.ok) {
            throw new Error('خطای سرور. لطفا مجدد تلاش کنید.' + response.statusText);
        }
        return response.text();
    	})
            .then(data => {
            	alert(`${data}`);
				document.getElementById("updateAccountForm").reset();
            });
    });

    // View an account
    document.getElementById("viewAccountForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const accountNumber = document.getElementById("view_account_number").value;
        
    	const accountData = {
			account_number: accountNumber
    };
    
        // Retrieve the token from localStorage
   		const token = localStorage.getItem('token');

        fetch("/accounts/get_account_by_id", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}` // Add the token here
            },
            body: JSON.stringify(accountData),
        })
            .then(response => response.json())
                    
     .then(data => {
    // Store data in session storage to access it on the new page
    sessionStorage.setItem('accountData', JSON.stringify(data));
    
    // Redirect to the results page
    window.location.href = 'viewAccountResult.html'; 
    document.getElementById("viewAccountForm").reset();
	})

	.catch(error => {
    console.error('مشکلی پیش آمده است:', error);
    document.getElementById("result").innerHTML = `<p>مشکلی پیش آمده است: ${error.message}</p>`;
	});
});

    // View all accounts
 //   document.getElementById("getAllAccounts").addEventListener("click", function () {
 //       fetch("/accounts/get_all_accounts")
 //           .then(response => response.json())
 //           .then(data => {
 //               document.getElementById("allAccountsResult").innerHTML = JSON.stringify(data, null, 2);
 //           });
 //   });

    // Expose showSection function to the global scope for navigation buttons
    window.showSection = showSection;
});
