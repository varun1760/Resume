package com.rao.Resume.controller;

import com.rao.Resume.model.ResumeJson;
import com.rao.Resume.service.ResumeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ResumeController {
    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping({"/resume/view"})
    public String displayResume(Model model, HttpSession session) {
        if (!this.resumeService.hasResume()) {
            return "redirect:/resume/options";
        } else {
            ResumeJson resumeJson = this.resumeService.getResume();
            model.addAttribute("info", resumeJson.getPersonalInfo());
            model.addAttribute("summary", resumeJson.getProfessionalDetails().getSummary());
            model.addAttribute("experiences", resumeJson.getProfessionalDetails().getExperiences());
            model.addAttribute("skills", resumeJson.getProfessionalDetails().getSkills());
            model.addAttribute("educationList", resumeJson.getProfessionalDetails().getEducationList());
            model.addAttribute("learningGoals", resumeJson.getProfessionalDetails().getLearningGoals());
            model.addAttribute("techSkills", resumeJson.getTechnicalSkills());
            String base64 = this.resumeService.getProfileImageBase64();
            String navBase64 = this.resumeService.getNavImageBase64();
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
    public void downloadDocx(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=resume.docx");
        this.resumeService.generateDocx(response);
    }

    @PostMapping({"/resume/upload-profile"})
    @ResponseBody
    public String uploadProfile(@RequestParam("file") MultipartFile file, HttpSession session) {
        try {
            this.resumeService.saveProfileImage(file);
            session.setAttribute("profileImage", this.resumeService.getNavImageBase64());
            return "ok";
        } catch (Exception var4) {
            return "error";
        }
    }

    @GetMapping({"/logout"})
    public String logout(HttpSession session) {
        this.resumeService.resetProfileImage();
        session.invalidate();
        return "redirect:/home";
    }
}
