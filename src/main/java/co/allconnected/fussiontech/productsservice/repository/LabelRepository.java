package co.allconnected.fussiontech.productsservice.repository;
import co.allconnected.fussiontech.productsservice.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, String> {
}
