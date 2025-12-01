package com.rao.Resume.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeJson {
    private PersonalInfo personalInfo;
    private ProfessionalDetails professionalDetails;
    private TechnicalSkill technicalSkills;
}
