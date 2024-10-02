package co.allconnected.fussiontech.productsservice.controllers;

import co.allconnected.fussiontech.productsservice.dtos.LabelDTO;
import co.allconnected.fussiontech.productsservice.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/labels")
public class LabelsController {
    private final LabelService labelService;

    @Autowired
    public LabelsController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping
    public ResponseEntity<LabelDTO> createLabel(String name) {
        try{
            LabelDTO labelDTO = labelService.createLabel(name);
            return ResponseEntity.status(HttpStatus.CREATED).body(labelDTO);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
