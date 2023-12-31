package com.example.springfile.controller;

import com.example.springfile.service.PdfServiec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfServiec pdfServiec;

    @PostMapping("/export")
    public ResponseEntity export() {
        return new ResponseEntity(pdfServiec.export(), HttpStatus.OK);
    }

}
