package com.example.springfile.service.impl;

import com.example.springfile.service.PdfServiec;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
public class PdfServiceImpl implements PdfServiec {

    @Override
    public Object export() {
        Document document = new Document();
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, pdfStream);
            document.open();

            // Add content to the PDF
            document.add(new Paragraph("Hello, this is a sample PDF!"));

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return new ByteArrayInputStream(pdfStream.toByteArray());
    }
}
