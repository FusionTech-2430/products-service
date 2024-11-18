package co.allconnected.fussiontech.productsservice.dtos;

import java.util.UUID;

public record ProductCreateDTO (UUID idBusiness, String name, String description, Integer stock, Double price, String status) {
}