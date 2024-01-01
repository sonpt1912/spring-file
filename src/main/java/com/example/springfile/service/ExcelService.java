package com.example.springfile.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface ExcelService {

    Object importFile(MultipartFile file) throws IOException, InvalidFormatException;

    ByteArrayInputStream exportFile() throws IOException;

}
