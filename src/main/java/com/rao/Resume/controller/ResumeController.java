package com.rao.Resume.controller;

import com.rao.Resume.model.PersonalInfo;
import com.rao.Resume.model.ProfessionalDetails;
import com.rao.Resume.model.ResumeJson;
import com.rao.Resume.service.ResumeService;
import com.rao.Resume.service.StaticResumeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
public class ResumeController {
    private final ResumeService resumeService;
    private final StaticResumeService staticResumeService;

    @Autowired
    public ResumeController(ResumeService resumeService, StaticResumeService staticResumeService) {
        this.resumeService = resumeService;
        this.staticResumeService = staticResumeService;
    }

    @GetMapping({"/resume/view"})
    public String displayResume(Model model, HttpSession session) {
        if (!resumeService.hasResume()) {
            return "redirect:/resume/options";
        } else {
            ResumeJson resumeJson = resumeService.getResume();
            model.addAttribute("info", resumeJson.getPersonalInfo());
            model.addAttribute("jobTitle", resumeJson.getProfessionalDetails().getJobTitle());
            model.addAttribute("summary", resumeJson.getProfessionalDetails().getSummary());
            model.addAttribute("experiences", resumeJson.getProfessionalDetails().getExperiences());
            model.addAttribute("skills", resumeJson.getProfessionalDetails().getSkills());
            model.addAttribute("educationList", resumeJson.getProfessionalDetails().getEducationList());
            model.addAttribute("learningGoals", resumeJson.getProfessionalDetails().getLearningGoals());
            model.addAttribute("techSkills", resumeJson.getTechnicalSkills());
            String base64 = resumeService.getProfileImageBase64();
            String navBase64 = resumeService.getNavImageBase64();
            model.addAttribute("profileImage", base64);
            model.addAttribute("navProfileImage", navBase64);
            session.setAttribute("profileImage", navBase64);
            return "resume-view";
        }
    }

    @GetMapping({"/resume/download/pdf"})
    public void downloadPdf(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=resume.pdf");
        this.resumeService.generatePdfV3(response);
    }

    @GetMapping({"/resume/download/docx"})
    public void downloadDocx(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=resume.docx");
        this.resumeService.generateDocx(response);
    }

    @PostMapping({"/resume/upload-profile"})
    @ResponseBody
    public String uploadProfile(@RequestParam("file") MultipartFile file, HttpSession session) {
        try {
            this.resumeService.saveProfileImage(file);
            session.setAttribute("profileImage", resumeService.getNavImageBase64());
            return "ok";
        } catch (Exception var4) {
            return "error";
        }
    }

    @GetMapping({"/logout"})
    public String logout(HttpSession session) {
        resumeService.resetProfileImage();
        resumeService.clearResume();
        session.invalidate();
        return "redirect:/home";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        ResumeJson resumeJson = resumeService.getResume();

        // If resume is missing, create EMPTY placeholders instead of redirecting
        if (resumeJson == null) {
            model.addAttribute("info", new PersonalInfo("", "", "", "", "", "", ""));
            model.addAttribute("jobTitle", "Not Available");
            model.addAttribute("navProfileImage", resumeService.getNavImageBase64());

            return "profile";
        }

        model.addAttribute("info", resumeJson.getPersonalInfo());
        model.addAttribute("jobTitle", resumeJson.getProfessionalDetails().getJobTitle());
        model.addAttribute("navProfileImage", resumeService.getNavImageBase64());

        return "profile";
    }

    @PostMapping("/profile/update")
    @ResponseBody
    public String updateProfileField(@RequestBody Map<String, String> payload) {

        String field = payload.get("field");
        String value = payload.get("value");

        ResumeJson resume = resumeService.getResume();
        if (resume == null) {
            resume = new ResumeJson();
            resume.setPersonalInfo(new PersonalInfo());
            resume.setProfessionalDetails(new ProfessionalDetails());
            resumeService.saveResume(resume);
        }

        PersonalInfo info = resume.getPersonalInfo();
        ProfessionalDetails details = resume.getProfessionalDetails();

        switch (field) {
            case "name": info.setName(value); break;
            case "jobTitle": details.setJobTitle(value); break;
            case "email": info.setEmail(value); break;
            case "contact": info.setContact(value); break;
            case "location": info.setLocation(value); break;
            case "about": info.setAbout(value); break;
            default: return "error";
        }
        return "ok";
    }


}
