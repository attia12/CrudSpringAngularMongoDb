package com.example.metaData.models;

import lombok.Data;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;



import java.util.List;

@Data
@Document

public class Label {
    @Id
    private String _id;
    private String name;


    private List<Documentation> documentationIds;
}
