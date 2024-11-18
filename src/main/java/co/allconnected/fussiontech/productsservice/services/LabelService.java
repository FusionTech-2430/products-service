package co.allconnected.fussiontech.productsservice.services;
import java.util.List;
import java.util.Optional;
import co.allconnected.fussiontech.productsservice.dtos.LabelDTO;
import co.allconnected.fussiontech.productsservice.model.Label;
import co.allconnected.fussiontech.productsservice.model.Product;
import co.allconnected.fussiontech.productsservice.repository.LabelRepository;
import co.allconnected.fussiontech.productsservice.repository.ProductRepository;
import co.allconnected.fussiontech.productsservice.utils.OperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelService {
    private final LabelRepository labelRepository;
    private final ProductRepository productRepository;
    @Autowired
    public LabelService(LabelRepository labelRepository, ProductRepository productRepository) {
        this.labelRepository = labelRepository;
        this.productRepository = productRepository;
    }

    // Create a label
    public LabelDTO createLabel(String name) {
        Label label = new Label();
        label.setLabel(name);
        return new LabelDTO(labelRepository.save(label));
    }
    // Update a label
    public LabelDTO updateLabel(String id, String labelText){
        Optional <Label> labelOptional = labelRepository.findById(id);
        if (labelOptional.isPresent()) {
            Label label = labelOptional.get();
            label.setLabel(labelText);
            return new LabelDTO(labelRepository.save(label));
        } else {
            throw new OperationException(404, "Label not found");
        }
    }
    // Get a label by id
    public LabelDTO getLabel(String id) {
        return labelRepository.findById(id)
                .map(LabelDTO::new)
                .orElseThrow(() -> new OperationException(404, "Label not found"));
    }
    // Get all labels
    public LabelDTO [] getLabels() {
        return labelRepository.findAll().stream()
                .map(LabelDTO::new)
                .toArray(LabelDTO[]::new);
    }
    public void deleteLabel (String id){
        Optional <Label> labelOptional = labelRepository.findById(id);
        if (labelOptional.isPresent()) {
            // Delete from products the label by relationship
            List<Product> products = productRepository.findAll();
            for (Product product : products) {
                product.getLabels().remove(labelOptional.get());
                productRepository.save(product);
            }

            Label label = labelOptional.get();
            labelRepository.delete(label);
        } else {
            throw new OperationException(404, "Label not found");
        }
    }
}
