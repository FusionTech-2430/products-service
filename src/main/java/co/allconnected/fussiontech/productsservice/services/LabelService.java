package co.allconnected.fussiontech.productsservice.services;
import java.util.Optional;
import co.allconnected.fussiontech.productsservice.dtos.LabelDTO;
import co.allconnected.fussiontech.productsservice.model.Label;
import co.allconnected.fussiontech.productsservice.repository.LabelRepository;
import co.allconnected.fussiontech.productsservice.utils.OperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelService {
    private final LabelRepository labelRepository;
    @Autowired
    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    // Create a label
    public LabelDTO createLabel(String name) {
        Label label = new Label();
        label.setLabel(name);
        return new LabelDTO(labelRepository.save(label));
    }
    // Update a label
    public LabelDTO updateLabel(String id, LabelDTO labelDTO){
        Optional <Label> labelOptional = labelRepository.findById(id);
        if (labelOptional.isPresent()) {
            Label label = labelOptional.get();
            label.setLabel(labelDTO.getLabel());
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
}
