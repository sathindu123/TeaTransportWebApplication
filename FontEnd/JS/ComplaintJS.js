
document.addEventListener("DOMContentLoaded", () => {
   genarateComplaintID();
});


function genarateComplaintID() {
    const complaintIdInput = document.getElementById("complaintId");

    // Generate unique ID: CMPL-YYYYMMDD-HHMMSS-RandomNumber
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

        console.log(data);

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

    }catch (error) {
        Swal.fire("Error loading orders:");
    }
}

document.getElementById("submitbtn").addEventListener("click", async (event) => {
    event.preventDefault();

    const description = document.getElementById("description1").value;
    console.log(description);
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
