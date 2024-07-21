

// ------------ CART ---------------------------------------
$(document).ready(function () {

    // -> Khi người dùng thay đổi số lượng sản phẩm
    $(document).on('change', '.quantity-input', function() {
        let productId = $(this).closest('.form-outline').data('id');
        let quantity = $(this).val();

        updateCartItem(productId, quantity);
    });

    // -> Khi người dùng ấn nút xóa một sản phẩm
    $(document).on('click', '.remove-item', function() {
        let productId = $(this).data('id');
        removeFromCart(productId);
    });

    // -> Khi người dùng muốn xóa hết các sản phẩm trong giỏ hàng
    $('#clearCart').on('click', function() {
        clearCart();
    });

    $(document).on('click', '.plus', function() {
        // Tìm input[type=number] trong phần tử cha của button
        let $inputNumber = $(this).parent().find('input[type=number]');
        
        // Gọi phương thức stepUp() để tăng giá trị của input lên
        $inputNumber[0].stepUp(); // Sử dụng [0] để truy cập DOM element trong jQuery object

        updateCartItem($inputNumber.data("id"), $inputNumber.val());
    });

    $(document).on('click', '.minus', function() {
        // Tìm input[type=number] trong phần tử cha của button
        let $inputNumber = $(this).parent().find('input[type=number]');
        
        // Gọi phương thức stepUp() để tăng giá trị của input lên
        $inputNumber[0].stepDown(); // Sử dụng [0] để truy cập DOM element trong jQuery object

        updateCartItem($inputNumber.data("id"), $inputNumber.val());
    });

    // Load các sản phẩm trong giỏ hàng
    loadCart();
});

function displayCart(cartItems) {
    let cartContainer = $("#cartContainer");
    let alertContainer = $(".alert-info");
    let soluong = $("#soluong-items");

    // Xóa nội dung cũ 
    cartContainer.empty(); 
    alertContainer.empty();
    soluong.empty();

    let items = '';
    if (cartItems.length <= 0) {
        alertContainer.append('Your cart is empty.');
    } else {

        alertContainer.empty();
        alertContainer.hide();
        
        var iCount = 0;
        // Thêm từng sản phẩm vào cartContainer
        cartItems.forEach((item) => {
            var price = formatPrice(item.quantity*item.product.price) + " đ";
            cartContainer.append(`
                <div class="row">
                    <div class="col-lg-3 col-md-12 mb-4 mb-lg-0">
                        <div class="bg-image hover-overlay hover-zoom ripple rounded">
                            <img src="${item.product.img}" class="w-100" alt="${item.product.name}" />
                            <a href="#!">
                                <div class="mask" style="background-color: rgba(251, 251, 251, 0.2)"></div>
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-5 col-md-6 mb-4 mb-lg-0">
                        <p><strong>${item.product.name}</strong></p>
                        <a href="#" class="btn btn-danger btn-sm me-1 mb-2 remove-item" data-id="${item.product.id}">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3-fill" viewBox="0 0 16 16">
                                <path d="M11 1.5v1h3.5a.5.5 0 0 1 0 1h-.538l-.853 10.66A2 2 0 0 1 11.115 16h-6.23a2 2 0 0 1-1.994-1.84L2.038 3.5H1.5a.5.5 0 0 1 0-1H5v-1A1.5 1.5 0 0 1 6.5 0h3A1.5 1.5 0 0 1 11 1.5m-5 0v1h4v-1a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5M4.5 5.029l.5 8.5a.5.5 0 1 0 .998-.06l-.5-8.5a.5.5 0 1 0-.998.06m6.53-.528a.5.5 0 0 0-.528.47l-.5 8.5a.5.5 0 0 0 .998.058l.5-8.5a.5.5 0 0 0-.47-.528M8 4.5a.5.5 0 0 0-.5.5v8.5a.5.5 0 0 0 1 0V5a.5.5 0 0 0-.5-.5"/>
                            </svg>
                            Remove Item
                        </a>
                    </div>
                    <div class="col-lg-4 col-md-6 mb-4 mb-lg-0">
                        <label class="form-label" for="form1">Quantity</label>
                        <div class="d-flex mb-4 quanity-container" style="max-width: 300px">
                            <button class="btn btn-primary px-3 me-2 minus">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-dash-lg" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M2 8a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11A.5.5 0 0 1 2 8"/>
                                </svg>
                            </button>
                            <div class="form-outline" data-id=${item.product.id}>
                                <input id="form1-quantity" data-id=${item.product.id} min="0" name="quantity" value="${item.quantity}" type="number" class="form-control quantity-input" />
                                
                            </div>
                            <button class="btn btn-primary px-3 ms-2 plus">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2"/>
                                </svg>
                            </button>
                        </div>

                        <p class="text-start text-md-center" id="price" price=${item.product.price}>
                            <strong>${price}</strong>
                        </p>
                    </div>
                </div>
                <hr class="my-4"/>`
            );
            iCount++;
        });

        // Thiết lập ở Summary
        soluong.html(iCount);
        loadTotalPrice();
    }
}


// ------------------------------------------------------------------------------------
// <Hàm>
// GET - All
// Hàm này dùng để lấy tất cả các sản phẩm và số lượng của chúng trong giỏ hàng
function loadCart() {
  $.ajax({
    url: "/api/cart",
    method: "GET",
    success: function (cartItems) {
      displayCart(cartItems);
      totalQuanityCartItem();
      let cart_span = $(".cart-quanity-span");
      cart_span.hide();
    },
  });
}

// Total
// Hàm dùng để lấy tổng giá trị của giỏ hàng
function loadTotalPrice() {
    let total_amouts = $("#total-amount");
    total_amouts.empty();
    let content = "";
    $.ajax({
        url: "/api/cart/total",
        method: "GET",
        success: function (totalPrice) {
            total_amouts.html("<strong>" + formatPrice(totalPrice) + " đ" + "</strong>" );
        },
    });
}

// Update
// Hàm dùng để cập nhập lại số lượng sản phẩm của các sản phẩm trong giỏ hàng
function updateCartItem(productId, quantity) {
    if (quantity <= 0 || quantity == null) {
        removeFromCart(productId);
    }
    else {
        $.ajax({
            url: "/api/cart/update",
            method: "POST",
            data: {
            productId: productId,
            quantity: quantity,
            },
            success: function () {
            loadCart();
            },
        });
    }

}

// Remove
// Hàm dùng để xóa một sản phẩm khỏi giỏ hàng
function removeFromCart(productId) {
  $.ajax({
    url: "/api/cart/remove",
    method: "POST",
    data: {
      productId: productId,
    },
    success: function () {
      loadCart();
    },
  });
}

// Clear
// Hàm dùng để xóa hết sản phẩm khỏi giỏ hàng
function clearCart() {
    $.ajax({
        url: '/api/cart/clear',
        method: 'POST',
        success: function() {
            loadCart();
        }
    });
}


function totalQuanityCartItem() {

    $.ajax({
        url: "/api/cart/quanity",
        method: "GET",
        success: function (total) {
            updateCartTB(total);
        },
    });
}

function updateCartTB(total) {
    let update_cart = $("#so-quanitys");
    update_cart.empty();
    update_cart.html(total);
    update_cart.attr("title", total);
}

// </Hàm>
// ------------------------------------------------------------------------------------

function formatPrice(number) {
    return number.toLocaleString(
        undefined, // leave undefined to use the visitor's browser 
                   // locale or a string like 'en-US' to override it.
        { minimumFractionDigits: 0}
      ).toString(); // Định dạng số với dấu chấm
}