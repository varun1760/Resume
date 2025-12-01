package com.rao.Resume.service;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.rao.Resume.model.ResumeJson;
import com.rao.Resume.utilities.helper.DocxBuilder;
import com.rao.Resume.utilities.helper.PdfBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.*;
import java.util.Base64;
import java.util.List;

@Service
public class ResumeService {
    private ResumeJson resumeJson;
    public byte[] profileImage;
    private static final long MAX_FILE_SIZE = 2097152L;
    private static final List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg", "image/jpg");
    private static final String UPLOAD_DIR = "uploads/profile/";
    private static final String PROFILE_FILE = "profile.png";

    public ResumeService() {
        File directory = new File("uploads/profile/");
        if (!directory.exists()) {
            directory.mkdirs();
        }

    }

    public void saveResume(ResumeJson json) {
        this.resumeJson = json;
    }

    public ResumeJson getResume() {
        return this.resumeJson;
    }

    public boolean hasResume() {
        return this.resumeJson != null;
    }

    public void generatePdfV3(HttpServletResponse response) throws Exception {
        ResumeJson resume = this.getResume();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=resume.pdf");
        Document doc = new Document(PageSize.A4, 36.0F, 36.0F, 36.0F, 36.0F);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();
        PdfBuilder.Options opt = new PdfBuilder.Options();
        opt.theme = PdfBuilder.Theme.BLUE;
        opt.enableQr = true;
        opt.darkMode = false;
        opt.profileImage = null;
        PdfBuilder.build(doc, resume, opt);
        doc.close();
    }

    public void generateDocx(HttpServletResponse response) {
        try {
            ResumeJson resume = this.getResume();
            byte[] profileImage = this.loadDefaultImage();
            XWPFDocument doc = new XWPFDocument();
            DocxBuilder.build(doc, resume, profileImage);
            doc.write(response.getOutputStream());
            doc.close();
            response.flushBuffer();
        } catch (Exception var6) {
            try {
                response.setStatus(500);
            } catch (Exception var5) {
            }
        }

    }

    public void saveProfileImage(MultipartFile file) throws Exception {
        if (file != null && !file.isEmpty()) {
            if (file.getSize() > 2097152L) {
                throw new Exception("File too large. Max allowed is 2MB.");
            } else if (!ALLOWED_TYPES.contains(file.getContentType())) {
                throw new Exception("Invalid file type. Only PNG/JPG allowed.");
            } else {
                Files.createDirectories(Paths.get("uploads/profile/"));
                Path savedPath = Paths.get("uploads/profile/profile.png");
                Files.write(savedPath, file.getBytes(), new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING});
                this.profileImage = file.getBytes();
            }
        } else {
            throw new Exception("File is empty");
        }
    }

    public byte[] loadProfileImage() {
        try {
            Path path = Paths.get("uploads/profile/profile.png");
            return Files.exists(path, new LinkOption[0]) ? Files.readAllBytes(path) : this.loadDefaultImage();
        } catch (Exception var2) {
            return this.loadDefaultImage();
        }
    }

    public byte[] loadDefaultImage() {
        try {
            Path defaultPath = Paths.get("src/main/resources/static/assets/images/profile1.png");
            return Files.readAllBytes(defaultPath);
        } catch (Exception var2) {
            return new byte[0];
        }
    }

    public String getProfileImageBase64() {
        try {
            byte[] img = this.loadProfileImage();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(img);
        } catch (Exception var2) {
            return null;
        }
    }

    public String getNavImageBase64() {
        try {
            return this.getProfileImageBase64();
        } catch (Exception var2) {
            return null;
        }
    }

    public void resetProfileImage() {
        try {
            Path path = Paths.get("uploads/profile/profile.png");
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (Exception var2) {
        }

    }
}
