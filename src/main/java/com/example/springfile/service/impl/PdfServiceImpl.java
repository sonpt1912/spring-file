package com.example.springfile.service.impl;

import com.example.springfile.service.PdfServiec;
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

import java.io.File;

@Service
public class PdfServiceImpl implements PdfServiec {

    @Override
    public Object export() {
        // tạo mới một đối tượng PD document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();

        // tạo một trang mới
        document.addPage(page);
        File tempFile = null;
        try {
            // tao một đói tượng PDPageContentStream để viết nội dung vào trang
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // thêm văn bản vào trang
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 22);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700); // vij trí bắt đầu của văn bản
            contentStream.showText("hello, this is pham son who sleeping ");
            contentStream.endText();

            // kết thúc và đóng tài liệu
            contentStream.close();
            tempFile = File.createTempFile("hello", ".pdf");
            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=hello.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(new FileSystemResource(tempFile));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }


    }
}
