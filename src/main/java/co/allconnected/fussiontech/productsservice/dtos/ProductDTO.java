package co.allconnected.fussiontech.productsservice.dtos;

import co.allconnected.fussiontech.productsservice.model.Label;
import co.allconnected.fussiontech.productsservice.model.Product;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class ProductDTO implements Serializable {
    private Integer id;
    private String idBusiness;
    private String name;
    private String description;
    private String photoUrl;
    private Integer stock;
    private Double price;
    private String status;
    private String [] labels;

    public ProductDTO(Product product){
        this.id = product.getId();
        this.idBusiness = product.getIdBusiness().toString();
        this.name = product.getName();
        this.description = product.getDescription();
        this.photoUrl = product.getPhotoUrl();
        this.stock = product.getStock();
        this.price = product.getPrice();
        this.status = product.getStatus();
        this.labels = product.getLabels().stream().map(Label::getLabel).toArray(String[]::new);
    }
}