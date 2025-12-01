package com.rao.Resume.utilities.helper;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.rao.Resume.model.*;
import com.rao.Resume.utilities.PdfUtil;

import java.awt.*;
import java.util.List;

public class PdfBuilder {
    private static Color primary(Theme theme) {
        Color var10000;
        switch (theme.ordinal()) {
            case 1 -> var10000 = new Color(120, 70, 170);
            case 2 -> var10000 = new Color(0, 130, 130);
            case 3 -> var10000 = new Color(34, 34, 34);
            default -> var10000 = new Color(45, 95, 175);
        }

        return var10000;
    }

    public static void build(Document doc, ResumeJson resume, Options opt) {
        Color primary = primary(opt.theme);
        Color text = opt.darkMode ? new Color(230, 230, 230) : new Color(40, 40, 40);
        Font nameFont = PdfUtil.font(26.0F, 1, primary);
        Font sectionFont = PdfUtil.font(13.0F, 1, text);
        Font normal = PdfUtil.font(10.0F, 0, text);
        PdfPTable layout = new PdfPTable(new float[]{0.32F, 0.68F});
        layout.setWidthPercentage(100.0F);
        layout.setKeepTogether(true);
        PdfPTable left = new PdfPTable(1);
        left.setWidthPercentage(100.0F);
        if (opt.profileImage != null) {
            left.addCell(PdfUtil.section("Profile Image", "\ud83d\uddbc", sectionFont, primary));

            try {
                PdfPCell imgCell = PdfUtil.imageCell(opt.profileImage, 100.0F, 100.0F);
                if (imgCell != null) {
                    left.addCell(imgCell);
                }
            } catch (Exception var20) {
            }

            left.addCell(PdfUtil.spacerCell(10.0F));
        }

        PersonalInfo pi = resume.getPersonalInfo();
        left.addCell(PdfUtil.section("Contact", "✉", sectionFont, primary));
        if (pi != null) {
            if (pi.getEmail() != null) {
                left.addCell(PdfUtil.padded(new Phrase(pi.getEmail(), normal)));
            }

            if (pi.getContact() != null) {
                left.addCell(PdfUtil.padded(new Phrase(pi.getContact(), normal)));
            }

            if (pi.getGithub() != null) {
                left.addCell(PdfUtil.padded(new Phrase(pi.getGithub(), normal)));
            }

            if (pi.getLinkedin() != null) {
                left.addCell(PdfUtil.padded(new Phrase(pi.getLinkedin(), normal)));
            }
        } else {
            left.addCell(PdfUtil.padded(new Phrase("No contact info", normal)));
        }

        left.addCell(PdfUtil.spacerCell(6.0F));
        if (opt.enableQr && pi != null && pi.getLinkedin() != null) {
            try {
                Image qr = PdfUtil.qrCode(pi.getLinkedin(), 200);
                if (qr != null) {
                    left.addCell(PdfUtil.section("Profile QR", "▣", sectionFont, primary));
                    left.addCell(PdfUtil.padded(qr));
                }
            } catch (Exception var19) {
            }

            left.addCell(PdfUtil.spacerCell(10.0F));
        }

        left.addCell(PdfUtil.section("Skills", "★", sectionFont, primary));
        List<String> skills = resume.getProfessionalDetails() != null ? resume.getProfessionalDetails().getSkills() : null;
        if (skills != null && !skills.isEmpty()) {
            left.addCell(PdfUtil.padded(PdfUtil.bulletList(skills, normal)));
        } else {
            left.addCell(PdfUtil.padded(new Phrase("No skills listed", normal)));
        }

        left.addCell(PdfUtil.spacerCell(10.0F));
        left.addCell(PdfUtil.section("Learning Goals", "⚑", sectionFont, primary));
        List<String> goals = resume.getProfessionalDetails() != null ? resume.getProfessionalDetails().getLearningGoals() : null;
        if (goals != null && !goals.isEmpty()) {
            left.addCell(PdfUtil.padded(PdfUtil.bulletList(goals, normal)));
        } else {
            left.addCell(PdfUtil.padded(new Phrase("No learning goals listed", normal)));
        }

        left.addCell(PdfUtil.spacerCell(10.0F));
        PdfPTable right = new PdfPTable(1);
        right.setWidthPercentage(100.0F);
        if (pi != null) {
            if (pi.getName() != null) {
                right.addCell(PdfUtil.padded(PdfUtil.para(pi.getName(), nameFont)));
            }

            if (pi.getLocation() != null) {
                right.addCell(PdfUtil.padded(PdfUtil.para(pi.getLocation(), normal)));
            }
        }

        right.addCell(PdfUtil.spacerCell(6.0F));
        ProfessionalDetails prof = resume.getProfessionalDetails();
        right.addCell(PdfUtil.padded(PdfUtil.section("Summary", "✦", sectionFont, primary)));
        if (prof != null && prof.getSummary() != null) {
            right.addCell(PdfUtil.padded(new Phrase(prof.getSummary(), normal)));
        } else {
            right.addCell(PdfUtil.padded(new Phrase("No summary available", normal)));
        }

        right.addCell(PdfUtil.padded(PdfUtil.section("Experience", "⦿", sectionFont, primary)));
        if (prof != null && prof.getExperiences() != null) {
            for(Experience exp : prof.getExperiences()) {
                right.addCell(PdfUtil.padded(new Phrase(exp.getRole() + " — " + exp.getCompany(), sectionFont)));
                right.addCell(PdfUtil.padded(new Phrase(exp.getDuration(), normal)));
                if (exp.getTechStack() != null && !exp.getTechStack().isEmpty()) {
                    right.addCell(PdfUtil.padded(PdfUtil.bulletList(exp.getTechStack(), normal)));
                }

                if (exp.getProjects() != null) {
                    for(Project p : exp.getProjects()) {
                        right.addCell(PdfUtil.padded(new Phrase("Project: " + p.getName(), sectionFont)));
                        if (p.getResponsibilities() != null) {
                            right.addCell(PdfUtil.padded(PdfUtil.bulletList(p.getResponsibilities(), normal)));
                        }

                        if (p.getAchievements() != null) {
                            right.addCell(PdfUtil.padded(PdfUtil.bulletList(p.getAchievements(), normal)));
                        }
                    }
                }
            }
        } else {
            right.addCell(PdfUtil.padded(new Phrase("No experience listed", normal)));
        }

        right.addCell(PdfUtil.padded(PdfUtil.section("Education", "\ud83c\udf93", sectionFont, primary)));
        if (prof != null && prof.getEducationList() != null) {
            for(Education ed : prof.getEducationList()) {
                right.addCell(PdfUtil.padded(new Phrase(ed.getDegree() + " — " + ed.getInstitute(), sectionFont)));
                right.addCell(PdfUtil.padded(new Phrase(ed.getLocation() + " | " + ed.getYear(), normal)));
            }
        } else {
            right.addCell(PdfUtil.padded(new Phrase("No education listed", normal)));
        }

        PdfPCell leftCell = new PdfPCell(left);
        leftCell.setBorder(0);
        leftCell.setPaddingRight(12.0F);
        leftCell.setNoWrap(false);
        layout.addCell(leftCell);
        PdfPCell rightCell = new PdfPCell(right);
        rightCell.setBorder(0);
        rightCell.setPaddingLeft(12.0F);
        rightCell.setNoWrap(false);
        layout.addCell(rightCell);
        doc.add(layout);
    }

    public static enum Theme {
        BLUE,
        PURPLE,
        TEAL,
        DARK;
    }

    public static class Options {
        public Theme theme;
        public boolean darkMode;
        public boolean enableQr;
        public byte[] profileImage;

        public Options() {
            this.theme = PdfBuilder.Theme.BLUE;
            this.darkMode = false;
            this.enableQr = true;
            this.profileImage = null;
        }
    }
}
