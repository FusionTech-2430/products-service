package co.allconnected.fussiontech.productsservice.model;

import co.allconnected.fussiontech.productsservice.dtos.ProductCreateDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product", schema = "all_connected_products")
public class Product {
    public Product (ProductCreateDTO dto) {
        this.idBusiness = dto.idBusiness();
        this.name = dto.name();
        this.description = dto.description();
        this.stock = dto.stock();
        this.price = dto.price();
        this.status = dto.status();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product", nullable = false)
    private Integer id;

    @Column(name = "id_business", nullable = false)
    private UUID idBusiness;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "description", nullable = false, length = 280)
    private String description;

    @Column(name = "photo_url", length = 700)
    private String photoUrl;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "status", nullable = false, length = Integer.MAX_VALUE)
    private String status;

    @ManyToMany(mappedBy = "products")
    private Set<Label> labels = new LinkedHashSet<>();

}