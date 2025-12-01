package com.rao.Resume.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {
    private String name;
    private String location;
    private String email;
    private String contact;
    private String linkedin;
    private String github;
}
