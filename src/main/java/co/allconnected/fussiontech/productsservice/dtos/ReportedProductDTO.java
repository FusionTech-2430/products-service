package co.allconnected.fussiontech.productsservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public class ReportedProductDTO implements Serializable {
    private Integer id;
    private Integer productId;
    private String reason;
    private String description;
    private Instant reportDate;
}