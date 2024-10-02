package co.allconnected.fussiontech.productsservice.controllers;

import co.allconnected.fussiontech.productsservice.dtos.ProductCreateDTO;
import co.allconnected.fussiontech.productsservice.dtos.ProductDTO;
import co.allconnected.fussiontech.productsservice.dtos.Response;
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
}
