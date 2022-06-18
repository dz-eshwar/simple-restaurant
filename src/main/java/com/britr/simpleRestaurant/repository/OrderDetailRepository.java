package com.britr.simpleRestaurant.repository;

import com.britr.simpleRestaurant.dto.FetchOrderResponse;
import com.britr.simpleRestaurant.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findAllByOrderId(String orderId);
    @Query("select o from OrderDetail o where o.order.id = :orderId and o.menu.id = :menuId and o.table.id = :tableId")
    OrderDetail findByOrderIdAndMenuIdAndTableId(@Param("orderId") String orderId, @Param("menuId") Integer menuId, @Param("tableId") Integer tableId);

    @Query("select o from OrderDetail o where o.order.id = :orderId and o.table.id = :tableId")
    List<OrderDetail> findByOrderIdAndTableId(@Param("orderId") String orderId, @Param("tableId") Integer tableId);

    @Modifying
    @Query("delete from OrderDetail o where o.order.id = :orderId and o.menu.id = :menuId and o.table.id = :tableId")
    Integer deleteByOrderIdAndMenuIdAndTableId(@Param("orderId") String orderId, @Param("menuId") Integer menuId, @Param("tableId") Integer tableId);

    List<OrderDetail> findAllByTableId(Integer tableId);
}