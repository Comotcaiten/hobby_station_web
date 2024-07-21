$(document).ready(function () {
    loadDashboard();
    initializeDashboardCharts();
})

// --- HOME ---
function loadDashboard() {
    getDoanhThu();
    getSanLuong();
    displaySanLuongProducts();
}

function getDoanhThu() {
    let tongDoanhThu = $("#tongDoanhThu");
    tongDoanhThu.empty();
    $.ajax({
        url: "/api/orders/revenue/total",
        method: "Get",
        success: function (data) {
            tongDoanhThu.html("Tổng doanh thu: " + formatPrice(data) + " đ");
        },
    });
}

function getSanLuong() {
    let tongSoLuong = $("#tongSoLuongDonHang");
    tongSoLuong.empty();
    $.ajax({
        url: "/api/orders/quantity/total",
        method: "Get",
        success: function (data) {
            // console.log(data);
            tongSoLuong.html("Tổng số sản phẩm đã bán: " + data);
        },
    });
}

function displaySanLuongProducts() {
    let sp_banchay = $(".danh-sach-sp-ban-chay");
    sp_banchay.empty();
    $.ajax({
        url: "/api/admin/products/top-selling/delivered",
        method: "Get",
        success: function (data) {
            $.each(data, function (index, item) {
                displayProdcutsAndQuantity(item[0], item[1]);
            })
        },
    });
}

function displayProdcutsAndQuantity(id, quantity) {
    let sp_banchay = $(".danh-sach-sp-ban-chay");
    let content = '';
    $.ajax({
        url: "/api/admin/products/" + id,
        method : "Get",
        success : function (data) {
            content = `
                <tr>
                    <th>${data.id}</th>
                    <th>${data.name}</th>
                    <th>${quantity}</th>
                </tr>`;
            sp_banchay.append(content);
        }
    })
}

function formatPrice(number) {
    return number.toLocaleString(
        undefined, // leave undefined to use the visitor's browser 
                   // locale or a string like 'en-US' to override it.
        { minimumFractionDigits: 0}
      ).toString(); // Định dạng số với dấu chấm
}

