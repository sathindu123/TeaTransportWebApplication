
const descc = document.getElementById("description1");

document.addEventListener("DOMContentLoaded", () => {
   genarateComplaintID();

});

descc.addEventListener("input", async function() {
    description1.style.border ="";
    const quary = this.value.trim();


});


//machn pandr 4yi ganak welawa.. update ekath ekapara weda karam kollo..

document.getElementById("updatebtn").addEventListener("click", async (event) => {
    event.preventDefault();
    const cmID = document.getElementById("complaintId").value;
    const des =  document.getElementById("description1").value;

    try {

        const response = await fetch(`http://localhost:8080/auth/updateComplaint/${cmID}/${des}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) {
            Swal.fire({
                icon: 'error',
                title: 'Complaint Update Failed!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
        }else {
            Swal.fire({
                icon: 'success',
                title: 'Complaint Update Successfully!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
            genarateComplaintID();
        }

    }catch (error) {
        Swal.fire("Error Delete Complaint! ");
    }
});


//delete btn eke function eka

document.getElementById("deletebtn").addEventListener("click", async (event) => {
    event.preventDefault();
    const cmID =document.getElementById("complaintId").value;

    try {
        const response = await fetch(`http://localhost:8080/auth/deleteComplaint/${cmID}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Complaint Delete Successfully!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
            genarateComplaintID();
        }else {
            Swal.fire({
                icon: 'error',
                title: 'Complaint Delete Failed!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
        }

    }catch (error) {
        Swal.fire("Error Delete Complaint! ");
    }
});



// meka machan iventlistner eka hodt balaganin.. re 3yi ganak yanko den.. balagin

function addRowClickListeners() {
    const table = document.getElementById("complaintsTableBody");
    const rows = table.querySelectorAll("tr");

    rows.forEach(row => {
        row.addEventListener("click", () => {
            const complaintId = row.cells[0].innerText; // first cell = complaintId
            const description = row.cells[1].innerText; // second cell = description
            const status = row.cells[2].innerText;      // third cell = status
            const remarks = row.cells[3].innerText;     // fourth cell = remarks

            // Set values to the form inputs
            document.getElementById("complaintId").value = complaintId;
            document.getElementById("description1").value = description;

            submitbtn.disabled = true;
            deletebtn.disabled = false;
            updatebtn.disabled = false;

        });
    });
}


async function countGenarate() {
    const token = localStorage.getItem("token");

    try {
        const response = await fetch("http://localhost:8080/auth/getCountCom", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
        });

        if (!response.ok) {
            Swal.fire("Failed to load counts!");
            return;
        }


        const counts = await response.json();

        document.getElementById("totalComplaints").textContent = counts[0];
        document.getElementById("pendingComplaints").textContent = counts[1];
        document.getElementById("inProgressComplaints").textContent = counts[2];
        document.getElementById("resolvedComplaints").textContent = counts[3];

    } catch (error) {
        Swal.fire("Error Delete Complaint! ");
    }
}


function genarateComplaintID() {
    description1.style.border = "";
    description1.value = "";
    submitbtn.disabled = false;
    updatebtn.disabled = true;
    deletebtn.disabled = true;
    const complaintIdInput = document.getElementById("complaintId");


    countGenarate();

    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, "0");
    const day = String(now.getDate()).padStart(2, "0");
    const hours = String(now.getHours()).padStart(2, "0");
    const minutes = String(now.getMinutes()).padStart(2, "0");
    const seconds = String(now.getSeconds()).padStart(2, "0");
    const random = Math.floor(Math.random() * 1000); // 0-999


    let lastId = localStorage.getItem("lastComplaintId");
    lastId = lastId ? parseInt(lastId) + 1 : 1;

    const generatedId = `CMPL-${String(lastId).padStart(4, "0")}`;
    complaintIdInput.value = generatedId;


    localStorage.setItem("lastComplaintId", lastId);

    loadTable();
}

async function loadTable() {
    const token = localStorage.getItem("token");

    try {
        const response = await fetch("http://localhost:8080/auth/LoadComplaint", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
        });

        if (!response.ok) {
            Swal.fire("Failed to Load Complaint");
        }

        const data = await response.json();



        const table = document.getElementById("complaintsTableBody");
        table.innerHTML = "";

        data.forEach(cmt => {
            let row = `
                <tr>
                    <td>${cmt.complainId}</td>
                    <td>${cmt.description}</td>
                    <td>${cmt.status}</td>
                    <td>${cmt.remarks}</td>
                    <td>${cmt.date}</td> 
                </tr>
            `;
            table.innerHTML += row;
        });

        addRowClickListeners();
    }catch (error) {
        Swal.fire("Error loading orders:");
    }
}

document.getElementById("submitbtn").addEventListener("click", async (event) => {
    event.preventDefault();

    const description = document.getElementById("description1").value;

    if (description == ""){
        description1.style.border = "2px solid red";
        showNotification("Descprion value is Empty", "error");
        return;
    }

    const compId = document.getElementById("complaintId").value;
    const today = new Date();

    const token = localStorage.getItem("token");

    const status = "Pending";
    const remark = "Not updated";

    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const day = String(today.getDate()).padStart(2, "0");

    const dateStr = `${year}-${month}-${day}`;

    let array = [];

    const complaint = {
        complainId: compId,
        description: description,
        status: status,
        remarks: remark,
        date: dateStr,
    };

    try {
        const response = await fetch("http://localhost:8080/auth/submit", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(complaint)
        });

        if (response.ok) {
            const data = await response.text();


            Swal.fire({
                icon: 'success',
                title: 'Complaint Submit Successfully!',
                text: 'Message sent!',
                showConfirmButton: false,
                timer: 2000
            });
            description.value = "";
            genarateComplaintID();
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Failed to Submit',
                text: 'Message not sent!',
                showConfirmButton: false,
                timer: 2000
            });
        }

    } catch (error) {
        alert("Network error");
    }
});
