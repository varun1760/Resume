package com.rao.Resume.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Education {
    private String degree;
    private String institute;
    private String location;
    private String year;
}
