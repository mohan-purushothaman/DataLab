/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adapter.excel.adx.test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.apache.poi.ss.examples.ExcelComparator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.ai.datalab.adapter.excel.adx.ExcelReader;
import org.ai.datalab.adapter.excel.adx.ExcelResourcePool;
import org.ai.datalab.adapter.excel.adx.ExcelWriter;

import org.ai.datalab.core.DataJob;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.ValueConverter;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.visual.DataLabVisualUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public class ExcelTest {

    public ExcelTest() {
    }

    @Test
    public void testExcel() throws Exception {
//TODO delete the outpool after use
        ExcelResourcePool pool = new ExcelResourcePool(Paths.get("src", "test", "resources", "input.xls").toFile());
        ExcelResourcePool outpool = new ExcelResourcePool(File.createTempFile("test", ".xlsx"));
        System.out.println(outpool.getResourceId());
        ResourceFactory.addResourcePool(pool);
        ResourceFactory.addResourcePool(outpool);
        DataJob job = DataJob.getJob("testJob ", null);

        String sheetName = "emp_data";

        MappingHelper map = new MappingHelper();
        map.addIdMap(0, "EMPLOYEE_ID", ValueConverter.LONG_CONVERTER,  "?");
        map.addIdMap(1, "FIRST_NAME", ValueConverter.SIMPLE_STRING_CONVERTER,  "?");
        map.addIdMap(2, "LNAME", ValueConverter.SIMPLE_STRING_CONVERTER,  "?");
        map.addIdMap(3, "EMAIL", ValueConverter.SIMPLE_STRING_CONVERTER,  "?");
        map.addIdMap(4, "PHONE_NUMBER", ValueConverter.SIMPLE_STRING_CONVERTER,  "?");
        map.addIdMap(5, "H_DATE", ValueConverter.SIMPLE_STRING_CONVERTER,  "?");
        map.addIdMap(6, "JOB_ID", ValueConverter.SIMPLE_STRING_CONVERTER,  "?");
        map.addIdMap(7, "SALARY", ValueConverter.LONG_CONVERTER,  "?");
        map.addIdMap(8, "COMMISSION_PCT", ValueConverter.NO_CONVERTER,  "?");
        map.addIdMap(9, "MANAGER_ID", ValueConverter.LONG_CONVERTER,  "?");
        map.addIdMap(10, "DEPARTMENT_ID", ValueConverter.LONG_CONVERTER,  "?");

        job.setReader("Reader", new ExcelReader(map, pool, sheetName, true))
                .addExecutor("testWrite", new ExcelWriter(outpool, sheetName, true));

        System.out.println("Starting Job " + new Date());

        try {
            DataLabVisualUtil.visualize(job);
        } catch (Exception ex) {
            Logger.getLogger(ExcelTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Resource<File> f1 = pool.getResource()) {
            try (Resource<File> f2 = outpool.getResource()) {
                try (Workbook w1 = WorkbookFactory.create(f1.get())) {
                    try (Workbook w2 = WorkbookFactory.create(f2.get())) {

                        List<String> compare = ExcelComparator.compare(w1, w2);
                        for (String s : compare) {
                            if (!s.contains("Cell Data-Type does not Match in :: ")) {
                                Assert.fail(s);
                            }
                        }

                    }
                }
                f2.get().delete();
            }
        }

    }
}
