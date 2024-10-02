package co.allconnected.fussiontech.productsservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public record ProductCreateDTO (UUID idBusiness, String name, String description, int stock, float price, String status ){
}
