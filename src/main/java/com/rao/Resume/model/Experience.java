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
public class Experience {
    private String role;
    private String company;
    private String duration;
    private String location;
    private List<String> techStack = new ArrayList<>();
    private List<Project> projects = new ArrayList();
}
