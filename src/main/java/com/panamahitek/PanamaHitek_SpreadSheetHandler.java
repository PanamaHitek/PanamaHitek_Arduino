package com.panamahitek;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class PanamaHitek_SpreadSheetHandler {

    private final List<List<Object>> valuesMatrix = new ArrayList<>();
    private final List<Object> currentRowList = new ArrayList<>();
    private final List<SpreadsheetColumns> spreadsheetColumns = new ArrayList<>();
    private int rowCount = 0;
    private String spreadSheedTitle = "Hoja1";
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;

    public PanamaHitek_SpreadSheetHandler() {
        rowCount = 0;
    }

    public void createSpreadsheetColumn(int columnIndex, String columnHeaderTitle) {
        spreadsheetColumns.add(new SpreadsheetColumns(columnIndex, columnHeaderTitle));
        currentRowList.add(columnIndex, null);
    }

    public void printSpreadsheetRow() {
        List<Object> rowList = new ArrayList<>();
        currentRowList.forEach(i -> rowList.add(i));
        valuesMatrix.add(rowList);
        for (int i = 0; i < currentRowList.size(); i++) {
            currentRowList.set(i, null);
        }
    }

    public void setSpreadsheetCellValue(int columnIndex, Object value) {
        currentRowList.set(columnIndex, value);
    }

    public void setSpreadsheetCellValue(String columnHeaderTitle, Object value) {
        int columnIndex = spreadsheetColumns.stream().filter(
                e -> e.getColumnHeaderTitle().equals(columnHeaderTitle)
        ).findFirst().get().getIndex();
        currentRowList.set(columnIndex, value);
    }

    public void printSpreadsheet() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(spreadSheedTitle);
        Row headerRow = sheet.createRow(rowCount++);
        spreadsheetColumns.stream().forEach((spreadsheetColumn) -> {
            Cell cell = headerRow.createCell(spreadsheetColumn.getIndex());
            cell.setCellValue(spreadsheetColumn.getColumnHeaderTitle());
        });
        valuesMatrix.stream().forEach((valuesMatrix1) -> {
            Row row = sheet.createRow(rowCount++);
            for (int j = 0; j < valuesMatrix1.size(); j++) {
                Cell cell = row.createCell(j);
                if (valuesMatrix1.get(j) instanceof Integer) {
                    cell.setCellValue((Integer) valuesMatrix1.get(j));
                } else if (valuesMatrix1.get(j) instanceof Double) {
                    cell.setCellValue((Double) valuesMatrix1.get(j));
                } else if (valuesMatrix1.get(j) instanceof Float) {
                    cell.setCellValue((Float) valuesMatrix1.get(j));
                } else if (valuesMatrix1.get(j) instanceof String) {
                    cell.setCellValue((String) valuesMatrix1.get(j));
                } else if (valuesMatrix1.get(j) instanceof Boolean) {
                    cell.setCellValue((Boolean) valuesMatrix1.get(j));
                } else if (valuesMatrix1.get(j) instanceof Date) {
                    cell.setCellValue((Date) valuesMatrix1.get(j));
                }
            }
        });

    }

    public void createSpreadsheetFile() {
        try {
            FileOutputStream out
                    = new FileOutputStream(new File("spreadsheet.xls"));
            workbook.write(out);
            out.close();
            System.out.println("spreadsheet.xls written successfully...");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createSpreadsheetFile(String fileName) {
        try {

            if (!fileName.contains(".xls")) {
                throw new Exception("Nombre de archivo inválido. Debe poseer la extensión .xls");
            }
            FileOutputStream out
                    = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
            System.out.println(fileName + " written successfully...");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(PanamaHitek_SpreadSheetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getSpreadSheedTitle() {
        return spreadSheedTitle;
    }

    public void setSpreadSheedTitle(String spreadSheedTitle) {
        this.spreadSheedTitle = spreadSheedTitle;
    }

    private class SpreadsheetColumns {

        private int index;
        private String columnHeaderTitle;

        public SpreadsheetColumns(int index, String columnHeaderTitle) {
            this.index = index;
            this.columnHeaderTitle = columnHeaderTitle;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getColumnHeaderTitle() {
            return columnHeaderTitle;
        }

        public void setColumnHeaderTitle(String columnHeaderTitle) {
            this.columnHeaderTitle = columnHeaderTitle;
        }

    }
}
