document.addEventListener("DOMContentLoaded", function() {

    // Create person
    document.getElementById("createPersonForm").addEventListener("submit", function(event) {
        event.preventDefault();
        const nationalId = document.getElementById("national_id").value;
        const name = document.getElementById("name").value;
        const family = document.getElementById("family").value;
        const gender = document.getElementById("gender").value;
        const mobile = document.getElementById("mobile").value;
        const email = document.getElementById("email").value;
        const birthDate = document.getElementById("birthDate").value;
        const conscription = document.getElementById("conscription").checked;
        
        const accountData = {
            national_id: nationalId,
            name: name,
            family: family,
            gender: gender === "MALE" ? "Male" : "Female", // Convert to correct enum value
            mobile: mobile,
            email: email,
            birthDate: birthDate,
            conscription: conscription
    };
    
        // Retrieve the token from localStorage
   		const token = localStorage.getItem('token');

        fetch("/persons/create_new_person", {
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
        return response.text();
    })
		.then(data => {
			alert(`${data}`);
			document.getElementById("createPersonForm").reset();
        });
    });

    // Delete person
    document.getElementById("deletePersonForm").addEventListener("submit", function(event) {
        event.preventDefault();
        const nationalId = document.getElementById("delete_national_id").value;
        
        // Retrieve the token from localStorage
   		const token = localStorage.getItem('token');

        fetch("/persons/delete_a_person", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}` // Add the token here
            },
            body: JSON.stringify({ national_id: nationalId }),
        })
        .then(response => response.text())
        .then(data => {
			alert(`${data}`);
            document.getElementById("deletePersonForm").reset();
        });
    });

    // Update person
    document.getElementById("updatePersonForm").addEventListener("submit", function(event) {
        event.preventDefault();

        const nationalId = document.getElementById("new_national_id").value;
        const newName = document.getElementById("new_name").value;
        const newFamily = document.getElementById("new_family").value;
        const newMobile = document.getElementById("new_mobile").value;
        const newEmail = document.getElementById("new_email").value;
        const newBirthDate = document.getElementById("new_birthDate").value;
        const newGender = document.getElementById("new_gender").value;
        const newConscription = document.getElementById("new_conscription").checked;

        const personData = {
		invoiceRequest: { national_id: nationalId },
        updatedPerson: {
            national_id: nationalId,
            name: newName,
            family: newFamily,
            gender: newGender === "MALE" ? "Male" : "Female", 
            mobile: newMobile,
            email: newEmail,
            birthDate: newBirthDate,
            conscription: newConscription
       }
       };
       
        // Retrieve the token from localStorage
   		const token = localStorage.getItem('token');

        fetch("/persons/update_a_person", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}` // Add the token here
            },
            body: JSON.stringify(personData),
        })
        .then(response => {
        if (!response.ok) {
            throw new Error('خطای سرور. لطفا مجدد تلاش کنید.' + response.statusText);
        }
        return response.text();
    	})
        .then(data => {
			alert(`${data}`);
            document.getElementById("updatePersonForm").reset();
        });
    });

    // View person's accounts
    document.getElementById("viewAccountsForm").addEventListener("submit", function(event) {
        event.preventDefault();
        const nationalId = document.getElementById("person_national_id").value;
        
        // Retrieve the token from localStorage
   		const token = localStorage.getItem('token');

        fetch("/persons/get_person_accounts_by_id", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}` // Add the token here
            },
            body: JSON.stringify({ national_id: nationalId }),
        })
        .then(response => response.json())
        .then(data => {
	    	// Store data in session storage to access it on the new page
    		sessionStorage.setItem('accountData', JSON.stringify(data));
    		// Redirect to the results page
            window.location.href = 'viewPersonAccounts.html';
            document.getElementById("viewAccountsForm").reset();
        });
    });

    // Get all persons
 /*   document.getElementById("getAllPersons").addEventListener("click", function() {
        fetch("/persons/get_all_persons")
        .then(response => response.json())
        .then(data => {
            document.getElementById("allPersonsResult").innerHTML = JSON.stringify(data, null, 2);
        });
    }); */

    // View person's information
    document.getElementById("viewPersonForm").addEventListener("submit", function(event) {
        event.preventDefault();
        const nationalId = document.getElementById("view_person_national_id").value;
        
        const accountData = {
		national_id: nationalId
    	};
    	
        // Retrieve the token from localStorage
   		const token = localStorage.getItem('token');
   		
        fetch("/persons/get_person_by_id", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}` // Add the token here
            },
            body: JSON.stringify(accountData),
        })
        .then(response => response.json())
        .then(data => {
        	sessionStorage.setItem('accountData', JSON.stringify(data));
    		// Redirect to the results page
    		window.location.href = 'viewPersonResult.html'; 
    		document.getElementById("viewPersonForm").reset();
        });
    });

    // Expose the showSection function globally for navigation
    window.showSection = showSection;
});
