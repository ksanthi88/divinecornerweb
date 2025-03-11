document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("sign-up");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent form submission until validation

        const name = document.getElementById("name").value.trim();
        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("create-password").value;
        const confirmPassword = document.getElementById("confirm-password").value;

        // Name validation
        if (name === "") {
            alert("Please enter your full name.");
            return;
        }

        // Email validation using regex
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(email)) {
            alert("Please enter a valid email address.");
            return;
        }

        // Password length check
        if (password.length < 6) {
            alert("Password must be at least 6 characters long.");
            return;
        }

        // Confirm password match
        if (password !== confirmPassword) {
            alert("Passwords do not match.");
            return;
        }

        // If all validations pass, submit the form
        alert("Registration successful!");
        form.submit();
    });
});