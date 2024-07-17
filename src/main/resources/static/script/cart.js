

$(document).ready(function () {
    $("#check-out").click('submit', function (e) {
        e.preventDefault();
        console.log("Check Out");
    });

    // Lắng nghe sự kiện 'input' cho tất cả các input có class 'form-control'
    $('.form-outline .form-control').on('input', function() {
        var quantityValue = $(this).val(); // Lấy giá trị trong input
        var productId = $(this).closest('.form-outline').data('id'); // Lấy ID sản phẩm từ thuộc tính data-id
        var pricePerUnit = $(this).closest('.form-outline').find('input[name="quantity"]').data('price'); // Giả sử có giá mỗi đơn vị

        // Tính toán giá mới
        var newPrice = (quantityValue * pricePerUnit).toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ","); // Định dạng số với dấu phẩy
        // Cập nhật giá hiển thị
        getPriceProduct(productId).then(price => {
            newPrice = price * quantityValue;
            let formattedPrice = formatPrice(newPrice); // Định dạng giá
            $(this).closest('.col-lg-4').find('#price').text(newPrice + ' đ');
        }).catch(error => {
        });
    });
})


function getPriceProduct(id) {
    return new Promise((resolve, reject) => {
        if (id == null) {
            resolve(0); // Trả về 0 nếu id là null
            return;
        }
        
        let url = `/api/products/${id}`;
        
        $.ajax({
            url: url,
            type: "GET",
            success: function (product) {
                resolve(product.price); // Trả về giá trị price
            },
            error: function (xhr, status, error) {
                resolve(0);
            },
        });
    });
}

function formatPrice(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, "."); // Định dạng số với dấu chấm
}