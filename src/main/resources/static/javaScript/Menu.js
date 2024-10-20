function handleTaskClick(task) {
  //  alert(`شما گزینه '${task}' را انتخاب کردید.`);


	if (task === 'عملیات مربوط به شخص') {
        window.location.href = 'Person.html'; 
    } else if (task === 'عملیات مربوط به حساب') {
        window.location.href = 'Account.html'; 
    } else if (task === 'عملیات واریز و برداشت') {
        window.location.href = 'DepositAndWithraw.html';  
    } else if (task === 'لیست تراکنش ها') {
        window.location.href = 'InvoiceList.html';  
    } else {
        alert('گزینه انتخاب شده معتبر نیست.');
    }
    
}

