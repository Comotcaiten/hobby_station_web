package com.vanlang.hobby_station.repository;

import com.vanlang.hobby_station.model.Order.OrderStatus;
import com.vanlang.hobby_station.model.OrderDetail;
import com.vanlang.hobby_station.model.Product;

// import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
       
       // Trả về các sản phẩm top selling dạng Object [id-product, số lượng bán ra]

       // lấy tất cả
    @Query("SELECT od.product.id, SUM(od.quantity) as totalQuantity " +
           "FROM OrderDetail od " +
           "GROUP BY od.product.id " +
           "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProducts();

       // Lấy tất cả với điều kiện phụ thuộc vào status của đơn hàng
    @Query("SELECT od.product.id, SUM(od.quantity) as totalQuantity " +
    "FROM OrderDetail od " +
    "JOIN od.order o " +
    "WHERE o.status = :status " +
    "GROUP BY od.product.id " +
    "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProductsByStatus(@Param("status") OrderStatus status);

   // Lấy tất cả với điều kiện phụ thuộc vào isDeleted của sản phẩm
    @Query("SELECT od.product.id, SUM(od.quantity) as totalQuantity " +
    "FROM OrderDetail od " +
    "WHERE od.product.isDeleted = :isDeleted " +
    "GROUP BY od.product.id " +
    "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProductsByIsDeleted(boolean isDeleted);


    // Lấy tất cả với điều kiện phụ thuộc vào isDeleted = false của sản phẩm và status của đơn đặt hàng
    @Query("SELECT od.product.id, SUM(od.quantity) as totalQuantity " +
       "FROM OrderDetail od " +
       "WHERE od.product.isDeleted = :isDeleted AND od.order.status = :status " +
       "GROUP BY od.product " +
       "ORDER BY totalQuantity DESC")
       List<Object[]> findTopSellingProductsByStatusAndIsDeleted(@Param("isDeleted") boolean isDeleted, @Param("status") OrderStatus status);


    // Trả về các sản phẩm top selling dạng Products
    // trả về tất cả
//     @Query("SELECT od.product " +
//            "FROM OrderDetail od " +
//            "WHERE od.product.isDeleted = :isDeleted " +
//            "GROUP BY od.product " +
//            "ORDER BY SUM(od.quantity) DESC")
//     List<Product> findTopSellingProductsObj(boolean isDeleted);

    // trả về tất cả sản phẩm theo isDeleted và Status
    @Query("SELECT od.product " +
    "FROM OrderDetail od " +
    "WHERE od.product.isDeleted = :isDeleted AND od.order.status = :status " +
    "GROUP BY od.product " +
    "ORDER BY SUM(od.quantity) DESC")
       List<Product> findTopSellingProductsObj(@Param("isDeleted") boolean isDeleted, @Param("status") OrderStatus status);



    // Trả về các oderdetail theo id của order
    @Query("SELECT od FROM OrderDetail od WHERE od.order.id = :orderId")
    List<OrderDetail> findByOrderId(Long orderId);


    // Cho chức năng thống kê
    // Lấy tổng doanh thu (bỏ qua các điều kiện khác)
    @Query("SELECT SUM(od.quantity * p.price) FROM OrderDetail od JOIN od.product p")
    double findTotalRevenue();

    // Lấy tổng doanh thu theo status
    @Query("SELECT SUM(od.quantity * p.price) FROM OrderDetail od JOIN od.product p JOIN od.order o WHERE o.status = :status")
    double findTotalRevenueByStatus(@Param("status") OrderStatus status);

    // Lấy tổng sản lượng theo status
    @Query("SELECT SUM(od.quantity) FROM OrderDetail od WHERE od.order.status = :status")
    double findTotalQuantityByStatus(@Param("status") OrderStatus status);
}
