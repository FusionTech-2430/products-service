package co.allconnected.fussiontech.productsservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_label", schema = "all_connected_products")
public class ProductLabel {
    @EmbeddedId
    private ProductLabelId id;

    @MapsId("idAnnouncement")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_announcement", nullable = false)
    private Product idAnnouncement;

    @MapsId("idLabel")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_label", nullable = false)
    private Label idLabel;

}