package com.example.metaData.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document

public class Documentation {
    @Id
    private String _id;
    private String description;
    private String country;

}
