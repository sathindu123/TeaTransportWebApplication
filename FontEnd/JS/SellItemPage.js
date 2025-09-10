let cart = [];
let total = 0;

function addToCart(item, price,productId) {
    console.log(productId);
    let existing = cart.find(c => c.productId === productId);
    if (existing) {
        existing.count += 1;
        existing.totalPrice += price;
    } else {
        cart.push({
            productId: productId,
            item: item,
            price: price,
            count: 1,
            totalPrice: price
        });

    }
    total += price;
    renderCart();
    document.getElementById("cartSidebar").classList.add("active");
}

function renderCart() {
    let cartHTML = "";
    cart.forEach((c, index) => {
        cartHTML += `<div class="cart-item">
                        ${c.item} - Rs.${c.price} x ${c.count} = Rs.${c.totalPrice}
                        <button onclick="removeItem(${index})">❌</button>
                     </div>`;
    });
    document.getElementById("cartItems").innerHTML = cartHTML;

    // total calculate fresh
    total = cart.reduce((sum, c) => sum + c.totalPrice, 0);
    document.getElementById("total").innerText = total;
}


function removeItem(index) {
    total -= cart[index].price * cart[index].count;
    cart.splice(index, 1);
    renderCart();
}

function toggleCart() {
    document.getElementById("cartSidebar").classList.toggle("active");
}


document.getElementById("checkoutBtn").addEventListener("click", () => {
    document.getElementById("checkoutForm").style.display = "block";

    if(cart.length === 0){
        alert("Cart is empty!");
        return;
    }

    document.getElementById("totalCheckout").innerText = total+350;

});



document.addEventListener("DOMContentLoaded", function () {
    fetch("http://localhost:8080/auth/setDetailsProduct")
        .then(res => res.json())
        .then(data => {

            const products = data.data; // sellRepository.findAll() result

            products.forEach(product => {

                const card = document.querySelector(`.product-card[data-id="${product.id}"]`);
                if (card) {

                    const priceElement = card.querySelector("p");
                    if (priceElement) {
                        priceElement.textContent = "Rs. " + product.price;
                    }


                    const button = card.querySelector("button");
                    if (button) {
                        button.setAttribute("onclick", `addToCart('${product.type}', ${product.price}, '${product.id}')`);
                    }
                }
            });
        })
        .catch(err => console.error("Error loading products:", err));
});




// Show checkout sidebar
function openCheckout() {
    document.getElementById("checkoutSidebar").classList.add("active");
}

// Hide checkout sidebar
function closeCheckout() {
    document.getElementById("checkoutSidebar").classList.remove("active");
}



document.getElementById("checkoutBtnSidebar").addEventListener("click", closeCheckout);


document.getElementById("checkoutBtn").addEventListener("click", () => {
    if(cart.length === 0){
        showNotification("Cart is empty!", "error");
        return;
    }
    openCheckout();

    // Render items inside checkout
    renderCheckoutCart();
});


document.getElementById("conformbtn").addEventListener("click", () => {
    const token = localStorage.getItem("token");


    const custName = document.getElementById("names").value.trim();
    const custAddress = document.getElementById("address").value.trim();
    const custTel = document.getElementById("tel").value.trim();

    if (!custName || !custAddress || !custTel) {
        showNotification("Please fill in all required fields!", "error");
        return; // stop execution
    }


    const orderData = {
        custName: document.getElementById("names").value,
        custAddress: document.getElementById("address").value,
        custTel: document.getElementById("tel").value,
        totalPrice: total,
        cartItem: cart,
    }

    fetch("http://localhost:8080/auth/addtocart", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(orderData)
    })
        .then(async res => {
            const data = await res.json();


            if(!res.ok || data.status === "error"){

                showNotification(data.message, "error");
                throw new Error(data.message);
            }


            showNotification(data.message, "success");
            cart = [];
            total = 0;
            renderCart();
            toggleCart();

            clearDetais();


        })
        .catch(err => console.error("Error placing order:", err));



});


function clearDetais() {
    names.value="";
    address.value ="";
    tel.value="";
    total.value ="";
    totalCheckout.value = "";
    cardNumber.value = "";
    cardExpiry.value = "";
    cardCVV.value = "";
    bankAccount.value = "";
    bankName.value = "";


}


const paymentSelect = document.getElementById("paymentMethod");
const cardDiv = document.getElementById("cardDetails");
const bankDiv = document.getElementById("bankDetails");

paymentSelect.addEventListener("change", () => {
    if(paymentSelect.value === "card") {
        cardDiv.style.display = "block";
        bankDiv.style.display = "none";
    } else if(paymentSelect.value === "bank") {
        bankDiv.style.display = "block";
        cardDiv.style.display = "none";
    } else {
        cardDiv.style.display = "none";
        bankDiv.style.display = "none";
    }
});
