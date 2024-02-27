package com.example.metaData.controller;

import com.example.metaData.dto.LabelStatisticsDTO;
import com.example.metaData.models.Documentation;
import com.example.metaData.models.Label;
import com.example.metaData.repositories.LabelRepository;
import com.example.metaData.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("api/label")

public class LabelController {
    @Autowired
    private LabelService labelService;
    @Autowired
    private LabelRepository labelRepository;
    @PostMapping
    public ResponseEntity<Label> createLabel(@RequestBody Label label)
    {
         labelService.SaveOrUpdate(label);
        return new ResponseEntity<>(label, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Label>>getAll()
    {
        List<Label>list=labelService.listAll();
        return new ResponseEntity<>(list,HttpStatus.OK);

    }
    @PutMapping("/{_id}")
    public ResponseEntity<Label>updateLabel(@RequestBody Label label,@PathVariable String _id)
    {
        Label updateLabel=labelService.updateLabel(label,_id);
        return new ResponseEntity<>(updateLabel,HttpStatus.OK);
    }
    @DeleteMapping("/{_id}")
    public ResponseEntity<String>deleteLabel(@PathVariable String _id)
    {
       labelService.deleteLabel(_id);
        return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
    }
//    @PostMapping("/affecter")
//    public ResponseEntity<?>affecter()
//    {
//
//    }

    @GetMapping("/statistics")
    public List<LabelStatisticsDTO> getLabelStatistics() {
        List<Label> labels = labelRepository.findAll();
        List<LabelStatisticsDTO> statistics = new ArrayList<>();

        for (Label label : labels) {
            LabelStatisticsDTO dto = new LabelStatisticsDTO();
            dto.setLabelName(label.getName());

            List<Documentation> documentations = label.getDocumentationIds();
            if (documentations != null) {
                dto.setDocumentationCount(documentations.size());
            } else {
                dto.setDocumentationCount(0); // Or you can set it to null or any default value based on your requirement
            }

            statistics.add(dto);
        }

        return statistics;
    }

    @GetMapping("/average-description-length-per-label")
    public ResponseEntity<Map<String, Double>> getAverageDescriptionLengthPerLabel() {
        Map<String, Double> averageDescriptionLengthPerLabel = labelService.calculateAverageDescriptionLengthPerLabel();
        return ResponseEntity.ok(averageDescriptionLengthPerLabel);
    }
    @GetMapping("/linear")
    public Map<String, Map<String, Long>> getLinearChartData() {
        return labelService.getLinearChartData();
    }
    @GetMapping("/{id}")
    public Label getLabelById(@PathVariable String id) {
        return labelService.getLabelById(id);
    }
}
