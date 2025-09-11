let currentPage = 0;   // use camelCase everywhere
let pageSize = 5;
const searchInput = document.getElementById("searchInput");

document.addEventListener("DOMContentLoaded", function () {
    loadOrders(currentPage);
    setupPagination();
});


document.getElementById("searchButton").addEventListener("click", async function () {
    const queary = searchInput.value.trim();
    console.log(queary);
    try {
        if (searchInput.value == "") {
            searchInput.style.borderColor = "red";
            showNotification("Please Input Name", "error");
        }

        const response = await fetch(`http://localhost:8080/auth/LoadTableSudjest?query=${encodeURIComponent(queary)}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            showNotification("request Faild", "Error");
            return;
        }

        const data = await response.json();
        console.log("Full Response:", data);

        const tableBody = document.getElementById("complaintsTableBody");
        tableBody.innerHTML = "";


        data.content.forEach(order => {
            let row = `
                <tr>
                    <td>${order.productId}</td>
                    <td>${order.count}</td>
                    <td>${order.custName}</td>
                    <td>${order.custAddress}</td>
                    <td>${order.custTel}</td>
                   <td>
                        <button class="btn-delete" onclick="deleteOrder('${order.oid}','${order.productId}')">Delete</button>

                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });


        document.getElementById("pageInfo").innerText =
            `Page ${data.number + 1} of ${data.totalPages}`;



    } catch (error) {
        Swal.fire("Error loading orders:", error);
    }
})

async function loadOrders(page) {
    try {
        const response = await fetch(`http://localhost:8080/auth/viewOrder?page=${page}&size=${pageSize}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            Swal.fire("Failed to fetch orders");
        }

        const data = await response.json();
        const tableBody = document.getElementById("complaintsTableBody");
        tableBody.innerHTML = "";


        data.content.forEach(order => {
            let row = `
                <tr>
                    <td>${order.productId}</td>
                    <td>${order.count}</td>
                    <td>${order.custName}</td>
                    <td>${order.custAddress}</td>
                    <td>${order.custTel}</td>
                   <td>
                        <button class="btn-delete" onclick="deleteOrder('${order.oid}','${order.productId}')">Delete</button>

                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });


        document.getElementById("pageInfo").innerText =
            `Page ${data.number + 1} of ${data.totalPages}`;

    } catch (error) {
        Swal.fire("Error loading orders:", error);
    }
}

function setupPagination() {
    document.getElementById("prevPage").addEventListener("click", function () {
        if (currentPage > 0) {
            currentPage--;
            loadOrders(currentPage);
        }
    });

    document.getElementById("nextPage").addEventListener("click", function () {
        currentPage++;
        loadOrders(currentPage);
    });
}

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

    // result.isConfirmed true නම් user confirm කළා කියලා
    return result.isConfirmed;
}

// Delete function
async function deleteOrder(orderId,productId) {
    const confirmed = await confirmDelete();
    if (!confirmed) return; // user cancelled

    try {
        const response = await fetch(`http://localhost:8080/auth/deleteOrder/${orderId}/${productId}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        });


        if (response.ok) {
            Swal.fire({
                icon: "success",
                title: "Verified!",
                text: "Order deleted successfully",
                timer: 2000,
                showConfirmButton: false
            });
            loadOrders(currentPage); // reload current page
        } else {
            Swal.fire({
                icon: "error",
                title: "Failed to delete order",
                text: "Try Again.",
            });
        }
    } catch (error) {
        console.error("Error deleting order:", error);
        alert("Error deleting order");
    }
}




searchInput.addEventListener("input", async function() {
    searchInput.style.borderColor= "#e0e0e0";
    const query = this.value.trim();
    if (query.length === 0) {
        closeSuggestionList();
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/auth/searchUser?query=${query}`);
        const usernames = await response.json();

        showSuggestions(usernames);

    } catch (error) {
        console.error("Error fetching suggestions:", error);
    }
});



function showSuggestions(list) {
    closeSuggestionList();

    const container = document.createElement("div");
    container.setAttribute("id", "autocomplete-list");
    container.setAttribute("class", "autocomplete-items");
    searchInput.parentNode.appendChild(container);

    list.forEach(item => {
        const div = document.createElement("div");
        div.innerHTML = item;
        div.addEventListener("click", function() {
            searchInput.value = this.innerText;
            closeSuggestionList();
        });
        container.appendChild(div);
    });
}

function closeSuggestionList() {
    const existing = document.getElementById("autocomplete-list");
    if (existing) existing.remove();
}

document.addEventListener("click", function(e) {
    if (e.target != searchInput) {
        closeSuggestionList();
    }
});