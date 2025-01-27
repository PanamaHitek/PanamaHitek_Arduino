/**
 * ======================= Aviso de Derechos de Autor =======================
 *
 * El presente código ha sido desarrollado por Antony García González en colaboración con el Equipo Creativo de Panama Hitek.
 *
 * Este código está protegido bajo la licencia GNU Lesser General Public License (LGPL) versión 2.1,
 * cuya copia puede consultarse en el siguiente enlace: http://www.gnu.org/licenses/lgpl.txt.
 *
 * Para su funcionamiento, el código utiliza la biblioteca JSSC (anteriormente conocida como RXTX),
 * la cual ha sido incorporada en su versión original, sin modificaciones por parte de nuestro equipo.
 * Expresamos nuestro agradecimiento a Alexey Sokolov, creador de JSSC, por proporcionar una herramienta
 * poderosa y eficiente que ha permitido mejorar nuestra biblioteca.
 *
 * Esta biblioteca es de código abierto y ha sido diseñada con el propósito de proporcionar a los usuarios,
 * desde principiantes hasta expertos, las herramientas necesarias para el desarrollo de sus proyectos
 * de manera sencilla e intuitiva.
 *
 * Se solicita que, en cualquier uso o distribución de este código, se reconozca su origen y autoría.
 * Este software fue desarrollado en la República de Panamá por Antony García González, Ingeniero Electromecánico,
 * docente e investigador de la Universidad de Panamá. Este repositorio ha sido mantenido desde el año 2013 hasta
 * la fecha.
 *
 * El autor es miembro del Equipo Creativo de Panama Hitek, una organización sin fines de lucro
 * dedicada a la enseñanza del desarrollo de software y hardware a través de su sitio web oficial: http://panamahitek.com.
 *
 * Apreciamos que esta compilación de código sea reconocida como un trabajo desarrollado por panameños,
 * con el propósito de contribuir tanto a Panamá como al resto del mundo.
 *
 * Para cualquier consulta o comunicación, puede escribirnos a: creativeteam@panamahitek.com.
 *
 * ========================================================================
 *
 * ======================= Copyright Notice =======================
 *
 * This code has been developed by Antony García González in collaboration with the Creative Team of Panama Hitek.
 *
 * This code is protected under the GNU Lesser General Public License (LGPL) version 2.1,
 * a copy of which can be found at the following link: http://www.gnu.org/licenses/lgpl.txt.
 *
 * This code makes use of the JSSC library (formerly known as RXTX),
 * which has been included in its original version, without modifications by our team.
 * We express our gratitude to Alexey Sokolov, creator of JSSC, for providing such a powerful
 * and efficient tool that has enabled improvements to our library.
 *
 * This library is open-source and has been designed to provide users, from beginners to experts,
 * with the necessary tools for the development of their projects in a simple and intuitive manner.
 *
 * We request that, in any use or distribution of this code, its origin and authorship be acknowledged.
 * This software was developed in the Republic of Panama by Antony García González, Electromechanical Engineer,
 * professor, and researcher at the University of Panama. This repository has been maintained from the year 2013
 * to the present.
 *
 * The author is a member of the Creative Team of Panama Hitek, a non-profit organization dedicated
 * to teaching software and hardware development through its official website: http://panamahitek.com.
 *
 * We appreciate that this compilation of code is recognized as a work developed by Panamanians,
 * with the purpose of contributing to Panama and the rest of the world.
 *
 * For any inquiries or communication, please contact us at: creativeteam@panamahitek.com.
 *
 * ========================================================================
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
 * [ES] <br>
 * Esta clase ha sido diseñada para gestionar el almacenamiento de datos en
 * hojas de cálculo de Excel de manera fácil y rápida. <br>
 * Proporciona métodos para agregar columnas, insertar datos, exportar a
 * archivos Excel y JSON, y visualizar los datos en una tabla. <br>
 * Para más información, visita panamahitek.com. <br>
 * <br>
 * [EN] <br>
 * This class is designed to manage data storage in Excel spreadsheets easily
 * and quickly. <br>
 * It provides methods to add columns, insert data, export to Excel and JSON
 * files, and visualize the data in a table. <br>
 * For more information, visit panamahitek.com. <br>
 * <br>
 *
 * @author Antony García González
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

    /**
     * [ES] <br>
     * Listener para cambios en el modelo de la tabla. <br>
     * Este listener se encarga de actualizar la visualización de la tabla
     * cuando se detectan cambios en el modelo de datos. <br>
     * <br>
     * [EN] <br>
     * Listener for table model changes. <br>
     * This listener updates the table view when changes in the data model are
     * detected. <br>
     */
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
     * [ES] <br>
     * Constructor de la clase `PanamaHitek_DataBuffer`. <br>
     * Este constructor inicializa las listas principales que se utilizarán para
     * almacenar los datos, las variables y los oyentes de eventos. <br>
     * <br>
     * [EN] <br>
     * Constructor of the `PanamaHitek_DataBuffer` class. <br>
     * This constructor initializes the main lists that will be used to store
     * data, variables, and event listeners. <br>
     */
    public PanamaHitek_DataBuffer() {
        mainBuffer = new ArrayList<>();
        variableList = new ArrayList<>();
        classList = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    /**
     * [ES] <br>
     * Limpia el buffer de datos. <br>
     * Este método elimina todos los datos almacenados en el buffer y reinicia
     * el contador de filas. <br>
     * <br>
     * [EN] <br>
     * Clears the data buffer. <br>
     * This method removes all data stored in the buffer and resets the row
     * count. <br>
     */
    public void clearBuffer() {
        for (int i = 0; i < mainBuffer.size(); i++) {
            mainBuffer.get(i).clear();
        }
        ROW_COUNT = 0;
    }

    /**
     * [ES] <br>
     * Permite agregar una columna al buffer de datos. <br>
     * Este método añade una nueva columna al buffer, especificando su índice,
     * nombre y tipo de datos. <br>
     * <br>
     * [EN] <br>
     * Adds a column to the data buffer. <br>
     * This method adds a new column to the buffer, specifying its index, name,
     * and data type. <br>
     * <br>
     *
     * @param index <br>
     * [ES] El índice (posición) de la columna. <br>
     * [EN] The index (position) of the column. <br>
     * <br>
     * @param variableName <br>
     * [ES] El nombre que se desea asignar a la variable. <br>
     * [EN] The name to be assigned to the variable. <br>
     * <br>
     * @param dataType <br>
     * [ES] Tipo de datos a guardar en la columna. <br>
     * [EN] Data type to be stored in the column. <br>
     */
    public void addColumn(int index, String variableName, Object dataType) {
        variableList.add(index, variableName);
        classList.add(dataType);
        List<Object> list = new ArrayList<>();
        mainBuffer.add(list);
    }

    /**
     * [ES] <br>
     * Agrega una columna de registro de tiempo al buffer de datos. <br>
     * Este método permite almacenar registros de tiempo en una columna
     * específica del buffer. <br>
     * <br>
     * [EN] <br>
     * Adds a time log column to the data buffer. <br>
     * This method allows storing time logs in a specific column of the buffer.
     * <br>
     * <br>
     *
     * @param index <br>
     * [ES] El índice (posición) de la columna de tiempo. <br>
     * [EN] The index (position) of the time column. <br>
     * <br>
     * @param variableName <br>
     * [ES] El nombre que se desea asignar a la variable. <br>
     * [EN] The name to be assigned to the variable. <br>
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
     * [ES] <br>
     * Establece el formato de la fecha. Por ejemplo, si se desea que la fecha
     * tenga el formato de horas, minutos y segundos, se debe enviar como
     * parámetro `new SimpleDateFormat("HH:mm:ss")`. <br>
     * <br>
     * [EN] <br>
     * Sets the date format. For example, if you want the date to have the
     * format of hours, minutes, and seconds, you should pass `new
     * SimpleDateFormat("HH:mm:ss")` as a parameter. <br>
     * <br>
     *
     * @param format <br>
     * [ES] Formato que se desea establecer. <br>
     * [EN] Format to be set. <br>
     * <br>
     * @see SimpleDateFormat
     */
    public void setDateFormat(String format) {
        this.dateFormat = new SimpleDateFormat(format);
        dateStringFormat = format;
    }

    /**
     * [ES] <br>
     * Establece el formato de la fecha. Por ejemplo, si se desea que la fecha
     * tenga el formato de horas, minutos y segundos, se debe enviar como
     * parámetro `new SimpleDateFormat("HH:mm:ss")`. <br>
     * <br>
     * [EN] <br>
     * Sets the date format. For example, if you want the date to have the
     * format of hours, minutes, and seconds, you should pass `new
     * SimpleDateFormat("HH:mm:ss")` as a parameter. <br>
     * <br>
     *
     * @param format <br>
     * [ES] Formato que se desea establecer. <br>
     * [EN] Format to be set. <br>
     * <br>
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
     * [ES] <br>
     * Agrega un nuevo valor al buffer de datos. <br>
     * Este método permite almacenar un valor en una columna específica del
     * buffer. <br>
     * <br>
     * [EN] <br>
     * Adds a new value to the data buffer. <br>
     * This method allows storing a value in a specific column of the buffer.
     * <br>
     * <br>
     *
     * @param column <br>
     * [ES] Columna en la que se desea almacenar el valor. <br>
     * [EN] Column where the value will be stored. <br>
     * <br>
     * @param value <br>
     * [ES] Valor a almacenar. <br>
     * [EN] Value to be stored. <br>
     * <br>
     * @throws Exception <br>
     * [ES] Se lanza si se selecciona una columna que no ha sido definida o si
     * se intenta guardar un valor de un tipo diferente al establecido para la
     * columna. <br>
     * [EN] Thrown if a column that has not been defined is selected or if an
     * attempt is made to store a value of a different type than the one
     * established for the column. <br>
     */
    public void addValue(int column, Object value) throws Exception {
        if (column > variableList.size()) {
            throw new Exception("El parámetro 'column' no puede ser mayor a la cantidad de columnas declaradas.");
        }
        mainBuffer.get(column).add(ROW_COUNT, value);
    }

    /**
     * [ES] <br>
     * Devuelve la cantidad de filas almacenadas en el buffer. <br>
     * <br>
     * [EN] <br>
     * Returns the number of rows stored in the buffer. <br>
     *
     * @return <br>
     * [ES] La cantidad de filas almacenadas en el buffer. <br>
     * [EN] The number of rows stored in the buffer. <br>
     */
    public int getRowCount() {
        return ROW_COUNT;
    }

    /**
     * [ES] <br>
     * Devuelve la cantidad de columnas del buffer. <br>
     * <br>
     * [EN] <br>
     * Returns the number of columns in the buffer. <br>
     *
     * @return <br>
     * [ES] La cantidad de columnas del buffer. <br>
     * [EN] The number of columns in the buffer. <br>
     */
    public int getColumnCount() {
        return variableList.size();
    }

    /**
     * [ES] <br>
     * Establece el nombre de la hoja en el libro de Excel. <br>
     * <br>
     * [EN] <br>
     * Sets the name of the sheet in the Excel workbook. <br>
     *
     * @param sheetName <br>
     * [ES] El nombre de la hoja en el libro de Excel. <br>
     * [EN] The name of the sheet in the Excel workbook. <br>
     */
    public void setSheetName(String sheetName) {
        this.SHEET_NAME = sheetName;
    }

    /**
     * [ES] <br>
     * Obtiene el nombre de la hoja de Excel. <br>
     * <br>
     * [EN] <br>
     * Gets the name of the Excel sheet. <br>
     *
     * @return <br>
     * [ES] El nombre de la hoja de Excel. <br>
     * [EN] The name of the Excel sheet. <br>
     */
    public String getSheetName() {
        return SHEET_NAME;
    }

    /**
     * [ES] <br>
     * Abre una ventana emergente para seleccionar la ubicación donde se desea
     * guardar la hoja de datos de Excel. <br>
     * <br>
     * [EN] <br>
     * Opens a dialog window to select the location where the Excel data sheet
     * will be saved. <br>
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
     * [ES] <br>
     * Crea y guarda la hoja de Excel con los datos del buffer en una ubicación
     * específica. <br>
     * <br>
     * [EN] <br>
     * Creates and saves the Excel sheet with the data from the buffer to a
     * specific location. <br>
     * <br>
     *
     * @param path <br>
     * [ES] Ubicación donde se desea guardar el archivo. <br>
     * [EN] Location where the file should be saved. <br>
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
     * [ES] <br>
     * Construye la hoja de Excel con los datos almacenados en el buffer. <br>
     * <br>
     * [EN] <br>
     * Builds the Excel sheet with the data stored in the buffer. <br>
     * <br>
     *
     * @return <br>
     * [ES] Instancia de la clase XSSFWorkbook con los datos almacenados en el
     * buffer. <br>
     * [EN] Instance of the XSSFWorkbook class with the data stored in the
     * buffer. <br>
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
     * [ES] <br>
     * Construye la hoja de Excel con los datos almacenados en el buffer. <br>
     * <br>
     * [EN] <br>
     * Builds the Excel sheet with the data stored in the buffer. <br>
     * <br>
     *
     * @return <br>
     * [ES] Instancia de la clase XSSFWorkbook con los datos almacenados en el
     * buffer. <br>
     * [EN] Instance of the XSSFWorkbook class with the data stored in the
     * buffer. <br>
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
     * [ES] <br>
     * Construye un `JTable` y lo inserta dentro de un `JPanel`. <br>
     * <br>
     * [EN] <br>
     * Builds a `JTable` and inserts it into a `JPanel`. <br>
     * <br>
     *
     * @param panel <br>
     * [ES] `JPanel` donde se desea insertar la tabla. <br>
     * [EN] `JPanel` where the table will be inserted. <br>
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
     * [ES] <br>
     * Devuelve la instancia de `JTable` creada dentro de la clase. <br>
     * <br>
     * [EN] <br>
     * Returns the instance of `JTable` created within the class. <br>
     */
    public JTable getTable() {
        buildTable();
        return this.table;
    }

    /**
     * [ES] <br>
     * Devuelve un JScrollPane que contiene la tabla de datos. <br>
     * <br>
     * [EN] <br>
     * Returns a JScrollPane containing the data table. <br>
     */
    public JScrollPane getScrollPane() {
        buildTable();
        scroll = new javax.swing.JScrollPane();
        scroll.setViewportView(table);
        scroll.setVisible(true);
        return this.scroll;
    }

    /**
     * [ES] <br>
     * Construye una JTable de Swing donde se almacenan los datos. <br>
     * Este método configura la tabla con el modelo de datos, establece el
     * renderizador de celdas y ajusta las propiedades de la tabla para su
     * correcta visualización. <br>
     * <br>
     * [EN] <br>
     * Builds a Swing JTable where the data is stored. <br>
     * This method sets up the table with the data model, configures the cell
     * renderer, and adjusts the table properties for proper display. <br>
     *
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
     * [ES] <br>
     * Agrega el evento DataInsertionListener. <br>
     * <br>
     * [EN] <br>
     * Adds the DataInsertionListener event. <br>
     * <br>
     *
     * @param listener <br>
     * [ES] Instancia de la clase DataInsertionListener. <br>
     * [EN] Instance of the DataInsertionListener class. <br>
     */
    public void addEventListener(DataInsertionListener listener) {
        listeners.add(listener);
        listenerFlag = true;
    }

    /**
     * [ES] <br>
     * Elimina el eventListener. <br>
     * <br>
     * [EN] <br>
     * Removes the eventListener. <br>
     */
    public void removeEventListener() {
        listeners.remove(listeners.size() - 1);
        listenerFlag = false;
    }

    /**
     * [ES] <br>
     * Disparador de evento onDataInsertion. <br>
     * Este método se encarga de notificar a todos los listeners registrados
     * cuando se inserta un nuevo dato en el buffer. <br>
     * <br>
     * [EN] <br>
     * onDataInsertion event trigger. <br>
     * This method is responsible for notifying all registered listeners when a
     * new data is inserted into the buffer. <br>
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
     * [ES] <br>
     *
     * @return Lista de clases almacenadas en las columnas del databuffer. <br>
     * <br>
     * [EN] <br>
     * @return List of classes stored in the databuffer columns.
     */
    public List<Object> getClassList() {
        return classList;
    }

    /**
     * [ES] <br>
     *
     * @return Lista de los nombres de las columnas declaradas. <br>
     * <br>
     * [EN] <br>
     * @return List of declared column names.
     */
    public List<String> getVariableList() {
        return variableList;
    }

    /**
     * [ES] <br>
     * Obtiene el buffer de datos. <br>
     * <br>
     * [EN] <br>
     * Gets the data buffer. <br>
     * <br>
     *
     * @return <br>
     * [ES] El buffer de datos. <br>
     * [EN] The data buffer. <br>
     */
    public List<List<Object>> getMainBuffer() {
        return mainBuffer;
    }

    /**
     * [ES] <br>
     *
     * @return Índice de la columna de tiempo. <br>
     * <br>
     * [EN] <br>
     * @return Index of the time column.
     */
    public int getTimeColumn() {
        return timeColumn;
    }

    /**
     * [ES] <br>
     * Ordena los datos en una columna específica de la tabla generada por esta
     * clase. <br>
     * <br>
     * [EN] <br>
     * Sorts the data in a specific column of the table generated by this class.
     *
     * @param column [ES] La columna a ordenar. <br>
     * [EN] The column to sort.
     * @param ascending [ES] Indica si el orden debe ser ascendente o
     * descendente. <br>
     * [EN] Indicates whether the order should be ascending or descending.
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
