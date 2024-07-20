
// ----------- HEAD ----------------
$(document).ready(function () {

    $(document).on("click", ".btn-add-cart" , function () {
        let productId = $(this).data("id");

        addCartItem(productId, 1);
    });

    loadCart();
})


//
function loadCart() {
    totalQuanityCartItem();
}

function print(cotent) {
    console.log(cotent);
}



// Hàm dùng để thêm sản phẩm vào giỏ hàng
function addCartItem(productId, quantity) {
    if (quantity <= 0 || quantity == null) {
        quantity = 1;
    }
    else {
        $.ajax({
            url: "/api/cart/add",
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
    let update_cart = $("#update-cart-amount");
    update_cart.html(total);
    update_cart.attr("title", total);
}
