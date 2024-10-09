package co.allconnected.fussiontech.productsservice.repository;
import co.allconnected.fussiontech.productsservice.model.ReportedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsRepository extends JpaRepository<ReportedProduct, String> {
}