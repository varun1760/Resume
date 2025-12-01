package com.rao.Resume.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private String name;
    private List<String> responsibilities = new ArrayList<>();
    private List<String> achievements = new ArrayList();
}
