const custID = document.getElementById("complaintId").value;


document.addEventListener("DOMContentLoaded", function() {

    // Today Date Generate
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');


    const dateStr = `${year}-${month}-${day}`;


    document.getElementById("Date").value = dateStr;

});

document.getElementById("okbtn").addEventListener("click", async function () {
    try {

        const  id = document.getElementById("complaintId").value;

        const response = await fetch(`http://localhost:8080/auth/LoadName/${id}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        const customerName = await response.text();

        console.log(customerName);

        if (!response.ok || customerName === "Customer ID Not Found") {
            showNotification(customerName, "error");
            document.getElementById("complaintId").value = "";
            document.getElementById("CustomerName").value = "";
            return;
        }else {
            document.getElementById("CustomerName").value = customerName;
        }



    } catch (error) {
        showNotification(error, "error");
    }

});

document.getElementById("submitbtn").addEventListener("click", async function () {
    try {
        const dDate = document.getElementById("Date").value;
        const c_must = document.getElementById("c_must").value;
        const c_disc = document.getElementById("c_disc").value;
        const id = document.getElementById("complaintId").value;
        const TeaLeaf = {
            custId: id,
            date: dDate,
            goldLeafAmount: c_must,
            goodLeafAmount: c_disc,
        };

        const response = await fetch("http://localhost:8080/auth/submitTeaLeaf", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(TeaLeaf)
        });

        const data = await response.text();
        console.log(data);

        if (response.ok){
            Swal.fire({
                icon: 'success',
                title: 'Successfully!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
        }else {
            Swal.fire({
                icon: 'error',
                title: 'Unccessfully!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
        }

    }catch (error) {
        showNotification(error, "error");
    }
});