package com.rao.Resume.utilities.helper;

import com.rao.Resume.model.*;
import com.rao.Resume.utilities.DocxUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class DocxBuilder {
    public static void build(XWPFDocument doc, ResumeJson resume, byte[] profileImage) throws Exception {
        if (doc != null && resume != null) {
            PersonalInfo pi = resume.getPersonalInfo();
            ProfessionalDetails prof = resume.getProfessionalDetails();
            if (pi != null) {
                if (pi.getName() != null) {
                    DocxUtil.para(doc, pi.getName(), 26, true);
                }

                if (pi.getLocation() != null) {
                    DocxUtil.para(doc, pi.getLocation(), 12, false);
                }
            }

            if (profileImage != null) {
                DocxUtil.addImage(doc, profileImage, 100, 100);
            }

            DocxUtil.heading(doc, "Contact", "✉", 14);
            if (pi != null) {
                if (pi.getEmail() != null) {
                    DocxUtil.para(doc, "Email: " + pi.getEmail(), 12, false);
                }

                if (pi.getContact() != null) {
                    DocxUtil.para(doc, "Phone: " + pi.getContact(), 12, false);
                }

                if (pi.getGithub() != null) {
                    DocxUtil.para(doc, "Github: " + pi.getGithub(), 12, false);
                }

                if (pi.getLinkedin() != null) {
                    DocxUtil.para(doc, "LinkedIn: " + pi.getLinkedin(), 12, false);
                }
            }

            DocxUtil.heading(doc, "Skills", "★", 14);
            DocxUtil.bulletList(doc, prof != null ? prof.getSkills() : null, 12);
            DocxUtil.heading(doc, "Learning Goals", "⚑", 14);
            DocxUtil.bulletList(doc, prof != null ? prof.getLearningGoals() : null, 12);
            DocxUtil.heading(doc, "Summary", "✦", 14);
            if (prof != null && prof.getSummary() != null) {
                DocxUtil.para(doc, prof.getSummary(), 12, false);
            }

            DocxUtil.heading(doc, "Experience", "⦿", 14);
            if (prof != null && prof.getExperiences() != null) {
                for(Experience exp : prof.getExperiences()) {
                    DocxUtil.para(doc, exp.getRole() + " — " + exp.getCompany(), 12, true);
                    DocxUtil.para(doc, exp.getDuration(), 12, false);
                    DocxUtil.bulletList(doc, exp.getTechStack(), 12);
                    if (exp.getProjects() != null) {
                        for(Project p : exp.getProjects()) {
                            DocxUtil.para(doc, "Project: " + p.getName(), 12, true);
                            DocxUtil.bulletList(doc, p.getResponsibilities(), 12);
                            DocxUtil.bulletList(doc, p.getAchievements(), 12);
                        }
                    }
                }
            }

            DocxUtil.heading(doc, "Education", "\ud83c\udf93", 14);
            if (prof != null && prof.getEducationList() != null) {
                for(Education ed : prof.getEducationList()) {
                    DocxUtil.para(doc, ed.getDegree() + " — " + ed.getInstitute(), 12, true);
                    DocxUtil.para(doc, ed.getLocation() + " | " + ed.getYear(), 12, false);
                }
            }

        }
    }
}
