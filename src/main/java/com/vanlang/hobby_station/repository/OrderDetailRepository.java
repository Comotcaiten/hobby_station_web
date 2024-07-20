package com.vanlang.hobby_station.repository;

import com.vanlang.hobby_station.model.OrderDetail;
import com.vanlang.hobby_station.model.Product;

// import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("SELECT od.product.id, SUM(od.quantity) as totalQuantity " +
           "FROM OrderDetail od " +
           "GROUP BY od.product.id " +
           "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProducts();

    @Query("SELECT od.product.id, SUM(od.quantity) as totalQuantity " +
    "FROM OrderDetail od " +
    "WHERE od.product.isDeleted = :isDeleted " +
    "GROUP BY od.product.id " +
    "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProductsByIsDeleted(boolean isDeleted);


    @Query("SELECT od.product " +
           "FROM OrderDetail od " +
           "WHERE od.product.isDeleted = false " +
           "GROUP BY od.product " +
           "ORDER BY SUM(od.quantity) DESC")
    List<Product> findTopSellingProductsObj(boolean isDeleted);

    @Query("SELECT od FROM OrderDetail od WHERE od.order.id = :orderId")
    List<OrderDetail> findByOrderId(Long orderId);
}
