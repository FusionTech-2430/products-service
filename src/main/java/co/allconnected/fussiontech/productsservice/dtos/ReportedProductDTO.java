package co.allconnected.fussiontech.productsservice.dtos;

import co.allconnected.fussiontech.productsservice.model.ReportedProduct;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public class ReportedProductDTO implements Serializable {
    private Integer productId;
    private String reason;
    private String description;
    private Instant reportDate;

    public ReportedProductDTO (ReportedProduct reportedProduct){
        this.productId = reportedProduct.getProduct().getId();
        this.reason = reportedProduct.getReason();
        this.description = reportedProduct.getDescription();
        this.reportDate = reportedProduct.getReportDate();
    }
}