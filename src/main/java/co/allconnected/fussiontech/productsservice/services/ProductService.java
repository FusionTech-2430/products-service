package co.allconnected.fussiontech.productsservice.services;

import co.allconnected.fussiontech.productsservice.dtos.ProductCreateDTO;
import co.allconnected.fussiontech.productsservice.dtos.ProductDTO;
import co.allconnected.fussiontech.productsservice.model.Product;
import co.allconnected.fussiontech.productsservice.repository.ProductRepository;
import co.allconnected.fussiontech.productsservice.utils.OperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final FirebaseService firebaseService;

    @Autowired
    public ProductService(ProductRepository productRepository, FirebaseService firebaseService) {
        this.productRepository = productRepository;
        this.firebaseService = firebaseService;
    }

    public ProductDTO createProduct(ProductCreateDTO productDto, MultipartFile photo) throws IOException {
        Product product = new Product(productDto);
        if (photo != null && !photo.isEmpty()) {
            String extension = photo.getContentType();
            product.setPhotoUrl(firebaseService.uploadImgProduct(product.getName(), product.getId().toString(), extension, photo));
        }
        return new ProductDTO(productRepository.save(product));
    }

}
