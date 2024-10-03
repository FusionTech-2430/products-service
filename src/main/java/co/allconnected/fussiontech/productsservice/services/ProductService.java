package co.allconnected.fussiontech.productsservice.services;
import co.allconnected.fussiontech.productsservice.dtos.ProductCreateDTO;
import co.allconnected.fussiontech.productsservice.dtos.ProductDTO;
import co.allconnected.fussiontech.productsservice.model.Label;
import co.allconnected.fussiontech.productsservice.model.Product;
import co.allconnected.fussiontech.productsservice.repository.LabelRepository;
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
    private final LabelRepository labelRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, LabelRepository labelRepository, FirebaseService firebaseService) {
        this.productRepository = productRepository;
        this.firebaseService = firebaseService;
        this.labelRepository = labelRepository;
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
    public ProductDTO updateProduct(String id, ProductCreateDTO productDTO, MultipartFile photo) throws IOException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productDTO.name());
            product.setDescription(productDTO.description());
            product.setPrice((double) productDTO.price());
            product.setStock(productDTO.stock());

            if (photo != null && !photo.isEmpty()) {
                if (product.getPhotoUrl() != null) {
                    firebaseService.deleteImgProduct(product.getName(), product.getId().toString());
                }
                String extension = photo.getContentType();
                product.setPhotoUrl(firebaseService.uploadImgProduct(product.getName(), product.getId().toString(), extension, photo));
            }
            return new ProductDTO(productRepository.save(product));
        } else {
            throw new OperationException(404, "Product not found");
        }
    }
    // Get a product by id
    public ProductDTO getProduct(String id) {
        return productRepository.findById(id)
                .map(ProductDTO::new)
                .orElseThrow(() -> new OperationException(404, "Product not found"));
    }
    // Get all products
    public ProductDTO [] getProducts (){
        return productRepository.findAll()
                .stream()
                .map(ProductDTO::new)
                .toArray(ProductDTO[]::new);
    }
    // Get all products from a business
    public ProductDTO [] getProductsByBusiness(String businessId) {
        return productRepository.findByIdBusiness(businessId)
                .stream()
                .map(ProductDTO::new)
                .toArray(ProductDTO[]::new);
    }

    // Delete a product
    public void deleteProduct(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getPhotoUrl() != null) {
                firebaseService.deleteImgProduct(product.getName(), product.getId().toString());
            }
            productRepository.delete(product);
        } else {
            throw new OperationException(404, "Product not found");
        }
    }
     /*
    OPERATIONS LABELS
     */
    public void assignLabelToProduct(String productId, String labelId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional <Label> labelOptional = labelRepository.findById(labelId);
        if (productOptional.isPresent() && labelOptional.isPresent()) {
            Product product = productOptional.get();
            Label label = labelOptional.get();
            boolean relationshipExists = product.getLabels().stream()
                    .anyMatch(l -> l.getId().equals(label.getId()));
            if (!relationshipExists) {
                product.getLabels().add(label);
                productRepository.save(product);
            } else {
                throw new OperationException(409, "Label already assigned to product");
            }
        } else {
            throw new OperationException(404, "Product or Label not found");
        }
    }

    public void deleteLabelFromProduct(String productId, String labelId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional <Label> labelOptional = labelRepository.findById(labelId);
        if (productOptional.isPresent() && labelOptional.isPresent()) {
            Product product = productOptional.get();
            Label label = labelOptional.get();
            boolean relationshipExists = product.getLabels().stream()
                    .anyMatch(l -> l.getId().equals(label.getId()));
            if (relationshipExists) {
                product.getLabels().remove(label);
                productRepository.save(product);
            } else {
                throw new OperationException(409, "Label not assigned to product");
            }
        } else {
            throw new OperationException(404, "Product or Label not found");
        }
    }

    /*
    OPERATIONS REPORTS
     */

}
