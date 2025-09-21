const custID = document.getElementById("complaintId").value;

const months = [
    "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
    "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
];


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

        if (dDate === null || dDate === "" || id === null || id === "") {
            showNotification("CustomerID OR Date NULL Please Cheack!", "error");
            return;
        }

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
            const c_must = document.getElementById("c_must").value ="";
            const c_disc = document.getElementById("c_disc").value ="";
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

document.getElementById("deletebtn").addEventListener("click", async function () {
    const confirmed = await confirmDelete();
    if (!confirmed) return;


    try {

        const dDate = document.getElementById("Date").value;
        const c_must = document.getElementById("c_must").value;
        const c_disc = document.getElementById("c_disc").value;
        const id = document.getElementById("complaintId").value;

        if (dDate === null || dDate === "" || id === null || id === "") {
            showNotification("CustomerID OR Date NULL Please Cheack!", "error");
            return;
        }

        const TeaLeaf = {
            custId: id,
            date: dDate,
            goldLeafAmount: c_must,
            goodLeafAmount: c_disc,
        };

        const response = await fetch("http://localhost:8080/auth/DeleteTeaLeaf", {
            method: "DELETE",
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
                title: 'Delete Successfully!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
            const c_must = document.getElementById("c_must").value ="";
            const c_disc = document.getElementById("c_disc").value ="";
        }else {
            Swal.fire({
                icon: 'error',
                title: 'Delete Unccessfully!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
        }

    }catch (error) {
        showNotification(error, "error");
    }
});


document.getElementById("Abtn").addEventListener("click", async function () {
    try {
        const dDate = document.getElementById("Date").value;
        const dateObj = new Date(dDate);

        let total = document.getElementById("AID").value;
        const warika = document.getElementById("wrikA").value;
        const id = document.getElementById("complaintId").value;

        let price = 0;
        console.log(warika);

        if (warika === "0" || warika === null ||warika === ""){
            showNotification("Please Input Installment!", "error");
            return;
        }else if (warika > 1){
            price = total/warika;

            let dates = [];

            for (let i = 0; i < warika; i++) {
                let newDate = new Date(dateObj);
                newDate.setMonth(dateObj.getMonth() + i);

                let year = newDate.getFullYear();
                let month = String(newDate.getMonth() + 1).padStart(2, "0");
                let day = String(newDate.getDate()).padStart(2, "0");

                const ss =(`${year}-${month}-${day}`);

                const dateObj1 = new Date(ss);

                const monthName1 = months[dateObj1.getMonth()];
                const year1 = dateObj1.getFullYear();


                const formatted = `${monthName1}${year1}`;
                dates.push(formatted);

                if (dDate === null || dDate === "" || id === null || id === "") {
                    showNotification("CustomerID OR Date NULL Please Cheack!", "error");
                    return;
                }

                const TeaLeaf = {
                    custId: id,
                    date: dDate,
                    month: dates[i],
                    price: total,
                    monthPrice: price,
                };

                const response = await fetch("http://localhost:8080/auth/SubmitAdvance", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(TeaLeaf)
                });

                const data = await response.text();

                if (response.ok){
                    Swal.fire({
                        icon: 'success',
                        title: 'Successfully!',
                        text: 'Message sent!',
                        showConfirmButton: false,
                        timer: 2000
                    });
                    const c_must = document.getElementById("AID").value ="";
                    const c_disc = document.getElementById("wrikA").value ="";
                }else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Unccessfully!',
                        text: 'Message sent!',
                        showConfirmButton: false,
                        timer: 2000
                    });
                }

            }


        }else {
            price = total;
        }


        const monthName = months[dateObj.getMonth()];
        const year = dateObj.getFullYear();


        const formatted = `${monthName}${year}`;


        if (dDate === null || dDate === "" || id === null || id === "") {
            showNotification("CustomerID OR Date NULL Please Cheack!", "error");
            return;
        }

        const TeaLeaf = {
            custId: id,
            date: dDate,
            month: formatted,
            price: total,
            monthPrice: price,
        };

        const response = await fetch("http://localhost:8080/auth/SubmitAdvance", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(TeaLeaf)
        });

        const data = await response.text();


        if (response.ok){
            Swal.fire({
                icon: 'success',
                title: 'Successfully!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
            const c_must = document.getElementById("AID").value ="";
            const c_disc = document.getElementById("wrikA").value ="";
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


document.getElementById("Adeletebtn").addEventListener("click", async (e) => {
    const confirmed = await confirmDelete();
    if (!confirmed) return;

    const dDate = document.getElementById("Date").value;

    const id = document.getElementById("complaintId").value;
    let total = document.getElementById("AID").value;
    const warika = document.getElementById("wrikA").value;


    if (dDate === null || dDate === "" || id === null || id === "") {
        showNotification("CustomerID OR Date NULL Please Cheack!", "error");
        return;
    }

    const TeaLeaf = {
        custId: id,
        date: dDate,
        price: total,
    };

    try {
        const response = await fetch(`http://localhost:8080/auth/deleteAdvance`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(TeaLeaf)
        });

        const data = await response.text();

        if (response.ok){
            Swal.fire({
                icon: 'success',
                title: 'Delete Successfully!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
            const c_must = document.getElementById("AID").value ="";
            const c_disc = document.getElementById("wrikA").value ="";
        }else {
            Swal.fire({
                icon: 'error',
                title: response.message,
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
        }

    }catch (error) {
        showNotification(error, "error");
    }


});


document.getElementById("Tbtn").addEventListener("click", async (e) => {
    const pID = document.getElementById("PIDT").value;
    const CIDT = document.getElementById("CIDT").value;
    const price = document.getElementById("TID").value;

    const dDate = document.getElementById("Date").value;
    const id = document.getElementById("complaintId").value;

    if (dDate === null || dDate === "" || CIDT === null || CIDT === "" ||  price === "" || price === null) {
        showNotification("Input All data. Cheack And Try Again", "error");
        return;
    }

    if (pID === null || pID === "" || id === null || id === "") {
        showNotification("CustomerID OR Date NULL Please Cheack!", "error");
        return;
    }

    const TeaLeaf = {
        custId: id,
        date: dDate,
        productId: pID,
        quntity: CIDT,
        totalPrice: price,
    };

    try {
        const response = await fetch(`http://localhost:8080/auth/submitTeaPacket`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(TeaLeaf)
        });

        const data = await response.text();

        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Successfully!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
            document.getElementById("PIDT").value = "";
            document.getElementById("CIDT").value ="";
            document.getElementById("TID").value="";
        } else {
            Swal.fire({
                icon: 'error',
                title: response.message,
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
        }

    } catch (error) {
            showNotification(error, "error");
        }





    });


async function confirmDelete() {
    const result = await Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    });


    return result.isConfirmed;
}