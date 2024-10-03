package co.allconnected.fussiontech.productsservice.controllers;

import co.allconnected.fussiontech.productsservice.dtos.LabelDTO;
import co.allconnected.fussiontech.productsservice.dtos.Response;
import co.allconnected.fussiontech.productsservice.services.LabelService;
import co.allconnected.fussiontech.productsservice.utils.OperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLabel (@PathVariable String id, @ModelAttribute LabelDTO labelDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(labelService.updateLabel(id, labelDTO));
        }
        catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLabel(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(labelService.getLabel(id));
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred: " + e.getMessage()));
        }
    }

    @GetMapping()
    public ResponseEntity<?> getLabels() {
        try {
            LabelDTO[] listLabelsDTO = labelService.getLabels();
            if (listLabelsDTO.length == 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(HttpStatus.NOT_FOUND.value(), "No labels found"));
            return ResponseEntity.status(HttpStatus.OK).body(listLabelsDTO);
        } catch (OperationException e) {
            return ResponseEntity.status(e.getCode()).body(new Response(e.getCode(), e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
}
