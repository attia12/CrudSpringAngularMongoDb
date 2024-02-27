package com.example.metaData.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document

public class Documentation {
    @Id
    private String _id;
    private String description;
    private String country;

    private LocalDate createdDate;
    public Documentation() {
        this.createdDate = LocalDate.now();
    }

}
