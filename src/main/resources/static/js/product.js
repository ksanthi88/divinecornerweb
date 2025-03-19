// Sample Product Data
// const products = [
//     { id: 1, name: "Traditional Closed Door Mandir", price: 4199, image: "../images/product1.jpg", description: "Beautiful handcrafted mandir with closed doors.", material: "Solid Wood", size: "24x18x12 inches" },
//     { id: 2, name: "Classic Open Door Mandir", price: 2500, image: "../images/product2.jpg", description: "Elegant mandir with open doors for easy access.", material: "Teak Wood", size: "20x15x10 inches" },
//     { id: 3, name: "Contemporary Open Door Mandir", price: 3000, image: "../images/product3.jpg", description: "Modern-style mandir with intricate carvings.", material: "Pine Wood", size: "22x16x11 inches" },
//     { id: 4, name: "Classic Open Door Mandir", price: 1500, image: "../images/product4.jpg", description: "Traditional open-door pooja mandir.", material: "MDF", size: "18x14x9 inches"  },
//     { id: 5, name: "Classic Open Door Mandir", price: 1500, image: "../images/product5.jpg", description: "Traditional open-door pooja mandir.", material: "MDF", size: "18x14x9 inches"  },
//     { id: 6, name: "Classic Open Door Mandir", price: 1500, image: "../images/product6.jpg", description: "Traditional open-door pooja mandir.", material: "MDF", size: "18x14x9 inches"  },
//     { id: 7, name: "Classic Open Door Mandir", price: 1500, image: "../images/product7.jpg", description: "Traditional open-door pooja mandir.", material: "MDF", size: "18x14x9 inches"  },
//     { id: 8, name: "Classic Open Door Mandir", price: 1500, image: "../images/product8.jpg", description: "Traditional open-door pooja mandir.", material: "MDF", size: "18x14x9 inches"  }
// ];

const productBody = document.getElementById("product-body");

// ✅ Load Products from Backend
async function loadProducts() {
    productBody.innerHTML = "";

    try {
        const response = await fetch("/api/products"); // ✅ Fetch products from backend
        const products = await response.json();

        products.forEach(product => {
            productBody.innerHTML += `
                <tr>
                    <td><img src="/images/${product.imageName || 'default.jpg'}" 
                             alt="${product.name}" width="100" onerror="this.onerror=null;this.src='/images/default.jpg';"></td>
                    <td>
                        <em>${product.description}</em><br>
                        <strong>Category:</strong> ${product.category} <br>
                    </td>
                    <td><strong>${product.name}</strong></td> 
                    <td>₹${product.price}</td>
                    <td><button class="btn" onclick="addToCart(${product.id})">Add to Cart</button></td>
                </tr>
            `;
        });

    } catch (error) {
        console.error("Error fetching products:", error);
        productBody.innerHTML = "<tr><td colspan='5'>⚠️ Failed to load products.</td></tr>";
    }
}

// Search & Filter Function
function filterProducts() {
    const query = document.getElementById("search-box").value.toLowerCase();
    productBody.innerHTML = "";

    fetch("/api/products")
        .then(response => response.json())
        .then(products => {
            products.filter(product => product.name.toLowerCase().includes(query))
                .forEach(product => {
                    productBody.innerHTML += `
                        <tr>
                            <td><img src="/images/${product.imageName || 'default.jpg'}" 
                                     alt="${product.name}" width="100" 
                                     onerror="this.onerror=null;this.src='/images/default.jpg';"></td>
                            <td>
                                <strong>${product.name}</strong><br>
                                <em>${product.description}</em><br>
                                <strong>Category:</strong> ${product.category} <br>
                            </td>
                            <td>₹${product.price}</td>
                            <td><button class="btn" onclick="addToCart(${product.id})">Add to Cart</button></td>
                        </tr>
                    `;
                });
        })
        .catch(error => console.error("Error filtering products:", error));
}

// ✅ Cart Functionality
function addToCart(id) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];

    fetch(`/api/products/${id}`)
        .then(response => response.json())
        .then(product => {
            let existingItem = cart.find(item => item.id === id);
            if (existingItem) {
                existingItem.quantity += 1;
            } else {
                cart.push({ ...product, quantity: 1 });
            }
            localStorage.setItem("cart", JSON.stringify(cart));
            updateCartCount();
            alert(`${product.name} has been added to your cart!`);
        })
        .catch(error => console.error("Error adding to cart:", error));
}

// Update Cart Count
function updateCartCount() {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    let cartCount = cart.reduce((total, item) => total + item.quantity, 0);
    let cartIcon = document.getElementById("cart-count");
    if (cartIcon) {
        cartIcon.textContent = cartCount;
    }
}

//Load products on page load
document.addEventListener("DOMContentLoaded", () => {
    loadProducts();
    updateCartCount();
});