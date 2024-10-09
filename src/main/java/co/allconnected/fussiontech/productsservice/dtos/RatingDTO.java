package co.allconnected.fussiontech.productsservice.dtos;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
@Getter
@Setter
public class RatingDTO implements Serializable {
    private Integer idRating;
    private Integer productId;
    private Integer userId;
    private Integer rating;
    private String comment;
    private String date;
}
