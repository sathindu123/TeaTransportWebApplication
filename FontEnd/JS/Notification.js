function showNotification(message, type = "info") {
    let notification = document.getElementById("notification");

    // create once if not exist
    if (!notification) {
        notification = document.createElement("div");
        notification.id = "notification";
        notification.className = "notification";
        document.body.appendChild(notification);
    }

    // Icons per type
    const icons = {
        success: "✅",
        error: "❌",
        warning: "⚠️",
        info: "ℹ️"
    };

    // reset + set
    notification.className = "notification " + type;
    notification.textContent = `${icons[type] || "ℹ️"} ${message}`;

    // show
    notification.classList.add("show");

    // auto hide
    setTimeout(() => {
        notification.classList.remove("show");
    }, 3500);
}


// showNotification("Login successful!", "success");
// showNotification("Something went wrong.", "error");
// showNotification("Please check your input.", "warning");
// showNotification("This is some info.", "info");