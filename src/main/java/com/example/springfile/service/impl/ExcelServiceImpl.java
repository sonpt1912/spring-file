package com.example.springfile.service.impl;

import com.example.springfile.service.ExcelService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public Object getDataFromFile(MultipartFile multipartFile) throws IOException, InvalidFormatException {


        List<Object> list = new ArrayList<>();

        File file = this.convertMultiPartToFile(multipartFile);

        // create workbook
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        // get sheet from excel
        XSSFSheet sheet = workbook.getSheetAt(0);

        //
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                // check cell type and format

                if(cell.getCellType().equals(CellType.NUMERIC)){
                    list.add(cell.getNumericCellValue());
                }else if(cell.getCellType().equals(CellType.STRING)){
                    list.add(cell.getStringCellValue());
                }else{
                    throw new IOException();
                }
            }
        }

        return list;

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


}
