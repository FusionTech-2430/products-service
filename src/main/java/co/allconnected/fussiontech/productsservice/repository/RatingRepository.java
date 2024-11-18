package co.allconnected.fussiontech.productsservice.repository;

import co.allconnected.fussiontech.productsservice.model.Product;
import co.allconnected.fussiontech.productsservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    public abstract Collection<Rating> findByIdProduct(Product idProduct);
}