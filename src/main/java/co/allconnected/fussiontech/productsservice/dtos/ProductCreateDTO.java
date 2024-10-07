package co.allconnected.fussiontech.productsservice.dtos;

public record ProductCreateDTO (String idBusiness, String name, String description, int stock, float price, String status ){
}