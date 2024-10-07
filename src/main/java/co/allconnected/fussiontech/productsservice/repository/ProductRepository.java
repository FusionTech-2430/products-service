package co.allconnected.fussiontech.productsservice.repository;
import co.allconnected.fussiontech.productsservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, String> {
    public abstract Collection<Product> findByIdBusiness(UUID idBusiness);
}