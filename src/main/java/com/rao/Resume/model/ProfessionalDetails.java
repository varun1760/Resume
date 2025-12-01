package com.rao.Resume.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessionalDetails {
    private String summary;
    private List<Experience> experiences = new ArrayList<>();
    private List<Education> educationList = new ArrayList();
    private List<String> skills = new ArrayList();
    private List<String> learningGoals = new ArrayList();
}
