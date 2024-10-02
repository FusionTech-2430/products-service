package co.allconnected.fussiontech.productsservice.dtos;

import co.allconnected.fussiontech.productsservice.model.Label;
import co.allconnected.fussiontech.productsservice.model.Product;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class LabelDTO implements Serializable {
    Integer id;
    String label;
    String [] products;

    public LabelDTO(Label label){
        this.id = label.getId();
        this.label = label.getLabel();
        this.products = label.getProducts().stream().map(Product::getName).toArray(String[]::new);
    }
}