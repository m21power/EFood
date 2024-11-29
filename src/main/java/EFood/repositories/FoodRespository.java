package EFood.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EFood.models.FoodModel;

@Repository
public interface FoodRespository extends JpaRepository<FoodModel, Long> {

}
