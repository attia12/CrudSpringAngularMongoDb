package com.example.metaData.controller;

import com.example.metaData.models.Label;
import com.example.metaData.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/label")

public class LabelController {
    @Autowired
    private LabelService labelService;
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
}
