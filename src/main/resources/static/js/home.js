// Typewriter Effect for Hero Section
const text = "Created by you, Hand Made by us...";
let index = 0;
function typeWriter() {
    if (index < text.length) {
        document.getElementById("typewriter").textContent += text.charAt(index);
        index++;
        setTimeout(typeWriter, 100);
    }
}
document.addEventListener("DOMContentLoaded", () => {
    typeWriter();
});

// Lightbox Effect for Product Images
const productImages = document.querySelectorAll(".product img");
const lightbox = document.createElement("div");
lightbox.classList.add("lightbox");
document.body.appendChild(lightbox);

const lightboxImg = document.createElement("img");
lightbox.appendChild(lightboxImg);

const closeButton = document.createElement("span");
closeButton.innerHTML = "&times;";
closeButton.classList.add("close");
lightbox.appendChild(closeButton);

productImages.forEach((img) => {
    img.addEventListener("click", () => {
        lightbox.style.display = "flex";
        lightboxImg.src = img.src;
    });
});

closeButton.addEventListener("click", () => {
    lightbox.style.display = "none";
});

lightbox.addEventListener("click", (e) => {
    if (e.target !== lightboxImg) {
        lightbox.style.display = "none";
    }
});


// Smooth Scrolling for Navigation Links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener("click", function (e) {
        e.preventDefault();
        document.querySelector(this.getAttribute("href")).scrollIntoView({
            behavior: "smooth"
        });
    });
});
