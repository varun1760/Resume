package com.rao.Resume.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalSkill {
    private List<String> programmingLanguages = new ArrayList();
    private List<String> frameworksAndLibraries = new ArrayList<>();
    private List<String> apisAndWebTechnologies = new ArrayList();
    private List<String> databases = new ArrayList();
    private List<String> dbAccess = new ArrayList();
    private List<String> tools = new ArrayList();
    private List<String> versionControl = new ArrayList();
    private List<String> testing = new ArrayList();
    private List<String> projectManagement = new ArrayList();
}
