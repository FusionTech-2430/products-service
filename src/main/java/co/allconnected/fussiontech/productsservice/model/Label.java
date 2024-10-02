package co.allconnected.fussiontech.productsservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "label", schema = "all_connected_products")
public class Label {
    public Label (String label){
        this.label = label;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_label", nullable = false)
    private Integer id;

    @Column(name = "label", nullable = false, length = 45)
    private String label;

    @ManyToMany
    @JoinTable(name = "product_label",
            joinColumns = @JoinColumn(name = "id_label"),
            inverseJoinColumns = @JoinColumn(name = "id_announcement"))
    private Set<Product> products = new LinkedHashSet<>();
}