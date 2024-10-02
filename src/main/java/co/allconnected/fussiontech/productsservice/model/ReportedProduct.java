package co.allconnected.fussiontech.productsservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "reported_product", schema = "all_connected_products")
public class ReportedProduct {
    @Id
    @Column(name = "id_product", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @Column(name = "reason", nullable = false, length = 45)
    private String reason;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "report_date", nullable = false)
    private Instant reportDate;

}