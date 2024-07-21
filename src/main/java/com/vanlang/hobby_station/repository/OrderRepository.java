package com.vanlang.hobby_station.repository;

import com.vanlang.hobby_station.model.Order;
import com.vanlang.hobby_station.model.Order.OrderStatus;

// import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    
    // Truy vấn lấy các đơn hàng theo trạng thái
    @Query("SELECT o FROM Order o WHERE o.status = :status")
    List<Order> findOrdersByStatus(@Param("status") OrderStatus status);


    @Query("DELETE FROM Order o WHERE o.status = :status")
    void deleteOrdersByStatus(@Param("status") OrderStatus status);
}
