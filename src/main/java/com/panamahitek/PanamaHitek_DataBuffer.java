/**
 * Este código ha sido construido por Antony García González y el Equipo
 * Creativo de Panama Hitek.
 *
 * Está protegido bajo la licencia LGPL v 2.1, cuya copia se puede encontrar en
 * el siguiente enlace: http://www.gnu.org/licenses/lgpl.txt
 *
 * Para su funcionamiento utiliza el código de la librería JSSC (anteriormente
 * RXTX) que ha permanecido intacto sin modificación alguna de parte de nuestro
 * equipo creativo. Agradecemos al creador de la librería JSSC, Alexey Sokolov
 * por esta herramienta tan poderosa y eficaz que ha hecho posible el
 * mejoramiento de nuestra librería.
 *
 * Esta librería es de código abierto y ha sido diseñada para que los usuarios,
 * desde principiantes hasta expertos puedan contar con las herramientas
 * apropiadas para el desarrollo de sus proyectos, de una forma sencilla y
 * agradable.
 *
 * Se espera que se en cualquier uso de este código se reconozca su procedencia.
 * Este algoritmo fue diseñado en la República de Panamá por Antony García
 * Gónzález, estudiante de la Universidad de Panamá en la carrera de
 * Licenciatura en Ingeniería Electromecánica, desde el año 2013 hasta el
 * presente. Su diseñador forma parte del Equipo Creativo de Panama Hitek, una
 * organización sin fines de lucro dedicada a la enseñanza del desarrollo de
 * software y hardware a través de su sitio web oficial http://panamahitek.com
 *
 * Solamente deseamos que se reconozca esta compilación de código como un
 * trabajo hecho por panameños para Panamá y el mundo.
 *
 * Si desea contactarnos escríbanos a creativeteam@panamahitek.com
 */
package com.panamahitek;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.panamahitek.events.DataInsertionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.panamahitek.events.DataInsertionListener;
import java.awt.Component;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

/**
 * Esta clase ha sido diseñada para gestionar el almacenamiento de datos en
 * hojas de cálculo de Excel de manera fácil y rápida
 *
 * @author Antony García González, de Proyecto Panama Hitek. Visita
 * http://panamahitek.com
 */
public class PanamaHitek_DataBuffer {

    private List<List<Object>> mainBuffer;
    private List<String> variableList;
    private List<Object> classList;
    private int ROW_COUNT = 0;
    private String SHEET_NAME = "Arduino_log";
    private JTable table;
    private JScrollPane scroll;
    private boolean tableFlag = false;

    private boolean listenerFlag = false;
    private boolean timeFlag = false;
    private int timeColumn = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private String dateStringFormat = "HH:mm:ss";
    private ArrayList listeners;

