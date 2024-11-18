package co.allconnected.fussiontech.productsservice.model;

import co.allconnected.fussiontech.productsservice.dtos.RatingCreateDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "rating", schema = "all_connected_products")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rating", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_product", nullable = false)
    private Product idProduct;

    @Column(name = "id_user", nullable = false, length = 28)
    private String idUser;

    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", length = 500)
    private String comment;

    public Rating (RatingCreateDTO dto) {
        this.idProduct = new Product();
        this.idProduct.setId(dto.productId());
        this.idUser = dto.userId();
        this.rating = dto.rating();
        this.comment = dto.comment();
        this.date = Instant.now();
    }

}