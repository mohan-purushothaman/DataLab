/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adapter.excel.adx;

import java.io.File;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.Executor;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.ValueConverter;
import org.ai.datalab.core.adx.misc.ValueGroupingStrategy;
import org.ai.datalab.core.adx.misc.ValueMapper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Reader;
import org.ai.datalab.core.misc.SimpleData;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExcelReader extends ExcelProvider {

    public ExcelReader(MappingHelper<Integer> mapping, ResourcePool<File> resourceId, String sheetName, boolean hasHeader) {
        super(mapping, resourceId.getResourceId(), sheetName, hasHeader);
    }

    @Override
    public ExecutorType getProvidingType() {
        return ExecutorType.READER;
    }

    @Override
    public Executor getNewExecutor() {
        return new Reader() {

            private Resource<File> resource;
            private Workbook workbook;
            private Sheet sheet;

            private Iterator<Row> iterator;

            private MappingHelper<Integer> mapping = getMapping();

            @Override
            public Data readData(ExecutionConfig config) throws Exception {
                if (iterator.hasNext()) {
                    Row row = iterator.next();
                    Data data = new SimpleData();
                    //TODO implement streaming read
                    mapping.map(new ValueMapper<Integer>() {
                        @Override
                        public Object[] getValue(Integer id) throws Exception {
                            return new Object[]{ExcelUtil.readValue(row.getCell(id))};
                        }
                    }, data);

                    return data;
                }
                return null;
            }

            @Override
            public void init(ExecutionConfig config) throws Exception {
                String excelFile = getResourceID();
                resource = ResourceFactory.getResourcePool(excelFile).getResource();
                File file = resource.get();
                workbook = WorkbookFactory.create(file, null, true);
                sheet = workbook.getSheet(getSheetName());
                iterator = sheet.iterator();
                if (isHasHeader() && iterator.hasNext()) {
                    Row row = iterator.next();

                    if (mapping == null) {
                        mapping = new MappingHelper<>();
                        for (Cell c : row) {
                            String columnName = c.getStringCellValue();
                            mapping.addIdMap(c.getColumnIndex(), columnName, ValueConverter.SIMPLE_STRING_CONVERTER, ValueGroupingStrategy.SINGLE, "");
                        }
                    }

                }
            }

            @Override
            public void shutdown(ExecutionConfig config) throws Exception {
                workbook.close();
                resource.close();
            }
        };
    }

}
