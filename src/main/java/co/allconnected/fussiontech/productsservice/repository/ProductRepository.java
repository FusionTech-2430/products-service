package co.allconnected.fussiontech.productsservice.repository;
import co.allconnected.fussiontech.productsservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductRepository extends JpaRepository<Product, String> {
Collection<Product> findByIdBusiness (String idBusiness);
}