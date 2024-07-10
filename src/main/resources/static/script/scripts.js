document.addEventListener('DOMContentLoaded', function() {
    // Xử lý khi click vào icon Giỏ hàng
    document.getElementById('cart-link').addEventListener('click', function(event) {
        event.preventDefault();
        // Thay đổi đường dẫn tại đây để điều hướng đến trang kiểm tra giỏ hàng
        window.location.href = 'https://www.your-checkout-page.com';
    });
});
