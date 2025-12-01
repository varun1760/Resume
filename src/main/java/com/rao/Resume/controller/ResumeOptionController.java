package com.rao.Resume.controller;

import com.rao.Resume.model.*;
import com.rao.Resume.service.ResumeService;
import com.rao.Resume.service.StaticResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/resume"})
public class ResumeOptionController {
    private final ResumeService resumeService;
    private final StaticResumeService staticResumeService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ResumeOptionController(ResumeService resumeService, StaticResumeService staticResumeService, ObjectMapper objectMapper) {
        this.resumeService = resumeService;
        this.staticResumeService = staticResumeService;
        this.objectMapper = objectMapper;
    }

    @GetMapping({"/options"})
    public String options() {
        return "resume-options";
    }

    @GetMapping({"/download-template"})
    public ResponseEntity<?> downloadTemplate() {
        String template = "{\n    \"personalInfo\": {\n        \"name\": \"\",\n        \"location\": \"\",\n        \"email\": \"\",\n        \"contact\": \"\",\n        \"linkedin\": \"\",\n        \"github\": \"\"\n    },\n    \"professionalDetails\": {\n        \"summary\": \"\",\n        \"experiences\": [],\n        \"educationList\": [],\n        \"skills\": [],\n        \"learningGoals\": []\n    },\n    \"technicalSkills\": {\n        \"programmingLanguages\": [],\n        \"frameworksAndLibraries\": [],\n        \"apisAndWebTechnologies\": [],\n        \"databases\": [],\n        \"dbAccess\": [],\n        \"tools\": [],\n        \"versionControl\": [],\n        \"testing\": [],\n        \"projectManagement\": []\n    }\n}\n";
        ByteArrayResource resource = new ByteArrayResource(template.getBytes());
        return ResponseEntity.ok()
                .header("Content-Disposition", new String[]{"attachment; filename=\"resume-template.json\""})
                .body(resource);
    }

    @PostMapping({"/upload"})
    public String uploadJson(@RequestParam("file") MultipartFile file, Model model) {
        try {
            ResumeJson data = objectMapper.readValue(file.getInputStream(), ResumeJson.class);
            resumeService.saveResume(data);
            return "redirect:/resume/view";
        } catch (Exception var4) {
            model.addAttribute("error", "Invalid JSON file!");
            return "resume-view";
        }
    }

    @GetMapping({"/form"})
    public String createResumeForm(Model model) {
        ResumeJson resumeJson = new ResumeJson();
        resumeJson.setPersonalInfo(new PersonalInfo());
        resumeJson.setProfessionalDetails(new ProfessionalDetails());
        resumeJson.setTechnicalSkills(new TechnicalSkill());
        Experience exp = new Experience();
        Project project = new Project();
        exp.getProjects().add(project);
        resumeJson.getProfessionalDetails().getExperiences().add(exp);
        resumeJson.getProfessionalDetails().getEducationList().add(new Education());
        model.addAttribute("resumeJson", resumeJson);
        return "resume-form";
    }

    @PostMapping({"/form"})
    public String handleForm(
            @ModelAttribute ResumeJson resumeJson, @RequestParam(required = false) String skillsCsv,
            @RequestParam(required = false) String learningGoalsCsv) {
        if (skillsCsv != null) {
            resumeJson.getProfessionalDetails()
                    .setSkills(Arrays.stream(skillsCsv.split(","))
                            .map(String::trim)
                            .collect(Collectors.toList()));
        }

        if (learningGoalsCsv != null) {
            resumeJson.getProfessionalDetails()
                    .setLearningGoals(Arrays.stream(learningGoalsCsv.split(","))
                            .map(String::trim)
                            .collect(Collectors.toList()));
        }

        for(Experience exp : resumeJson.getProfessionalDetails().getExperiences()) {
            if (exp.getTechStack() != null && exp.getTechStack().size() == 1) {
                String csv = exp.getTechStack().get(0);
                exp.setTechStack(Arrays.stream(csv.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList())
                );
            }

            for(Project p : exp.getProjects()) {
                if (p.getResponsibilities() != null && p.getResponsibilities().size() == 1) {
                    p.setResponsibilities(Arrays.stream(p.getResponsibilities().get(0).split(","))
                            .map(String::trim)
                            .collect(Collectors.toList())
                    );
                }

                if (p.getAchievements() != null && p.getAchievements().size() == 1) {
                    p.setAchievements(Arrays.stream(p.getAchievements().get(0).split(","))
                            .map(String::trim)
                            .collect(Collectors.toList()));
                }
            }
        }

        resumeService.saveResume(resumeJson);
        return "redirect:/resume/view";
    }

    @GetMapping({"/use-default"})
    public String useDefaultResume() {
        resumeService.saveResume(staticResumeService.getStaticResume());
        return "redirect:/resume/view";
    }
}
