document.addEventListener("DOMContentLoaded", function () {
    updateCartCount();
});

// ✅ Function to update the cart count dynamically
function updateCartCount() {
    fetch("/api/cart/items")
        .then(response => response.json())
        .then(data => {
            document.querySelector("#cart-count").textContent = data.length;
        })
        .catch(error => console.error("Error fetching cart count:", error));
}

// ✅ Function to add a product to the cart
function addToCart(productId) {
    fetch(`/api/cart/add/${productId}`, {
        method: "POST"
    })
        .then(response => response.text())
        .then(message => {
            alert(message); // Show success message
            updateCartCount(); // Update cart count in the header
        })
        .catch(error => console.error("Error adding product to cart:", error));
}

// ✅ Function to remove a product from the cart
function removeFromCart(productId) {
    fetch(`/api/cart/remove/${productId}`, {
        method: "POST"
    })
        .then(response => response.text())
        .then(message => {
            alert(message);
            location.reload(); // Reload cart page to reflect changes
        })
        .catch(error => console.error("Error removing product from cart:", error));
}

// ✅ Function to clear the cart
function clearCart() {
    fetch("/api/cart/clear", {
        method: "POST"
    })
        .then(response => response.text())
        .then(message => {
            alert(message);
            location.reload(); // Reload cart page to reflect changes
        })
        .catch(error => console.error("Error clearing cart:", error));
}

// ✅ Attach event listeners dynamically (if cart buttons exist)
document.querySelectorAll(".add-to-cart-btn").forEach(button => {
    button.addEventListener("click", function () {
        const productId = this.getAttribute("data-product-id");
        addToCart(productId);
    });
});

document.querySelectorAll(".remove-cart-btn").forEach(button => {
    button.addEventListener("click", function () {
        const productId = this.getAttribute("data-product-id");
        removeFromCart(productId);
    });
});

document.querySelector("#clear-cart-btn")?.addEventListener("click", function () {
    clearCart();
});