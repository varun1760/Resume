package com.rao.Resume.utilities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfUtil {
    public static Font font(float size, int style, Color color) {
        return new Font(1, size, style, color);
    }

    public static Paragraph para(String text, Font font) {
        return new Paragraph(text, font);
    }

    public static PdfPCell padded(Element e) {
        PdfPCell c = new PdfPCell();
        c.setBorder(0);
        c.setPadding(6.0F);
        c.addElement(e);
        return c;
    }

    public static com.lowagie.text.List bulletList(List<String> items, Font font) {
        com.lowagie.text.List list = new com.lowagie.text.List(false);
        list.setListSymbol("• ");
        list.setSymbolIndent(8.0F);
        if (items != null) {
            for(String s : items) {
                list.add(new ListItem(s, font));
            }
        }

        return list;
    }

    public static PdfPTable section(String title, String icon, Font font, Color color) {
        PdfPTable t = new PdfPTable(new float[]{0.12F, 0.88F});
        t.setWidthPercentage(100.0F);
        PdfPCell ic = new PdfPCell(new Phrase(icon, font(12.0F, 1, color)));
        ic.setBorder(0);
        t.addCell(ic);
        PdfPCell txt = new PdfPCell(new Phrase(title.toUpperCase(), font));
        txt.setBorder(0);
        t.addCell(txt);
        return t;
    }

    public static Paragraph spacer(float height) {
        Paragraph p = new Paragraph(" ");
        p.setSpacingBefore(height);
        p.setSpacingAfter(height);
        return p;
    }

    public static PdfPCell spacerCell(float height) {
        PdfPCell cell = new PdfPCell(new Phrase(" "));
        cell.setFixedHeight(height);
        cell.setBorder(0);
        return cell;
    }

    public static PdfPCell imageCell(byte[] imageBytes, float width, float height) throws Exception {
        if (imageBytes != null && imageBytes.length != 0) {
            Image img = Image.getInstance(imageBytes);
            img.scaleAbsolute(width, height);
            PdfPCell cell = new PdfPCell(img);
            cell.setBorder(0);
            cell.setPadding(6.0F);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(5);
            return cell;
        } else {
            return null;
        }
    }

    public static Image qrCode(String text, int size) throws Exception {
        if (text != null && !text.isBlank()) {
            BitMatrix matrix = (new MultiFormatWriter()).encode(text, BarcodeFormat.QR_CODE, size, size);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "png", baos);
            Image img = Image.getInstance(baos.toByteArray());
            img.scaleAbsolute(80.0F, 80.0F);
            return img;
        } else {
            return null;
        }
    }
}
