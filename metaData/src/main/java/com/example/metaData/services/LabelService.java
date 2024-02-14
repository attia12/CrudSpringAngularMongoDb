package com.example.metaData.services;

import com.example.metaData.models.Documentation;
import com.example.metaData.models.Label;
import com.example.metaData.repositories.DocuumentationRepository;
import com.example.metaData.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class LabelService {
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private DocuumentationRepository documentationRepository;

    public Label SaveOrUpdate(Label label) {


//        List<Documentation> savedDocumentation = new ArrayList<>();
//        for (Documentation documentation : label.getDocumentationIds()) {
//            savedDocumentation.add(documentationRepository.save(documentation));
//        }
//
//
//        label.setDocumentationIds(savedDocumentation);
//
//
//        return labelRepository.save(label);

        if (label.getDocumentationIds() != null) {

            List<Documentation> savedDocumentation = new ArrayList<>();
            for (Documentation documentation : label.getDocumentationIds()) {
                savedDocumentation.add(documentationRepository.save(documentation));
            }
            label.setDocumentationIds(savedDocumentation);
        }


        return labelRepository.save(label);
    }

    public List<Label> listAll() {
        return labelRepository.findAll();
    }

    public Label updateLabel(Label label, String id) {
        Label label1=labelRepository.findById(id).get();
        if(label1 != null )
        {
            label1.setName(label.getName());
        }
        labelRepository.save(label1);

        return label1;
    }

    public void deleteLabel(String id) {
        Optional<Label> optionalLabel = labelRepository.findById(id);
        if (optionalLabel.isPresent()) {
            Label label = optionalLabel.get();


            if (label.getDocumentationIds() != null) {
                for (Documentation doc : label.getDocumentationIds()) {
                    documentationRepository.deleteById(doc.get_id());
                }
            }

            labelRepository.delete(label);
        }

    }
}
