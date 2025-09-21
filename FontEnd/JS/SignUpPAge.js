const form = document.querySelector("form");
const roleDropdown = document.getElementById("roleDropdown");
const otpModalEl = document.getElementById("otpModal");
const otpModal = new bootstrap.Modal(otpModalEl);
const verifyOtpBtn = document.getElementById("verifyOtpBtn");
const resendOtpBtn = document.getElementById("resendOtpBtn");
const otpTimerEl = document.getElementById("otpTimer");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");
const customerInput = document.getElementById("customer");
const otpInputs = document.querySelectorAll(".otp-input");

let countdownInterval;
let otpTime = 60; // seconds


function startOtpTimer() {
    otpTime = 60;
    clearInterval(countdownInterval);
    otpTimerEl.textContent = formatTime(otpTime);

    countdownInterval = setInterval(() => {
        otpTime--;
        otpTimerEl.textContent = formatTime(otpTime);
        if (otpTime <= 0) {
            clearInterval(countdownInterval);
            Swal.fire("Timeout", "OTP expired. Please resend OTP.", "warning");
        }
    }, 1000);
}

function formatTime(seconds) {
    const min = String(Math.floor(seconds / 60)).padStart(2, "0");
    const sec = String(seconds % 60).padStart(2, "0");
    return `${min}:${sec}`;
}

// ---------------- OTP Inputs Auto-Focus ----------------
otpInputs.forEach((input, idx) => {
    input.addEventListener("input", () => {
        if (input.value.length === 1 && idx < otpInputs.length - 1) otpInputs[idx + 1].focus();
    });
    input.addEventListener("keydown", e => {
        if (e.key === "Backspace" && input.value === "" && idx > 0) otpInputs[idx - 1].focus();
    });
});

function getOtpValue() {
    return Array.from(otpInputs).map(input => input.value).join("");
}

// ---------------- Resend OTP ----------------
resendOtpBtn.addEventListener("click", () => {
    fetch("http://localhost:8080/auth/send-admin-otp", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({})
    })
        .then(res => res.json())
        .then(data => {
            if(data.success){
                Swal.fire("Info", "OTP resent to admin email.", "info");
                otpInputs.forEach(i => i.value = "");
                otpInputs[0].focus();
                startOtpTimer();
            } else {
                Swal.fire("Error", "Failed to resend OTP.", "error");
            }
        });
});

// ---------------- When Modal Opens ----------------
otpModalEl.addEventListener("shown.bs.modal", () => {
    otpInputs[0].focus();
    startOtpTimer();
});

// ---------------- Password Validation ----------------
function validatePassword(password) {
    const regex = /^(?=.*[A-Z]).{8,}$/;
    return regex.test(password);
}

// ---------------- Notification ----------------
function showNotification(message, type) {
    alert(type.toUpperCase() + ": " + message);
}

// ---------------- Send Admin OTP ----------------
function sendAdminOTP() {
    fetch("http://localhost:8080/auth/send-admin-otp", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({})
    })
        .then(res => res.json())
        .then(data => {
            if(data.success){
                showNotification("OTP sent to admin email.", "info");
            } else {
                showNotification("Failed to send OTP. Try again.", "error");
            }
        });
}

// ---------------- Register User ----------------
async function registerUser() {
    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();
    const role = roleDropdown.value;
    const customerId = customerInput.value.trim();

    if (!validatePassword(password)) {
        showNotification("Password must be at least 8 characters and include 1 uppercase letter.", "warning");
        passwordInput.value = "";
        passwordInput.focus();
        return;
    }

    const registerDTO = {
        username: username,
        password: password,
        role: role,
        id: role === "CUSTOMER" ? customerId : null
    };

    try {
        const response = await fetch("http://localhost:8080/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(registerDTO)
        });

        if (response.ok) {
            showNotification("Registration successful!", "success");
            passwordInput.value = "";
            usernameInput.value = "";
            window.location.href = "login.html";
        } else {
            let errorMsg = "Something went wrong. Please try again later.";
            try {
                const errorData = await response.json();
                if (errorData && errorData.data) errorMsg = errorData.data;
            } catch (e) {}
            showNotification(errorMsg, "error");
            passwordInput.value = "";
            usernameInput.value = "";
        }
    } catch (error) {
        passwordInput.value = "";
        usernameInput.value = "";
        console.error("Network error:", error);
        showNotification("Something went wrong. Please try again later.", "error");
    }
}

// ---------------- Form Submit ----------------
form.addEventListener("submit", function(event){
    event.preventDefault();
    const selectedRole = roleDropdown.value;

    if(selectedRole === "ADMIN"){
        sendAdminOTP();
        otpModal.show();
    } else {
        registerUser();
    }
});

// ---------------- Verify OTP ----------------
verifyOtpBtn.addEventListener("click", function(){
    const otp = getOtpValue().trim();
    if(otp.length !== 6){
        Swal.fire("Warning", "Please enter 6-digit OTP.", "warning");
        return;
    }

    fetch("http://localhost:8080/auth/verify-admin-otp", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ otp: otp })
    })
        .then(res => res.json())
        .then(data => {
            if(data.success){
                Swal.fire({
                    icon: "success",
                    title: "Verified!",
                    text: "OTP verified, registration complete.",
                    timer: 2000,
                    showConfirmButton: false
                });
                otpModal.hide();
                registerUser();
            } else {
                Swal.fire({
                    icon: "error",
                    title: "Invalid OTP",
                    text: "Please enter the correct OTP."
                });
            }
        })
        .catch(err => {
            console.error(err);
            showNotification("Network error while verifying OTP.", "error");
        });
});

// ---------------- Back to Login ----------------
document.querySelector(".btn-login").addEventListener("click", function(){
    window.location.href = "login.html";
});

document.addEventListener("DOMContentLoaded", function () {
    const roleDropdown = document.getElementById("roleDropdown");
    const billBookSection = document.getElementById("billBookSection");
    const hiddenRole = document.getElementById("hiddenRole");
    const selectedRoleLabel = document.getElementById("selectedRole");

    roleDropdown.addEventListener("change", function () {
        const selectedValue = roleDropdown.value;

        // Hidden input & label update කරන්න
        hiddenRole.value = selectedValue;
        selectedRoleLabel.textContent = "Selected Role: " + selectedValue;

        // CUSTOMER role නම් section එක show කරන්න
        if (selectedValue === "CUSTOMER") {
            billBookSection.style.display = "block";
        } else {
            billBookSection.style.display = "none";
        }
    });
});
