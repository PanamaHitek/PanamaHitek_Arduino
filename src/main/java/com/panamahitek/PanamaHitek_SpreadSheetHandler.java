package com.panamahitek;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
/**
 * Clase especialmente diseñada para el almacenamiento de información y su
 * posterior exportación a hojas de cálculo en formato MS Excel (.xls)
 *
 * @author Antony
 */
public class PanamaHitek_SpreadSheetHandler {

    private final List<List<Object>> valuesMatrix = new ArrayList<>();
    private final List<Object> currentRowList = new ArrayList<>();
    private final List<SpreadsheetColumns> spreadsheetColumns = new ArrayList<>();
    private int rowCount = 0;
    private String spreadSheedTitle = "Hoja1";
    private HSSFWorkbook workbook = null;
    private HSSFSheet sheet = null;

    /**
     * Constructor de la clase
     *
     * @since 2.8.3
     */
    public PanamaHitek_SpreadSheetHandler() {
        rowCount = 0;
    }

    /**
     * Método utilizado para crear las columnas que formarán la hoja de cálculo.
     * Cuantas veces este método sea utilizado, así mismo se agregará columnas a
     * la hoja de cálculo
     *
     * @param columnIndex Número de la columna, de izquierda a derecha
     * @param columnHeaderTitle Título de la columna. Nombre que aparecerá en la
     * cabecera
     * @since 2.8.3
     */
    public void createSpreadsheetColumn(int columnIndex, String columnHeaderTitle) {
        spreadsheetColumns.add(new SpreadsheetColumns(columnIndex, columnHeaderTitle));
        currentRowList.add(columnIndex, null);
    }

    /**
     * Se utiliza para setear los valores de una fila, una vez que todos los
     * valores de cada una de las columnas hayan sido seteado. Al utilizar este
     * método provocaremos un salto en la fila sobre la cual se están
     * imprimiendo valores
     *
     * @since 2.8.3
     */
    public void printSpreadsheetRow() {
        List<Object> rowList = new ArrayList<>();
        currentRowList.forEach(i -> rowList.add(i));
        valuesMatrix.add(rowList);
        for (int i = 0; i < currentRowList.size(); i++) {
            currentRowList.set(i, null);
        }
    }

    /**
     * Establece el valor instantáneo de una columna
     *
     * @param columnIndex Índice de la columna en la cual se desea imprimit
     * @param value Valor que se desea almacenar en la columna
     *
     * @see printSpreadsheetRow()
     * @since 2.8.3
     */
    public void setSpreadsheetCellValue(int columnIndex, Object value) {
        currentRowList.set(columnIndex, value);
    }

    /**
     * Establece el valor instantáneo de una columna
     *
     * @param columnHeaderTitle Título de la columna en la que se desea imprimir
     * un valor
     * @param value Valor que se desea almacenar en la columna
     *
     * @see printSpreadsheetRow()
     * @since 2.8.3
     */
    public void setSpreadsheetCellValue(String columnHeaderTitle, Object value) {
        int columnIndex = spreadsheetColumns.stream().filter(
                e -> e.getColumnHeaderTitle().equals(columnHeaderTitle)
        ).findFirst().get().getIndex();
        currentRowList.set(columnIndex, value);
    }

    /**
     * Se crea una hoja de cálculo con los valores que hayan sido almacenados
     * hasta este momento
     *
     * @since 2.8.3
     */
    private void printSpreadsheet() {
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

    /**
     * Se crea un archivo de MS Excel en formato .xls que tendrá como nombre
     * spreadsheet.xls. El archivo será guardado en la raíz de la carpeta en la
     * cual se encuentre el archivo jar con el código de programación en Java.
     *
     * @throws Exception Pueden producirse excepciones a la hora de escribir el
     * fichero
     *
     * @since 2.8.3
     */
    public void createSpreadsheetFile() throws Exception {
        printSpreadsheet();
        FileOutputStream out
                = new FileOutputStream(new File("spreadsheet.xls"));
        workbook.write(out);
        out.close();
        System.out.println("spreadsheet.xls written successfully...");
    }

    /**
     * Se crea un archivo de MS Excel en formato .xls que tendrá como nombre
     * spreadsheet.xls. El archivo será guardado en la ruta especificada con el
     * nombre especificado
     *
     * @param fileName En este parámetro se establece el nombre y la ruta en la
     * cual se escribirá el fichero con los datos almacenados.
     * @throws Exception Pueden producirse excepciones a la hora de escribir el
     * fichero
     * @since 2.8.3
     */
    public void createSpreadsheetFile(String fileName) throws Exception {
        printSpreadsheet();
        if (!fileName.contains(".xls")) {
            throw new Exception("Nombre de archivo inválido. Debe poseer la extensión .xls");
        }
        FileOutputStream out
                = new FileOutputStream(new File(fileName));
        workbook.write(out);
        out.close();
        System.out.println(fileName + " written successfully...");
    }

    /**
     * @return Título de la hoja de cálculo
     * @since 2.8.3
     */
    public String getSpreadSheedTitle() {
        return spreadSheedTitle;
    }

    /**
     *
     * @param spreadSheedTitle Se establece un título para la hoja de cálculo
     * @since 2.8.3
     */
    public void setSpreadSheedTitle(String spreadSheedTitle) {
        this.spreadSheedTitle = spreadSheedTitle;
    }

    /**
     * Clase para la creación de objetos representativos de columnas
     *
     * @since 2.8.3
     */
    private class SpreadsheetColumns {

        private int index;
        private String columnHeaderTitle;

        /**
         *
         * @param index
         * @param columnHeaderTitle
         */
        public SpreadsheetColumns(int index, String columnHeaderTitle) {
            this.index = index;
            this.columnHeaderTitle = columnHeaderTitle;
        }

        /**
         *
         * @return
         */
        public int getIndex() {
            return index;
        }

        /**
         *
         * @param index
         */
        public void setIndex(int index) {
            this.index = index;
        }

        /**
         *
         * @return
         */
        public String getColumnHeaderTitle() {
            return columnHeaderTitle;
        }

        /**
         *
         * @param columnHeaderTitle
         */
        public void setColumnHeaderTitle(String columnHeaderTitle) {
            this.columnHeaderTitle = columnHeaderTitle;
        }

    }
}
