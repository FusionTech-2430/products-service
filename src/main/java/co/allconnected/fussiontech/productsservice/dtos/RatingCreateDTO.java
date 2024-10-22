package co.allconnected.fussiontech.productsservice.dtos;

public record RatingCreateDTO(Integer productId, String userId, Integer rating, String comment) {
}
