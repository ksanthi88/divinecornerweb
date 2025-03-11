document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("login-form");

    if (loginForm) {
        loginForm.addEventListener("submit", async function (event) {
            event.preventDefault();

            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            try {
                const response = await fetch("http://localhost:8080/users/login", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: new URLSearchParams({
                        username: email, // Spring Security expects `username`
                        password: password
                    })
                });

                if (response.ok) {
                    const data = await response.json();
                    console.log("✅ Login successful:", data);

                    // Store user session in localStorage
                    localStorage.setItem("user", JSON.stringify(data));

                    // Redirect after login
                    window.location.href = data.redirect;
                } else {
                    console.error("❌ Login failed!", response.status);
                    alert("❌ Invalid email or password!");
                }
            } catch (error) {
                console.error("❌ Login error:", error);
                alert("⚠️ Unable to connect to the server.");
            }
        });
    }
});