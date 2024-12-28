package EFood.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import EFood.models.OrderModel;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
   @Query("SELECT o FROM OrderModel o WHERE o.userId = :userId")
   List<OrderModel> findAllByUserId(@Param("userId") Long userId);
}