document.addEventListener("DOMContentLoaded", function() {
    const isLoggedIn = localStorage.getItem("user"); // Check if user is logged in

    let authLinks = `
        <li><a href="..//login.html" id="auth-link">SIGN IN</a></li>
    `;

    if (isLoggedIn) {
        authLinks = `
            <li><a href="#" id="logout-btn">LOGOUT</a></li>
        `;
    }

    document.getElementById("header-container").innerHTML = `
        <nav>
            <div class="logo">
                <img src="../images/image.jpg" alt="Logo" class="logo-icon"> Divine Corner
            </div>
            <ul class="nav-links">
                <li><a href="../pages/home.html">HOME</a></li>
                <li><a href="../pages/product.html">PRODUCTS</a></li>
                <li><a href="../pages/about.html">ABOUT</a></li>
                <li><a href="../pages/contact.html">CONTACT US</a></li>
                <li><a href="../pages/cart.html" id="cart-btn">CART 🛒 (<span id="cart-count">0</span>)</a></li>
                ${authLinks} <!-- Dynamically insert login/logout button -->
            </ul>
        </nav>
    `;
// Update cart count
    updateCartCount();
// Logout button functionality
    const logoutBtn = document.getElementById("logout-btn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function(event) {
            event.preventDefault();
            localStorage.removeItem("user"); // Clear user session
            alert("✅ Logged out successfully!");
            window.location.href = "../pages/login.html"; // Redirect to login page
        });
    }

});

// Function to update cart count
function updateCartCount() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    document.getElementById("cart-count").textContent = cart.reduce((total, item) => total + item.quantity, 0);
}