    TableModelListener tableModelListener = new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent tme) {

            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> {
                try {
                    Thread.sleep(10);
                    table.repaint();
                    table.getTableHeader().repaint();
                    table.scrollRectToVisible(table.getCellRect(table.getRowCount(), 0, true));
                    table.repaint();
                    table.getTableHeader().repaint();
                    executor.shutdown();
                    executor.awaitTermination(100, TimeUnit.MILLISECONDS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PanamaHitek_DataBuffer.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    };

    /**
     * Constructor de la clase
     */
    public PanamaHitek_DataBuffer() {
        mainBuffer = new ArrayList<>();
        variableList = new ArrayList<>();
        classList = new ArrayList<>();
        listeners = new ArrayList();
    }

    /**
     * Permite agregar una columna al buffer de datos
     *
     * @param index El índice (posicion) de la columna
     * @param variableName El nombre que se desea asignar a la variable
     * @param dataType Tipo de datos a guardar en la columna
     */
    public void addColumn(int index, String variableName, Object dataType) {
        variableList.add(index, variableName);
        classList.add(dataType);
        List<Object> list = new ArrayList<>();
        mainBuffer.add(list);
    }

    /**
     * Permite agregar una columna de registro de tiempo al buffer de datos
     *
     * @param index El índice (posicion) de la columna de tiempo
     * @param variableName El nombre que se desea asignar a la variable
     */
    public void addTimeColumn(int index, String variableName) {
        variableList.add(index, variableName);
        classList.add(new Date());
        List<Object> list = new ArrayList<>();
        this.timeColumn = index;
        this.timeFlag = true;
        mainBuffer.add(list);
    }

    /**
     * Permite establecer el formato de la fecha. Por ejemplo, si se quiere que
     * la fecha tenga el formato de hora, minutos y segundos, se debe enviar
     * como parámetro new SimpleDateFormat("HH:mm:ss")
     *
     * @param format Formato que se desea establecer
     *
     * @see SimpleDateFormat
     */
    public void setDateFormat(String format) {
        this.dateFormat = new SimpleDateFormat(format);
        dateStringFormat = format;
    }

    /**
     * Permite establecer el formato de la fecha. Por ejemplo, si se quiere que
     * la fecha tenga el formato de hora, minutos y segundos, se debe enviar
     * como parámetro new SimpleDateFormat("HH:mm:ss")
     *
     * @param format Formato que se desea establecer
     *
     * @see SimpleDateFormat
     */
    public void setDateFormat(SimpleDateFormat format) {
        this.dateFormat = format;
    }

    /*
     * Provoca un salto de línea en el buffer de datos. Se utiliza cuando se 
     * haya terminado de guardar la información para un instante dado
     */
    public void printRow() {

        if (timeFlag) {
            try {
                mainBuffer.get(timeColumn).add(ROW_COUNT, dateFormat.parse(dateFormat.format(new Date())));
            } catch (ParseException ex) {
                Logger.getLogger(PanamaHitek_DataBuffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ROW_COUNT++;
        for (int i = 0; i < mainBuffer.size(); i++) {
            if (mainBuffer.get(i).size() != ROW_COUNT) {
                while (mainBuffer.get(i).size() != ROW_COUNT) {
                    Object columnValue = classList.get(i);
                    if ((columnValue instanceof String) || (columnValue.equals(String.class))) {
                        mainBuffer.get(i).add("");
                    } else if ((columnValue instanceof Boolean) || (columnValue.equals(Boolean.class))) {
                        mainBuffer.get(i).add(null);
                    } else if ((columnValue instanceof Date) || (columnValue.equals(Date.class))) {
                        mainBuffer.get(i).add(dateFormat.format(new Date()));
                    } else if ((columnValue instanceof Integer) || (columnValue.equals(Integer.class))) {
                        mainBuffer.get(i).add(0);
                    } else if ((columnValue instanceof Long) || (columnValue.equals(Long.class))) {
                        mainBuffer.get(i).add(0);
                    } else if ((columnValue instanceof Float) || (columnValue.equals(Float.class))) {
                        mainBuffer.get(i).add(0);
                    } else if ((columnValue instanceof Double) || (columnValue.equals(Double.class))) {
                        mainBuffer.get(i).add(0);
                    }
                }
            }
        }

        if (tableFlag) {
            Object[] row = new Object[variableList.size()];
            for (int i = 0; i < variableList.size(); i++) {
                row[i] = mainBuffer.get(i).get(ROW_COUNT - 1);
            }
            ((DefaultTableModel) table.getModel()).addRow(row);
        }

        if (listenerFlag) {
            triggerDataInsertionEvent();
        }

    }

    /**
     * Agrega nuevos valores al buffer de datos
     *
     * @param column Columna en la que se desea almacenar el valor
     * @param value Valor a almacenar
     * @throws Exception Se produce si se escoje una columna que no ha sido
     * definida o si se intenta guardar un valor diferente al del tipo de datos
     * establecidos para la columna
     */
    public void addValue(int column, Object value) throws Exception {
        if (column > variableList.size()) {
            throw new Exception("El parametro 'column' no puede ser mayor a la "
                    + "cantidad de columnas declaradas");
        }
        mainBuffer.get(column).add(ROW_COUNT, value);
    }

    /**
     *
     * @return Cantidad de filas almacenadas en el buffer
     */
    public int getRowCount() {
        return ROW_COUNT;
    }

    /**
     *
     * @return Cantidad de columnas del buffer
     */
    public int getColumnCount() {
        return variableList.size();
    }

    /**
     *
     * @param sheetName Nombre de la hoja en el libro de Excel
     */
    public void setSheetName(String sheetName) {
        this.SHEET_NAME = sheetName;
    }

    /**
     *
     * @return Nombre de la hoja de Excel
     */
    public String getSheetName() {
        return SHEET_NAME;
    }

    /**
     * Abre una ventana emergente para escoger la direccion en la cual se quiere
     * almacenar la hoja de datos de Excel
     */
    public void exportExcelFile() throws FileNotFoundException, IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        jfc.addChoosableFileFilter(new FileNameExtensionFilter("*.xlsx", "xlsx"));
        jfc.addChoosableFileFilter(new FileNameExtensionFilter("*.json", "JSON"));
        jfc.setDialogTitle("Especifique la ruta en la que desea guardar el archivo");
        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (jfc.getFileFilter().getDescription().equals("*.json")) {
                File selectedFile = jfc.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                if (!path.endsWith(".json")) {
                    path += ".json";
                }
                BufferedWriter output = null;
                File file = new File(path);
                output = new BufferedWriter(new FileWriter(file));
                output.write(buildJSON());
                output.close();

            } else {
                File selectedFile = jfc.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                if (!path.endsWith(".xlsx")) {
                    path += ".xlsx";
                }
                FileOutputStream outputStream = new FileOutputStream(path);
                XSSFWorkbook workbook = buildSpreadsheet();
                workbook.write(outputStream);
                workbook.close();
            }

        }

    }

    /**
     * Crea y almacena la hoja de Excel con los datos del buffer en una
     * ubicacion específica
     *
     * @param path Ubicacion en la que se desea almacenar el fichero
     */
    public void exportExcelFile(String path) {
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            XSSFWorkbook workbook = buildSpreadsheet();
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Construye la hoja de Excel
     *
     * @return Instancia de la clase XSSFWorkbook con los datos almacenados en
     * el buffer de datos
     */
    private XSSFWorkbook buildSpreadsheet() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        System.out.println("Building spreadsheet...");
        System.out.println("Buffer size: " + mainBuffer.size());
        for (int i = 0; i <= mainBuffer.get(0).size(); i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < variableList.size(); j++) {
                Cell cell = row.createCell(j);
                if (i == 0) {
                    cell.setCellValue((String) variableList.get(j));
                } else {
                    Object value = classList.get(j);
                    if ((value instanceof String) || (value.equals(String.class))) {
                        cell.setCellValue((String) mainBuffer.get(j).get(i - 1));
                    } else if ((value instanceof Boolean) || (value.equals(Boolean.class))) {
                        cell.setCellValue((Boolean) mainBuffer.get(j).get(i - 1));
                    } else if ((value instanceof Date) || (value.equals(Date.class))) {

                        CellStyle cellStyle = workbook.createCellStyle();
                        CreationHelper createHelper = workbook.getCreationHelper();
                        cellStyle.setDataFormat(
                                createHelper.createDataFormat().getFormat(dateStringFormat));
                        cell.setCellValue((Date) mainBuffer.get(j).get(i - 1));
                        cell.setCellStyle(cellStyle);

                    } else if ((value instanceof Integer) || (value.equals(Integer.class))) {
                        cell.setCellValue((Integer) mainBuffer.get(j).get(i - 1));
                    } else if ((value instanceof Long) || (value.equals(Long.class))) {
                        cell.setCellValue((Long) mainBuffer.get(j).get(i - 1));
                    } else if ((value instanceof Float) || (value.equals(Float.class))) {
                        cell.setCellValue((Float) mainBuffer.get(j).get(i - 1));
                    } else if ((value instanceof Double) || (value.equals(Double.class))) {
                        cell.setCellValue((Double) mainBuffer.get(j).get(i - 1));
                    }
                }
            }
        }
        return workbook;
    }

    /**
     * Construye la hoja de Excel
     *
     * @return Instancia de la clase XSSFWorkbook con los datos almacenados en
     * el buffer de datos
     */
    private String buildJSON() {
        JsonArray mainArray = new JsonArray();
        for (int i = 0; i < mainBuffer.get(0).size(); i++) {
            JsonArray array = new JsonArray();
            for (int j = 0; j < variableList.size(); j++) {
                Object value = mainBuffer.get(j).get(i);
                if ((value instanceof Date) || (value.equals(Date.class))) {
                    JsonObject cell = new JsonObject();
                    cell.add(variableList.get(j), dateFormat.format(value));
                    array.add(cell);
                } else {
                    JsonObject cell = new JsonObject();
                    cell.add(variableList.get(j), String.valueOf(value));
                    array.add(cell);
                }
            }
            mainArray.add(array);
        }
        return String.valueOf(mainArray.asArray());
    }

    /**
     * Construye un JTable y la inserta dentro de un JPanel
     *
     * @param panel JPanel donde se desea insertar la tabla
     */
    public void insertToPanel(JPanel panel) {
        buildTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);;
        scroll.setViewportView(table);
        scroll.setVisible(true);
        scroll.setBounds(0, 0, panel.getWidth(), panel.getHeight() - 25);
        tableFlag = true;
        panel.add(scroll);
    }

    /**
     *
     * @return JTable creado dentro de la clase
     */
    public JTable getTable() {
        buildTable();
        return this.table;
    }

    /**
     *
     * @return JScrollPane con la tabla de datos contenida en su interior
     */
    public JScrollPane getScrollPane() {
        buildTable();
        scroll = new javax.swing.JScrollPane();
        scroll.setViewportView(table);
        scroll.setVisible(true);
        return this.scroll;
    }

    /**
     * Construye la JTable donde se almacenan los datos
     */
    private void buildTable() {
        if (!tableFlag) {
            table = new JTable();
            table.setRowHeight(30);
            String[] headerTitles = new String[variableList.size()];
            Object[][] tableContent = new Object[mainBuffer.get(0).size()][variableList.size()];
            for (int i = 0; i < variableList.size(); i++) {
                headerTitles[i] = variableList.get(i);
                for (int j = 0; j < mainBuffer.get(i).size(); j++) {
                    tableContent[j][i] = mainBuffer.get(i).get(j);
                }
            }
            Class[] classes = new Class[classList.size()];
            for (int i = 0; i < classList.size(); i++) {
                classes[i] = classList.get(i).getClass();
            }
            table.setModel(new DefaultTableModel(tableContent, headerTitles) {
                Class[] types = classes;

                @Override
                public Class getColumnClass(int columnIndex) {
                    return this.types[columnIndex];
                }
            });
            DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            renderer.setHorizontalAlignment(0);
            table.getTableHeader().setReorderingAllowed(false);
            ((DefaultTableModel) table.getModel()).addTableModelListener(tableModelListener);

            for (int i = 0; i < classList.size(); i++) {
                if (classList.get(i) instanceof Date) {
                    TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
                        SimpleDateFormat f = dateFormat;

                        public Component getTableCellRendererComponent(JTable table,
                                Object value, boolean isSelected, boolean hasFocus,
                                int row, int column) {
                            if (value instanceof Date) {
                                value = f.format(value);
                            }
                            return super.getTableCellRendererComponent(table, value, isSelected,
                                    hasFocus, row, column);
                        }
                    };
                    table.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
                }

            }
        }

    }

    /**
     * Agrega el evento DataInsertionListener
     *
     * @param listener Instancia de la clase DataInsertionListener
     */
    public void addEventListener(DataInsertionListener listener) {
        listeners.add(listener);
        listenerFlag = true;
    }

    /**
     * Elimina el eventListener
     */
    public void removeEventListener() {
        listeners.remove(listeners.size() - 1);
        listenerFlag = false;
    }

    /**
     * Disparador de evento onDataInsertion
     */
    private void triggerDataInsertionEvent() {
        ListIterator li = listeners.listIterator();
        while (li.hasNext()) {
            DataInsertionListener listener = (DataInsertionListener) li.next();
            DataInsertionEvent readerEvObj = new DataInsertionEvent(this, this);
            (listener).onDataInsertion(readerEvObj);
        }
    }

    /**
     *
     * @return Lista de clases almacenadas en las columnas del databuffer
     */
    public List<Object> getClassList() {
        return classList;
    }

    /**
     *
     * @return Lista de los nombres de las columnas declaradas
     */
    public List<String> getVariableList() {
        return variableList;
    }

    /**
     *
     * @return Buffer de datos
     */
    public List<List<Object>> getMainBuffer() {
        return mainBuffer;
    }

    /**
     * @return Indice de la columna de tiempo
     */
    public int getTimeColumn() {
        return timeColumn;
    }

    /**
     * Permite ordenar los datos en una columna de la tabla que genera la clase
     *
     * @param column La columna que se desea ordenar
     * @param ascending si se desea un orden ascendente o descendente
     *
     * @since 3.0.3
     */
    public void sortColumn(int column, boolean ascending) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        if (ascending) {
            sortKeys.add(new RowSorter.SortKey(column, SortOrder.ASCENDING));
        } else {
            sortKeys.add(new RowSorter.SortKey(column, SortOrder.DESCENDING));
        }

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

}
