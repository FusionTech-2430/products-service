package co.allconnected.fussiontech.productsservice.dtos;

import co.allconnected.fussiontech.productsservice.model.Rating;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class RatingDTO implements Serializable {
    private Integer idRating;
    private Integer productId;
    private String userId;
    private Integer rating;
    private String comment;
    private Instant date;

    public RatingDTO(Rating rating){
        this.idRating = rating.getId();
        this.productId = rating.getIdProduct().getId();
        this.userId = rating.getIdUser();
        this.rating = rating.getRating();
        this.comment = rating.getComment();
        this.date = rating.getDate();
    }
}
