package com.example.metaData;

import com.example.metaData.models.Documentation;
import com.example.metaData.models.Label;
import com.example.metaData.repositories.DocuumentationRepository;
import com.example.metaData.repositories.LabelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class MetaDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetaDataApplication.class, args);
	}
//	@Bean
//	public CommandLineRunner dataInitializer(LabelRepository labelRepository, DocuumentationRepository docuumentationRepository) {
//		return args -> {
	// Create and save documentation objects
//			List<Documentation> documentationList = new ArrayList<>();
//			for (int i = 1; i <= 2; i++) {
//				Documentation documentation = new Documentation();
//				documentation.setDescription("Description " + i);
//				documentation.setCountry("Country " + i);
//				documentation.setCreatedDate(generateRandomDate(LocalDate.of(2020, 1, 1), LocalDate.of(2025, 12, 31))); // Set default createdDate
//				documentationList.add(documentation);
//			}
//			docuumentationRepository.saveAll(documentationList);
//
//
//			List<Documentation> savedDocs = docuumentationRepository.findAll();
//
//
//			for (int i = 0; i < savedDocs.size(); i++) {
//				Label label = new Label();
//				label.setName("Label " + (i + 1));
//				List<Documentation> docsForLabel = new ArrayList<>();
//				docsForLabel.add(savedDocs.get(i));
//				label.setDocumentationIds(docsForLabel);
//				labelRepository.save(label);
//			}
//		};
//			for (int i = 1; i <= 2; i++) {
//				Documentation documentation = new Documentation();
//				documentation.setDescription("Description " + i);
//				documentation.setCountry("Country " + i);
//				documentation.setCreatedDate(generateRandomDate(LocalDate.of(2020, 1, 1), LocalDate.of(2025, 12, 31)));
//				docuumentationRepository.save(documentation);
//			}
//
//
//			List<Documentation> savedDocs = docuumentationRepository.findAll();
//
//
//			List<Label> labelList = labelRepository.findAll();
//
//
//			for (Label label : labelList) {
//				for (int i = 0; i < savedDocs.size(); i++) {
//					label.getDocumentationIds().add(savedDocs.get(i));
//				}
//				labelRepository.save(label);
//			}
//		};
//
//	}
//	private LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
//		long startEpochDay = startDate.toEpochDay();
//		long endEpochDay = endDate.toEpochDay();
//		long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
//		return LocalDate.ofEpochDay(randomEpochDay);
//	}
//}
}
