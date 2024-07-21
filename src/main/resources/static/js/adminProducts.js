$(document).ready(function () {
    loadProducts();

    $(document).on('click', '#trash-can', function() {
        loadProductsTrashCan();
    });

    $(document).on("click", ".restore-btn", function () {
        var productId = $(this).data('id');
        restoreProduct(productId);
    })

    $(document).on("click", "#data-load", function() {
        loadProducts();
    })
});

// Load products function
function loadProducts() {
    $.ajax({
        url: '/api/admin/products',
        method: 'GET',
        success: function (data) {
            var tableBody = $('#table-row-products');
            tableBody.empty();
            data.forEach(function (product) {
                changeTitle("Danh sách sản phẩm");
                let price =
                    product.price.toLocaleString(undefined, {
                        minimumFractionDigits: 0,
                    }) + "đ"
                var row = '<tr>' +
                    '<td>' + product.id + '</td>' +
                    '<td>' + product.name + '</td>' +
                    '<td>' + price + '</td>' +
                    '<td>' + product.description + '</td>' +
                    '<td>' + product.category.name + '</td>' +
                    '<td>' + product.brand.name + '</td>' +
                    '<td>' +
                    '<div>' +
                    '<a href="/admin/products/edit/' + product.id + '" class="btn btn-success btn-lg mb-3">Sửa</a>' +
                    '<button class="btn btn-danger btn-lg mb-3 delete-product" data-id="' + product.id + '">Xóa</button>' +
                    '</div>' +
                    '</td>' +
                    '</tr>';
                tableBody.append(row);
            });

            // Attach delete event
            $('.delete-product').click(function () {
                var productId = $(this).data('id');
                if (confirm('Bạn có chắc không?')) {
                    deleteProduct(productId);
                }
            });
        },
        error: function (error) {
            console.error('Error fetching products:', error);
        }
    });
}

// Delete product function
function deleteProduct(id) {
    $.ajax({
        url: '/api/admin/products/' + id,
        method: 'DELETE',
        success: function () {
            loadProducts(); // Reload the products list after deletion
        },
        error: function (error) {
            console.error('Error deleting product:', error);
        }
    });
}

// Function to load products
function loadProductsTrashCan() {
    $.ajax({
        url: '/api/admin/products/trashed', // Endpoint to get trashed products
        method: 'GET',
        success: function(data) {
            // Populate the table with data
            var $tbody = $('#table-row-products');
            $tbody.empty(); // Clear existing content
            changeTitle("Danh sách sản phẩm > Thùng rác");
            $.each(data, function(index, product) {
                $tbody.append(
                    '<tr>' +
                        '<td>' + product.id + '</td>' +
                        '<td>' + product.name + '</td>' +
                        '<td>' + product.price.toFixed(2) + '</td>' +
                        '<td>' + product.description + '</td>' +
                        '<td>' + product.category.name + '</td>' +
                        '<td>' + product.brand.name + '</td>' +
                        '<td>' +
                            '<button class="btn btn-success btn-sm restore-btn" data-id="' + product.id + '">Khôi phục</button>' +
                        '</td>' +
                    '</tr>'
                );
            });
        }
    });
}
function restoreProduct(productId) {
    $.ajax({
        url: '/api/admin/products/restore/' + productId, // Endpoint to restore product
        method: 'POST',
        success: function() {
            alert('Product restored successfully');
            loadProductsTrashCan(); // Reload products
        },
        error: function() {
            alert('Failed to restore product');
        }
    });
}

function changeTitle(content) {
    let title = $(".h1_title")
    title.empty();
    title.html(content);
}