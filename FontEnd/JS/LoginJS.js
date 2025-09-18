// login.js



document.addEventListener("DOMContentLoaded", function () {

    const loginForm = document.querySelector("form");
    const usernameInput = document.getElementById("username");
    const passwordInput = document.getElementById("password");
    const signupBtn = document.querySelector(".btn-signup");



    // Login Form Submit
    loginForm.addEventListener("submit", async function (e) {
        e.preventDefault(); // normal form submit block karanawa

        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();

        if (!username || !password) {
            showNotification("Please enter both username and password.", "error");
            passwordInput.value = "";
            usernameInput.value = "";
            return;
        }

        // Password regex validation
        if (!validatePassword(password)) {
            showNotification("Password must be at least 8 characters and include 1 uppercase letter.", "warning");
            passwordInput.style.border = "2px solid red"; // red border
            passwordInput.focus();
            return;
        } else {
            // Reset border if valid
            passwordInput.style.border = "";
        }

        try {
            const response = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const data = await response.json();


                // Example: save JWT token / session
                localStorage.setItem("token", data.data.accessToken);
                localStorage.setItem("role", data.data.role);

                showNotification("Login successful!", "success");
                passwordInput.value = "";
                usernameInput.value = "";
                window.location.href = "Dashbord.html"; // Redirect
            } else {
                showNotification("Something went wrong.", "error");
                passwordInput.value = "";
                usernameInput.value = "";
            }

        } catch (error) {
            showNotification("Something went wrong. ad", "error");
            passwordInput.value = "";
            usernameInput.value = "";
        }
    });

    // Sign Up Button Click
    signupBtn.addEventListener("click", function () {
        window.location.href = "SisnUpPage.html"; // Redirect to signup page
    });

});


// Password validation function
function validatePassword(password) {
    // Minimum 8 chars, at least 1 uppercase letter
    const regex = /^(?=.*[A-Z]).{8,}$/;
    return regex.test(password);
}





