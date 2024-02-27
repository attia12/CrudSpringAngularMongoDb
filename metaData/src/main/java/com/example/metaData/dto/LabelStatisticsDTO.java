package com.example.metaData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class LabelStatisticsDTO {
    private String labelName;
    private int documentationCount;
}
