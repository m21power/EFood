package EFood.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EFood.models.OrderModel;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    OrderModel findByUserId(Long userId);
}