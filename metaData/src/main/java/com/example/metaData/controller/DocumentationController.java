package com.example.metaData.controller;

import com.example.metaData.models.Documentation;
import com.example.metaData.models.Label;
import com.example.metaData.services.DocumentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/documentation")

public class DocumentationController {
    @Autowired
    private DocumentationService documentationService;
    @PostMapping
    public ResponseEntity<Documentation> createDocumentation(@RequestBody Documentation documentation)
    {
        documentationService.SaveDocumentation(documentation);
        return new ResponseEntity<>(documentation, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Documentation>>getAll()
    {
        List<Documentation>list=documentationService.listAll();
        return new ResponseEntity<>(list,HttpStatus.OK);

    }
    @PutMapping("/{id}")
    public ResponseEntity<Documentation>updateDocumentation(@RequestBody Documentation documentation,@PathVariable String id)
    {
        Documentation updateDocumentation=documentationService.updateDocumentation(id,documentation);
        return new ResponseEntity<>(updateDocumentation,HttpStatus.OK);
    }
    @DeleteMapping("/{labelId}/{documenId}")
    public ResponseEntity<String>deleteDocumentation(@PathVariable String labelId,@PathVariable String documenId)
    {
        documentationService.deleteDocumentation(labelId,documenId);
        return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
    }
    @PostMapping("/affecter/{labelId}")
    public ResponseEntity<String>affecterDocALabel(@RequestBody Documentation doc , @PathVariable String labelId)
    {
        documentationService.affecter(labelId,doc);
        return new ResponseEntity("affecter Sussess",HttpStatus.OK);
    }
}
