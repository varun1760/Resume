package com.rao.Resume.utilities;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.ByteArrayInputStream;
import java.util.List;

public class DocxUtil {
    public static void heading(XWPFDocument doc, String title, String icon, int fontSize) {
        para(doc, (icon != null ? icon + " " : "") + title.toUpperCase(), fontSize, true);
    }

    public static void para(XWPFDocument doc, String text, int fontSize, boolean bold) {
        if (text != null && !text.isBlank()) {
            XWPFParagraph p = doc.createParagraph();
            XWPFRun run = p.createRun();
            run.setText(text);
            run.setFontSize(fontSize);
            run.setBold(bold);
        }
    }

    public static void bulletList(XWPFDocument doc, List<String> items, int fontSize) {
        if (items != null && !items.isEmpty()) {
            for(String item : items) {
                XWPFParagraph p = doc.createParagraph();
                p.setStyle("ListBullet");
                XWPFRun run = p.createRun();
                run.setText(item);
                run.setFontSize(fontSize);
            }

        }
    }

    public static void addImage(XWPFDocument doc, byte[] imageBytes, int width, int height) throws Exception {
        if (imageBytes != null) {
            XWPFParagraph p = doc.createParagraph();
            XWPFRun run = p.createRun();
            run.addPicture(new ByteArrayInputStream(imageBytes), XWPFDocument.PICTURE_TYPE_PNG, "profile.png", Units.toEMU((double)width), Units.toEMU((double)height));
        }
    }
}
