$(document).ready(function () {
  loadProductsByCategory();
});

// Function load các products theo category
// Function to load products by category
function loadProductsByCategory(id) {
  let url =
    id == null ? `/api/products` : `/api/products/categories/${id}`;

  $.ajax({
    url: url,
    type: "GET",
    success: function (products) {
      console.log("Products loaded:", products);

      let productList = "";
      if (products.length > 0) {
        $.each(products, function (index, product) {
          let price =
            product.price.toLocaleString(undefined, {
              minimumFractionDigits: 0,
            }) + " đ";
          productList += `
              <div class="product-item col-6 col-sm-4" style="width: 18rem;">
                <a href="/product/${product.id}" class="product-link">
                  <img src="${product.img}" class="card-img-top" alt="${product.name}" title="${product.name}">
                </a>
                <div class="card-body">
                  <h5 class="card-title" title="${product.name}">${product.name}</h5>
                  <p class="card-text price">${price}
                  </p>  
                    <button type="submit" class="btn btn-warning btn-sm btn-add-cart" data-id="${product.id}">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-basket-fill" viewBox="0 0 16 16">
                          <path d="M5.071 1.243a.5.5 0 0 1 .858.514L3.383 6h9.234L10.07 1.757a.5.5 0 1 1 .858-.514L13.783 6H15.5a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.5.5H15v5a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V9H.5a.5.5 0 0 1-.5-.5v-2A.5.5 0 0 1 .5 6h1.717zM3.5 10.5a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0zm2.5 0a.5.5 0 1 0-1 0v3a.5.5 0 0 0 1 0z"/>
                      </svg>
                      <span>Thêm Vào Giỏ</span>
                    </button>
                </div>
              </div>`;
        });
      } else {
        productList = `
          <div class="px-4 py-5 my-5 text-center">
              <h1 class="display-5 fw-bold text-body-emphasis">Trang chính</h1>
              <div class="col-lg-6 mx-auto">
                  <p class="lead mb-4">Cửa hàng hiện chưa có sản phẩm nào được thêm. Vui lòng quay lại sau để xem thêm.</p>
                  <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                      <a href="/" type="button" class="btn btn-primary btn-lg px-4 gap-3">Home</a>
                  </div>
              </div>
          </div>
        `;
      }
      $(".sanpham").html(productList);
      loadCategoriesForDB();
    },
    error: function (xhr, status, error) {},
  });
}

function loadCategoriesForDB() {
  let listCategoryShophtml = $(".list-categories-shop");
  $.ajax({
    url: "/api/categories",
    type: "GET",
    success(categories) {
      listCategoryShophtml.empty();

      let categoryList = "";
      categoryList += `
      <li>
        <a href="#All"
          onclick="loadProductsByCategory(); return false;">
          All
        </a>
      </li>`;
      $.each(categories, function (index, category) {
        categoryList += `
            <li>
              <a href="#${category.name}"
                data-category-id=${category.id}
                data-id=${category.id}
                onclick="loadProductsByCategory(${category.id}); return false;">
                ${category.name}
              </a>
            </li>
        `;
      });
      listCategoryShophtml.html(categoryList);
    },
  });
}
