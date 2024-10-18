package co.allconnected.fussiontech.productsservice.services;
import co.allconnected.fussiontech.productsservice.dtos.ProductCreateDTO;
import co.allconnected.fussiontech.productsservice.dtos.ProductDTO;
import co.allconnected.fussiontech.productsservice.dtos.ReportedProductCreateDTO;
import co.allconnected.fussiontech.productsservice.dtos.ReportedProductDTO;
import co.allconnected.fussiontech.productsservice.model.Label;
import co.allconnected.fussiontech.productsservice.model.Product;
import co.allconnected.fussiontech.productsservice.model.ReportedProduct;
import co.allconnected.fussiontech.productsservice.repository.LabelRepository;
import co.allconnected.fussiontech.productsservice.repository.ProductRepository;
import co.allconnected.fussiontech.productsservice.repository.ReportsRepository;
import co.allconnected.fussiontech.productsservice.utils.OperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final FirebaseService firebaseService;
    private final LabelRepository labelRepository;
    private final ReportsRepository reportsRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, LabelRepository labelRepository, ReportsRepository reportsRepository, FirebaseService firebaseService) {
        this.productRepository = productRepository;
        this.firebaseService = firebaseService;
        this.labelRepository = labelRepository;
        this.reportsRepository = reportsRepository;
    }

    /*
    OPERATIONS PRODUCTS
     */
    // Create a product
    public ProductDTO createProduct(ProductCreateDTO productDto, MultipartFile photo) throws IOException {
        Product product = new Product(productDto);
        System.out.println("Product name: " + productDto.name());
        try {
            if (photo != null && !photo.isEmpty()) {
                String extension = photo.getContentType();
                product.setPhotoUrl(firebaseService.uploadImgProduct(product.getName(), product.getId().toString(), extension, photo));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Asegúrate de que cualquier error relacionado con la subida de la foto se capture y se imprima.
        }

        try {
            return new ProductDTO(productRepository.save(product));
        } catch (Exception e) {
            e.printStackTrace(); // Asegúrate de que cualquier error relacionado con la base de datos se capture y se imprima.
            throw new IOException("Error al guardar el producto", e); // Lanza una excepción si es necesario.
        }
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
        return productRepository.findByIdBusiness(UUID.fromString(businessId))
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
         Optional<Label> labelOptional = labelRepository.findById(labelId);
         if (productOptional.isPresent() && labelOptional.isPresent()) {
             Product product = productOptional.get();
             Label label = labelOptional.get();
             boolean relationshipExists = product.getLabels().stream()
                     .anyMatch(l -> l.getId().equals(label.getId()));
             if (!relationshipExists) {
                 product.getLabels().add(label);
                 label.getProducts().add(product);
                 productRepository.save(product);
                 labelRepository.save(label);
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
                label.getProducts().remove(product);
                productRepository.save(product);
                labelRepository.save(label);
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
    public ReportedProductDTO reportProduct(String idProduct, ReportedProductCreateDTO reportedDTO) {
        Optional<Product> productOptional = productRepository.findById(String.valueOf(Integer.parseInt(idProduct)));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            System.out.println(reportedDTO.reason());
            System.out.println(reportedDTO.description());
            ReportedProduct reportedProduct = new ReportedProduct(reportedDTO);

            reportedProduct.setProduct(product);
            reportedProduct.setDescription(reportedDTO.description());
            reportedProduct.setReason(reportedDTO.reason());
            reportedProduct.setReportDate(Instant.now());

            ReportedProduct savedReportedProduct = reportsRepository.save(reportedProduct);

            return new ReportedProductDTO(savedReportedProduct);
        } else {
            throw new OperationException(404, "Product not found");
        }
    }

    public ReportedProductDTO updateProductReport (String idProduct, ReportedProductCreateDTO reportedDTO){
        Optional<ReportedProduct> reportOptional = reportsRepository.findById(String.valueOf(Integer.parseInt(idProduct)));
        if (reportOptional.isPresent()){
            ReportedProduct report = reportOptional.get();
            report.setReason(reportedDTO.reason());
            report.setDescription(reportedDTO.description());
            return new ReportedProductDTO(reportsRepository.save(report));
        }
        else {
            throw new OperationException(404, "Report not found");
        }
    }


    public void deleteReport (String idProduct){
        Optional<ReportedProduct> reportOptional = reportsRepository.findById(String.valueOf(Integer.parseInt(idProduct)));
        if (reportOptional.isPresent()){
            reportsRepository.delete(reportOptional.get());
        }
        else {
            throw new OperationException(404, "Report not found");
        }
    }
    public ReportedProductDTO getReport (String idProduct){
        return reportsRepository.findById(String.valueOf(Integer.parseInt(idProduct)))
                .map(ReportedProductDTO::new)
                .orElseThrow(() -> new OperationException(404, "Report not found"));
    }
    public ReportedProductDTO[] getReports (){
        return reportsRepository.findAll()
                .stream()
                .map(ReportedProductDTO::new)
                .toArray(ReportedProductDTO[]::new);
    }
}
