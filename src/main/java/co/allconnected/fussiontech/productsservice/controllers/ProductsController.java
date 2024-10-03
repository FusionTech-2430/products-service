package co.allconnected.fussiontech.productsservice.controllers;

import co.allconnected.fussiontech.productsservice.dtos.*;
import co.allconnected.fussiontech.productsservice.services.ProductService;
import co.allconnected.fussiontech.productsservice.utils.OperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    /*
    CRUD PRODUCTS
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@ModelAttribute ProductCreateDTO product, @RequestParam(value = "photo_url", required = false) MultipartFile photo) {
        try {
            ProductDTO productDTO = productService.createProduct(product, photo);
            return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @ModelAttribute ProductCreateDTO product, @RequestParam(value = "photo_url", required = false) MultipartFile photo) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, product, photo));
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct(id));
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }
    @GetMapping("/businesses/{id_business}")
    public ResponseEntity<?> getProductsByBusiness(@PathVariable String id_business) {
        try {
            ProductDTO[] listProductsDTO = productService.getProductsByBusiness(id_business);
            if (listProductsDTO.length == 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(HttpStatus.NOT_FOUND.value(), "No products found"));
            return ResponseEntity.status(HttpStatus.OK).body(listProductsDTO);
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
    @GetMapping
    public ResponseEntity<?> getProducts() {
        try {
            ProductDTO[] listProductsDTO = productService.getProducts();
            if (listProductsDTO.length == 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(HttpStatus.NOT_FOUND.value(), "No products found"));
            return ResponseEntity.status(HttpStatus.OK).body(listProductsDTO);
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpStatus.OK.value(), "Product deleted"));
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }
    /*
    OPERATIONS PRODUCTS - LABELS
    */
    @PostMapping("({id_product}/labels/{id_label}")
    public ResponseEntity<?> addLabel(@PathVariable String id_product, @PathVariable String id_label) {
        try {
            productService.assignLabelToProduct(id_product, id_label);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Label assigned to product successfully.");
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }
    @DeleteMapping("/{id_product}/labels/{id_label}")
    public ResponseEntity<?> deleteLabel(@PathVariable String id_product, @PathVariable String id_label) {
        try {
            productService.deleteLabelFromProduct(id_product, id_label);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Label deleted from product successfully.");
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }
    /*
    OPERATIONS PRODUCTS - REPORTS
    */
    @PostMapping("/{id_product}/report")
    public ResponseEntity<?> addReport(@PathVariable String id_product, @ModelAttribute ReportedProductCreateDTO reportedProductCreateDTO){
        try {
            ReportedProductDTO reportedProductDTO = productService.reportProduct(id_product, reportedProductCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(reportedProductDTO);
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id_product}/report")
    public ResponseEntity<?> deleteReport(@PathVariable String id_product){
        try {
            productService.deleteReport(id_product);
            return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpStatus.OK.value(), "Report deleted"));
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }

}
