/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adapter.excel.adx;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.ai.datalab.core.misc.Type;
import org.ai.datalab.core.misc.TypeUtil;
import org.ai.datalab.core.resource.ResourcePool;
import org.ai.datalab.core.resource.Resource;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExcelUtil {

    public static final FileFilter EXCEL_FILTER = new FileNameExtensionFilter("Excel file", "xls", "xlsx");

    private static final DataFormatter FORMAT = new DataFormatter();

    public static Object readValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        return FORMAT.formatCellValue(cell);
//
//        switch (cell.getCellType()) {
//            case NUMERIC: {
//                if (DateUtil.isCellDateFormatted(cell)) {
//                    return cell.getDateCellValue();
//                }
//                return new DataFormatter().
//            }
//            case STRING: {
//                return cell.getStringCellValue();
//            }
//            case FORMULA: {
//                return cell.getCellFormula();
//            }
//            case BLANK: {
//                return null;
//            }
//            case BOOLEAN: {
//                return cell.getBooleanCellValue();
//            }
//            case ERROR: {
//                return cell.getErrorCellValue();
//            }
//
//            default: {
//                return new DataFormatter().formatCellValue(cell);
//            }
//        }
    }

    public static void setValue(Cell c, Object value) throws Exception {

        Type type = TypeUtil.detectType(value);
        if (value != null) {
            switch (type) {
                case Date:
                case Timestamp: {
                    c.setCellValue((Date) value);
                    return;
                }
                case Boolean: {
                    c.setCellValue((Boolean) value);
                    return;
                }
                case Long: {
                    c.setCellValue(((Number) value).longValue());
                    return;
                }
                case Double: {
                    c.setCellValue(((Number) value).doubleValue());
                    return;
                }

            }
        }

        c.setCellValue(value == null ? (String) value : value.toString());

        //c.setCellValue(String.valueOf(field.getValue()));
    }

    public static Set<String> getSheetNames(ResourcePool<File> excelFile) throws Exception {
        Set<String> s = new LinkedHashSet<>();
        try (Resource<File> f = excelFile.getResource()) {
            File file = f.get();
            if (file.exists()) {
                try (Workbook ws = WorkbookFactory.create(file)) {
                    int numberOfSheets = ws.getNumberOfSheets();
                    for (int i = 0; i < numberOfSheets; i++) {
                        s.add(ws.getSheetName(i));
                    }
                }
            }
        }
        return s;
    }

    public static Object[][] fetchFirst2Rows(ResourcePool<File> excelFile, String sheetName) throws Exception {

        try (Resource<File> f = excelFile.getResource()) {
            File file = f.get();
            if (file.exists()) {
                try (Workbook ws = WorkbookFactory.create(file)) {
                    Sheet s = ws.getSheet(sheetName);
                    if (s != null) {
                        Row row = s.getRow(0);
                        if (row != null) {
                            Object[][] val = new Object[2][];
                            int size = row.getLastCellNum();
                            val[0] = new Object[size];
                            for (int i = 0; i < size; i++) {
                                val[0][i] = ExcelUtil.readValue(row.getCell(i));
                            }
                            row = s.getRow(1);
                            val[1] = new Object[size];
                            if (row != null) {
                                for (int i = 0; i < size; i++) {
                                    val[1][i] = ExcelUtil.readValue(row.getCell(i));
                                }
                            }
                            return val;
                        }
                    }
                }
            }
        }
        return null;
    }
}
