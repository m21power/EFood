package EFood.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import EFood.models.OrderItemModel;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemModel, Long> {
    @Query("SELECT oi FROM OrderItemModel oi WHERE oi.order.id = :orderId")
    List<OrderItemModel> findByOrderId(Long orderId);
}