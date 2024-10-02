package co.allconnected.fussiontech.productsservice.repository;
import co.allconnected.fussiontech.productsservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}