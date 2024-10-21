package co.allconnected.fussiontech.productsservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ProductLabelId implements Serializable {
    private static final long serialVersionUID = -5894418577482572878L;
    @Column(name = "id_announcement", nullable = false)
    private Integer idAnnouncement;

    @Column(name = "id_label", nullable = false)
    private Integer idLabel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductLabelId entity = (ProductLabelId) o;
        return Objects.equals(this.idLabel, entity.idLabel) &&
                Objects.equals(this.idAnnouncement, entity.idAnnouncement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLabel, idAnnouncement);
    }

}