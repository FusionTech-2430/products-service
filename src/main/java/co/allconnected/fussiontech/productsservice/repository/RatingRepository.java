package co.allconnected.fussiontech.productsservice.repository;

import co.allconnected.fussiontech.productsservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
}