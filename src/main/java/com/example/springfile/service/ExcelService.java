package com.example.springfile.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelService {

    Object getDataFromFile(MultipartFile file) throws IOException, InvalidFormatException;

}
