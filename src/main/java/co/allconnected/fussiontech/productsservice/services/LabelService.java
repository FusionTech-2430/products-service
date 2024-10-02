package co.allconnected.fussiontech.productsservice.services;

import co.allconnected.fussiontech.productsservice.dtos.LabelDTO;
import co.allconnected.fussiontech.productsservice.model.Label;
import co.allconnected.fussiontech.productsservice.repository.LabelRepository;
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
}
