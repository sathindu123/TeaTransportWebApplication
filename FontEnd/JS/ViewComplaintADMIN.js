let currentPage = 0;   // use camelCase everywhere
let pageSize = 5;

document.getElementById("logOuntbtn").addEventListener("click", () => {
    window.location.replace("Login.html");


});

document.addEventListener("DOMContentLoaded", function () {
    loadTable(currentPage)
    setupPagination();
});

document.getElementById("updatebtn").addEventListener("click", async function () {

    const compId = document.getElementById("complain-id").value;
    const remark = document.getElementById("remark").value;
    const status = document.getElementById("statusDropDown").value;

    const complaint = {
        complainId: compId,
        status: status,
        remarks: remark,
    };

    try {
        const response = await fetch("http://localhost:8080/auth/updateComplaintsAdmin", {
            method: "PUT",
            headers: {
                "content-type": "application/json"
            },
            body: JSON.stringify(complaint)
        });

        if (response.ok) {
            showNotification(response, "success");
        }else {
            showNotification(response, "error");
            return;
        }

        loadTable(currentPage);

    } catch (e) {
        showNotification(e, "error");
    }
})

async function loadTable(currentPage) {
    try {
        const response = await fetch(`http://localhost:8080/auth/LoadComplaintsAdmin?page=${currentPage}&size=${pageSize}`,  {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        const data = await response.json();

        console.log(data)

        if (response.ok) {

            const table = document.getElementById("complain-tbody-admin");
            table.innerHTML = "";

            data.content.forEach(cmt => {
                let row = `
                <tr>
                    <td>${cmt.complain_id}</td>
                    <td>${cmt.description}</td>
                    <td>${cmt.status}</td>
                    <td>${cmt.remarks}</td>
                    <td>${cmt.date}</td> 
                     <td>
                        <button class="btn-delete" id="deletebtn" onclick="deleteOrder('${cmt.complain_id}')">Delete</button>
                    </td>
                </tr>
            `;
                table.innerHTML += row;
            });



            document.getElementById("pageInfo").innerText =
                `Page ${data.number + 1} of ${data.totalPages}`;


            addRowClickListeners();


        }else {
            showNotification(response, "error");
        }

    }catch (e){
        showNotification(e, "error");
    }
}


function setupPagination() {
    document.getElementById("prevPage").addEventListener("click", function () {
        if (currentPage > 0) {
            currentPage--;
            loadTable(currentPage);
        }
    });

    document.getElementById("nextPage").addEventListener("click", function () {
        currentPage++;
        loadTable(currentPage);

    });
}


function addRowClickListeners() {
    const table = document.getElementById("complain-tbody-admin");
    const rows = table.querySelectorAll("tr");

    rows.forEach(row => {
        row.addEventListener("click", () => {
            const complaintId = row.cells[0].innerText; // first cell = complaintId
            const description = row.cells[1].innerText; // second cell = description
            const status = row.cells[2].innerText;      // third cell = status
            const remarks = row.cells[3].innerText;     // fourth cell = remarks

            // Set values to the form inputs
            document.getElementById("complain-id").value = complaintId;
            document.getElementById("remark").value = remarks;

        });
    });
}

