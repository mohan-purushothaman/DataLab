/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adapter.excel.adx;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.Executor;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Writer;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExcelWriter extends ExcelProvider {

    public ExcelWriter(ResourcePool<File> resource, String sheetName, boolean hasHeader) {
        super(null, resource.getResourceId(), sheetName, hasHeader);
    }

    @Override
    public ExecutorType getProvidingType() {
        return ExecutorType.WRITER;
    }

    @Override
    public Executor getNewExecutor() {
        return new Writer() {

            private Resource<File> resource;
            private OutputStream openHandle;
            private Workbook workbook;

            private Sheet sheet;

            private final AtomicInteger rownum = new AtomicInteger(0);
            private final List<String> keyList = new ArrayList<>();

            @Override
            public void writeData(Data[] d, ExecutionConfig config) throws Exception {
                for (Data data : d) {

                    Row row = sheet.createRow(rownum.getAndIncrement());
                    if (row.getRowNum() == 0) {

                        keyList.addAll(data.getKeyNames());
                        if (isHasHeader()) {
                            int no = 0;
                            for (String s : keyList) {
                                row.createCell(no++).setCellValue(s);
                            }
                        }

                        row = sheet.createRow(rownum.getAndIncrement());
                    }
                    int no = 0;
                    for (String s : keyList) {
                        ExcelUtil.setValue(row.createCell(no++), data.getValue(s));
                    }
                }
            }

            @Override
            public void init(ExecutionConfig config) throws Exception {
                String excelFile = getResourceID();
                resource = ResourceFactory.getResourcePool(excelFile).getResource();
                File file = resource.get();
                openHandle = new FileOutputStream(file);
                if (excelFile.toLowerCase().endsWith("s")) {
                    workbook = new HSSFWorkbook();
                } else {
                    workbook = new SXSSFWorkbook(1000);
                }
                sheet = workbook.createSheet(getSheetName());

            }

            @Override
            public void shutdown(ExecutionConfig config) throws Exception {

                workbook.write(openHandle);
                workbook.close();
                openHandle.close();
                resource.close();

            }

        };
    }

}
