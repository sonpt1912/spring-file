package com.example.springfile.service.impl;

import com.example.springfile.service.ExcelService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public Object importFile(MultipartFile multipartFile) throws IOException, InvalidFormatException {


        List<Object> list = new ArrayList<>();

        File file = this.convertMultiPartToFile(multipartFile);

        // create workbook
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        // get sheet from excel
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                // check cell type and format

                if (cell.getCellType().equals(CellType.NUMERIC)) {
                    list.add(cell.getNumericCellValue());
                } else if (cell.getCellType().equals(CellType.STRING)) {
                    list.add(cell.getStringCellValue());
                } else {
                    throw new IOException();
                }
            }
        }

        return list;

    }

    public ByteArrayInputStream exportFile() throws IOException {

        // create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // create blank sheet
        XSSFSheet xssfSheet = workbook.createSheet("demo-excel");
        ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();

        // creating an empty treeMap of String and Object[]
        Map<String, Object> data = new TreeMap<>();

        // writing data to object[]
        data.put("1", new Object[]{"STT", "Full name", "Age", "Gender"});
        data.put("2", new Object[]{1, "pham thanh son", 19, "Nam"});
        data.put("3", new Object[]{2, "nguyen thi minh hang", 32, "Nu"});

        Set<String> keySet = data.keySet();

        int rowNumber = 0;

        for (String key : keySet) {
            // create a new row in sheet
            Row row = xssfSheet.createRow(rowNumber++);

            Object[] objects = (Object[]) data.get(key);

            int cellColumn = 0;

            for (Object object : objects) {

                Cell cell = row.createCell(cellColumn++);

                if (object instanceof String) {
                    cell.setCellValue((String) object);
                } else if (object instanceof Integer) {
                    cell.setCellValue((Integer) object);
                }

            }
            // writing the workBook
            workbook.write(fileOutputStream);
        }

        return new ByteArrayInputStream((fileOutputStream.toByteArray()));
    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


}
