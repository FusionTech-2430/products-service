package co.allconnected.fussiontech.productsservice.dtos;

import lombok.Getter;
import lombok.Setter;

public record ProductCreateDTO (String idBusiness, String name, String description, int stock, float price, String status ){
}