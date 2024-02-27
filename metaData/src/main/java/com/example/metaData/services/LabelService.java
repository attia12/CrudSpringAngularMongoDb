package com.example.metaData.services;

import com.example.metaData.models.Documentation;
import com.example.metaData.models.Label;
import com.example.metaData.repositories.DocuumentationRepository;
import com.example.metaData.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class LabelService {
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private DocuumentationRepository documentationRepository;

    public Label SaveOrUpdate(Label label) {




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

    public Map<String, Double> calculateAverageDescriptionLengthPerLabel() {
        List<Label> labels = labelRepository.findAll();
        Map<String, Double> averageDescriptionLengthPerLabel = new HashMap<>();

        for (Label label : labels) {
            double totalDescriptionLength = 0;
            List<Documentation> documentations = label.getDocumentationIds();
            int numberOfDocumentations = documentations.size();

            for (Documentation documentation : documentations) {
                if (documentation.getDescription() != null) { // Check if description is not null
                    totalDescriptionLength += documentation.getDescription().length();
                }
            }

            double averageDescriptionLength = numberOfDocumentations == 0 ? 0 : totalDescriptionLength / numberOfDocumentations;
            averageDescriptionLengthPerLabel.put(label.getName(), averageDescriptionLength);
        }

        return averageDescriptionLengthPerLabel;
    }

    public Map<String, Map<String, Long>> getLinearChartData() {
        Map<String, Map<String, Long>> chartData = new HashMap<>();

        List<Label> labels = labelRepository.findAll();

        labels.forEach(label -> {
            Map<String, Long> data = new HashMap<>();

            List<Documentation> documents = label.getDocumentationIds();

            Map<YearMonth, Long> groupedData = documents.stream()
                    .collect(Collectors.groupingBy(doc -> YearMonth.from(doc.getCreatedDate()), Collectors.counting()));

            groupedData.forEach((yearMonth, count) -> data.put(yearMonth.toString(), count));

            chartData.put(label.getName(), data);
        });

        return chartData;
    }

    public Label getLabelById(String id) {
        return labelRepository.findById(id).orElse(null);
    }
}
