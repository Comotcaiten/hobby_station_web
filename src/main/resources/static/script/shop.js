
$(document).ready(function () {
  loadProductsByCategory();
});

// Function load các products theo category
// Function to load products by category
function loadProductsByCategory(id) {
  console.log("Loading products for category ID:", id);

  let url =
    id == null ? `/api/products` : `/api/products/categories-false/${id}`;

  $.ajax({
    url: url,
    type: "GET",
    success: function (products) {
      console.log("Products loaded:", products);

      let productList = "";
      $.each(products, function (index, product) {
        productList += `
            <div class="product-item col-6 col-sm-4" style="width: 18rem;">
              <a href="/products-detail/${product.id}" class="product-link">
                <img src="${product.img}" class="card-img-top" alt="${product.name}" title="${product.name}">
              </a>
              <div class="card-body">
                <h5 class="card-title" title="${product.name}">${product.name}</h5>
                <p class="card-text">${product.price.toLocaleString("vi-VN", {
                    style: "currency",
                    currency: "VND",
                  })}
                </p>  
                <form action="/cart/add" method="post" class="btn-action">
                  <input type="hidden" name="quantity" value="1">
                  <input type="hidden" value="${product.id}" name="productId"/>
                  <button type="submit" class="btn btn-warning btn-sm">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-basket-fill" viewBox="0 0 16 16">
                      <path d="M5.071 1.243a.5.5 0 0 1 .858.514L3.383 6h9.234L10.07 1.757a.5.5 0 1 1 .858-.514L13.783 6H15.5a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.5.5H15v5a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V9H.5a.5.5 0 0 1-.5-.5v-2A.5.5 0 0 1 .5 6h1.717zM3.5 10.5a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0z"/>
                    </svg>
                    <span>Thêm Vào Giỏ</span>
                  </button>
                </form>
              </div>
            </div>`;
      });
      $(".sanpham").html(productList);
    },
    error: function (xhr, status, error) {
      console.error("Failed to load products:", status, error);
    },
  });
}
