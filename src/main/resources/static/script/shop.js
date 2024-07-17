function loadProductsByCategory(categoryId) {
  fetch("/api/products/categories/" + categoryId)
    .then((response) => response.json())
    .then((data) => {
      const productContainer = document.querySelector(
        ".product-featured .sanpham"
      );
      productContainer.innerHTML = ""; // Clear current products
      data.forEach((product) => {
        const productItem = document.createElement("div");
        productItem.className = "product-item";
        productItem.innerHTML = `
                        <img src="${product.img}">
                        <h6 class="card-name-product" title="${product.name}">${
          product.name
        }</h6>
                        <p class="price">${product.price.toLocaleString(
                          "vi-VN",
                          { style: "currency", currency: "VND" }
                        )}</p>
                        <form action="/cart/add" method="post">
                            <input type="hidden" name="quantity" value="1">
                            <input type="hidden" name="productId" value="${
                              product.id
                            }">
                            <button type="submit" class="btn btn-warning btn-sm">Thêm Vào Giỏ</button>
                        </form>
                    `;
        productContainer.appendChild(productItem);
      });
    });
}
