package com.rao.Resume.service;

import com.rao.Resume.model.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class StaticResumeService {
    public ResumeJson getStaticResume() {
        PersonalInfo personalInfo = new PersonalInfo(
                "Varun Rao",
                "Delhi, India — Remote",
                "varunrao1760@gmail.com",
                "+91-1234567890",
                "https://www.linkedin.com/in/varunrao1760/",
                "https://github.com/varun1760",
                "Backend Developer at Arorian Technologies GmbH with hands-on experience in Java," +
                        " Spring Boot, Spring Security, JDBC, REST API development, and MySQL." +
                        " I build secure and efficient backend services while collaborating closely with frontend" +
                        " and QA teams to deliver reliable, high-quality applications." +
                        " Skilled in Git, AOP, multithreading, and server-side rendering with Thymeleaf," +
                        " I focus on writing maintainable code and continually improving my technical expertise.");

        String summary = "Java Backend Developer with 3+ years of experience working remotely, designing" +
                " and developing scalable backend systems and REST-ful APIs using Java and Spring Boot." +
                " Experienced in building modular microservices, writing clean and efficient code," +
                " and collaborating with cross-functional teams in agile environments." +
                " Known for continuous learning and growing responsibilities," +
                " currently contributing to production-grade enterprise systems.";

        Project project = new Project(
                "AROFINITY — Application Lifecycle Management (ALM) Tool",
                Arrays.asList("Developed and maintained REST-ful APIs and backend modules using Java and Spring Boot.",
                        "Used JDBC for database connectivity and MySQL for efficient data management.",
                        "Refactored backend components for performance optimization, reducing load times."),
                Arrays.asList("Reduced data load latency by 30% through optimized query handling.",
                        "Built real-time paging for 2000+ records.",
                        "Developed reusable export/import modules.")
        );

        Experience experience1 = Experience.builder()
                .role("Junior Software Developer")
                .company("Arorian Technologies GmbH, Eschborn (Frankfurt area), Germany")
                .duration("May 2022 – Present")
                .location("Remote")
                .techStack(List.of("Core Java", "Spring Boot", "Spring Security", "REST APIs", "MySQL", "Flyway",
                        "Multithreading", "Spring JDBC", "GitHub"))
                .projects(List.of(project))
                .build();
        List<Experience> experiences = List.of(experience1);

        Education e1 = new Education(
                "Bachelor of Technology in Computer Science",
                "Lovely Professional University",
                "Jalandhar, Punjab, India",
                "2020");
        List<Education> educations = List.of(e1);

        List<String> skills = Arrays.asList("Java 8/11", "Spring Boot", "Spring Security", "REST APIs", "MySQL",
                "Flyway", "Spring JDBC", "Git", "Postman", "Thymeleaf", "Multithreading");

        List<String> learningGoals = Arrays.asList("Docker", "AWS EC2/S3", "CI/CD", "JUnit", "Mockito", "PostgreSQL",
                "AI Integration");

        ProfessionalDetails professionalDetails = ProfessionalDetails.builder()
                .jobTitle("Junior Software Developer")
                .summary(summary)
                .experiences(experiences)
                .educationList(educations)
                .skills(skills)
                .learningGoals(learningGoals)
                .build();

        TechnicalSkill techSkill = new TechnicalSkill(
                List.of("Java 8/11", "SQL", "HTML", "CSS", "JSON"),
                List.of("Spring Boot 3", "Spring Security 6", "Spring 6", "Spring MVC", "Spring JDBC",
                        "Spring AOP (Aspect-Oriented Programming)", "Thymeleaf"),
                List.of("REST-ful APIs", "Exception Handling", "Controller Advice"),
                List.of("MySQL", "H2 Database"),
                List.of("Spring JDBC", "Flyway"),
                List.of("GitHub", "IntelliJ IDEA", "Postman", "Git Desktop", "MySQL Workbench"),
                List.of("Git"),
                List.of("Postman (API testing)"),
                List.of("Agile/Scrum", "Jira", "Microsoft Teams"));

        return getResumeJson(personalInfo, professionalDetails, techSkill);
    }

    private static ResumeJson getResumeJson(PersonalInfo personalInfo, ProfessionalDetails professionalDetails, TechnicalSkill techSkill) {
        return ResumeJson.builder().personalInfo(personalInfo).professionalDetails(professionalDetails).technicalSkills(techSkill).build();
    }
}
