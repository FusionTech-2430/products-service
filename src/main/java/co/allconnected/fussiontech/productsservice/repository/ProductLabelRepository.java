package co.allconnected.fussiontech.productsservice.repository;

import co.allconnected.fussiontech.productsservice.model.Product;
import co.allconnected.fussiontech.productsservice.model.ProductLabel;
import co.allconnected.fussiontech.productsservice.model.ProductLabelId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLabelRepository extends JpaRepository<ProductLabel, ProductLabelId> {
    void deleteByIdAnnouncement(Product idAnnouncement);
}