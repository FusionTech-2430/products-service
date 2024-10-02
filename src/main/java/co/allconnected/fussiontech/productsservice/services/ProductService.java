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

    /*
    OPERATIONS PRODUCTS
     */
    // Create a product
    public ProductDTO createProduct(ProductCreateDTO productDto, MultipartFile photo) throws IOException {
        Product product = new Product(productDto);
        if (photo != null && !photo.isEmpty()) {
            String extension = photo.getContentType();
            product.setPhotoUrl(firebaseService.uploadImgProduct(product.getName(), product.getId().toString(), extension, photo));
        }
        return new ProductDTO(productRepository.save(product));
    }

    // Update a product
    public ProductDTO updateProduct (String id, ProductCreateDTO productDTO, MultipartFile photo) throws IOException{
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice((double) productDTO.getPrice());
            product.setStock(productDTO.getStock());

            if (photo != null && !photo.isEmpty()) {
                if (product.getPhotoUrl() != null) {
                    firebaseService.deleteImgProduct(product.getName(), product.getId().toString());
                }
                String extension = photo.getContentType();
                product.setPhotoUrl(firebaseService.uploadImgProduct(product.getName(), product.getId().toString(), extension, photo));
            }
            return new ProductDTO(productRepository.save(product));
        }
        else{
            throw new OperationException(404, "Product not found");
        }
    }

    // Get a product by id
    public ProductDTO getProduct (String id){
        return productRepository.findById(id)
                .map(ProductDTO::new)
                .orElseThrow(() -> new OperationException(404, "Product not found"));

     /*
    OPERATIONS LABELS
     */

    /*
    OPERATIONS REPORTS
     */
}
