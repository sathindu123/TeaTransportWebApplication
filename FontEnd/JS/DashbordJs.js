document.addEventListener("DOMContentLoaded", function () {
    // token / role localStorage එකේ store කරලා තිබෙනවා කියලා assume කරමු
    const role = localStorage.getItem("role");



    const reportLink = document.getElementById("reportLink");
    const adLink = document.getElementById("advanceLink");


    if (role === "ADMIN") {
        if (reportLink) {
            ADreportLink.style.display = "block";
            adLink.style.display = "block";
            reportLink.style.display = "none";
            selIlt.style.display = "none";
            viewOrder.style.display = "block";
            advanceLink.style.display = "none";
            cmLink.style.display = "none";
            cmLinkAdmin.style.display = "block";

        }
    } else if (role === "CUSTOMER"){
        if (reportLink) {
            ADreportLink.style.display = "none";
            reportLink.style.display = "block";
            viewOrder.style.display = "none";
            advanceLink.style.display = "block";
            cmLink.style.display = "block";
            cmLinkAdmin.style.display = "none";
        }
    }

    else {
        if (reportLink) {
            ADreportLink.style.display = "none";
            reportLink.style.display = "none";
            viewOrder.style.display = "none";
            advanceLink.style.display = "none";
            cmLink.style.display = "block";
            cmLinkAdmin.style.display = "none";

        }
    }

});


document.getElementById("logOuntbtn").addEventListener("click", function () {
    window.location.replace("http://localhost:63343/SellItempage.html/Login.html");
})


// Email sender


document.querySelector("button").addEventListener("click", async () => {
    const name = document.querySelector(".name input").value;
    const email = document.querySelector(".email input").value;
    const message = document.querySelector(".message textarea").value;

    const response = await fetch("http://localhost:8080/auth/email",{
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, message })
    });

    if (response.ok) {
        Swal.fire({
            icon: 'success',
            title: 'Success!',
            text: 'Message sent!',
            showConfirmButton: false,
            timer: 2000
        });
        aea.value = "";
        emaile.value = "";
        nameEmail.value = "";
    } else {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Failed to send message.',
            showConfirmButton: false,
            timer: 2000
        });
    }


})



// dashboard.js

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");

    // 🛑 Token එක නැත්තම් -> Login page එකට යවන්න
    if (!token) {
        alert("Please log in first!");
        window.location.href = "Login.html";
        return;
    }
    // Disable Back button
    window.history.pushState(null, "", window.location.href);
    window.onpopstate = function () {
        window.history.pushState(null, "", window.location.href);
    };
    // 🔒 Token තියෙනවා -> secure data load කරන්න
    loadDashboardData(token);
});

// Example secure fetch
async function loadDashboardData(token) {
    try {
        const response = await fetch("http://localhost:8080/auth/dashboard", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (response.ok) {
            const data = await response.json();
            console.log("Dashboard Data:", data);
            // TODO: UI update with dashboard data
        } else if (response.status === 401) {
            // Unauthorized -> Token invalid/expired
            alert("Session expired. Please log in again.");
            localStorage.removeItem("token");
            localStorage.removeItem("role");
            window.location.href = "Login.html";
        } else {
            console.error("Failed to load dashboard");
        }
    } catch (error) {
        console.error("Error fetching dashboard data:", error);
    }
}

