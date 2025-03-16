document.getElementById("sign-up").addEventListener("submit", async function(event) {


    const fullName = document.getElementById("fullName").value.trim();
    const email = document.getElementById("email").value.trim();
    const phoneNumber = document.getElementById("phoneNumber").value.trim();
    const password = document.getElementById("password").value.trim();

    // ✅ Debugging: Log values before sending to API
    console.log("Captured Values:", { fullName, email, phoneNumber, password });

    // ✅ Check for missing inputs
    if (!fullName || !email || !phoneNumber || !password) {
        alert("⚠️ All fields are required!");
        return;
    }

    const response = await fetch("http://localhost:8080/users/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ fullName, email, phoneNumber, password }) // ✅ Matches Java Backend
    });

    if (response.ok) {
        alert("Registration successful! Please login.");
        window.location.href = "/users/login"; // ✅ Redirect to Thymeleaf Login
    } else {
        alert("Registration failed!");
    }
});