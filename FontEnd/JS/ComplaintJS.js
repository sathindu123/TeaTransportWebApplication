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
    lastId = lastId ? parseInt(lastId) + 1 : 1; // නව ID generate


    const generatedId = `CMPL-${String(lastId).padStart(4, "0")}`;
    complaintIdInput.value = generatedId;

    localStorage.setItem("lastComplaintId", lastId);
}

document.getElementById("description").addEventListener("click", () => {
   const description = document.getElementById("description");
   const compId = document.getElementById("complaintId");
    const today = new Date();

    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const day = String(today.getDate()).padStart(2, "0");
    
    const dateStr = `${year}-${month}-${day}`;
});