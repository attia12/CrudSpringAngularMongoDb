package com.example.metaData.services;

import com.example.metaData.models.Documentation;
import com.example.metaData.models.Label;
import com.example.metaData.repositories.DocuumentationRepository;
import com.example.metaData.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service


public class DocumentationService {

    @Autowired
    private DocuumentationRepository docuumentationRepository;
    @Autowired
    private LabelRepository labelRepository;
    public void SaveDocumentation(Documentation documentation) {
        docuumentationRepository.save(documentation);


    }

    public List<Documentation> listAll() {
        return docuumentationRepository.findAll();
    }

    public Documentation updateDocumentation(String documentationId, Documentation updatedDocumentation) {

        Documentation existingDocumentation = docuumentationRepository.findById(documentationId)
                .orElseThrow(() -> new IllegalArgumentException("Documentation not found"));
        existingDocumentation.setDescription(updatedDocumentation.getDescription());
        existingDocumentation.setCountry(updatedDocumentation.getCountry());
        Documentation savedDocumentation = docuumentationRepository.save(existingDocumentation);


        List<Label> labels = labelRepository.findAll();


        for (Label label : labels) {
            List<Documentation> documentationIds = label.getDocumentationIds();
            boolean updated = false;
            for (int i = 0; i < documentationIds.size(); i++) {
                if (documentationIds.get(i).get_id().equals(documentationId)) {
                    documentationIds.set(i, savedDocumentation);
                    updated = true;
                    break;
                }
            }
            if (updated) {
                label.setDocumentationIds(documentationIds);
                labelRepository.save(label);
            }
        }
        return savedDocumentation;
    }



    public void deleteDocumentation(String labelId, String documentId) {
        Optional<Label> optionalLabel = labelRepository.findById(labelId);
        if (optionalLabel.isPresent()) {
            Label label = optionalLabel.get();
            List<Documentation> documentationList = label.getDocumentationIds();
            if (documentationList != null) {
                Iterator<Documentation> iterator = documentationList.iterator();
                while (iterator.hasNext()) {
                    Documentation doc = iterator.next();
                    if (doc.get_id().equals(documentId)) {
                        iterator.remove(); // Remove the document from the list
                        docuumentationRepository.delete(doc); // Delete the document from the repository
                    }
                }

                labelRepository.save(label);
            }
        }

    }

    public void affecter(String labelId, Documentation doc) {
        Label labelFound = labelRepository.findById(labelId).orElse(null);
        if (labelFound != null) {
            List<Documentation> documentationList = labelFound.getDocumentationIds();
            if (documentationList == null) {
                documentationList = new ArrayList<>();
                labelFound.setDocumentationIds(documentationList);
            }
            documentationList.add(doc);
            docuumentationRepository.save(doc);
            labelRepository.save(labelFound);

        }
    }
}
