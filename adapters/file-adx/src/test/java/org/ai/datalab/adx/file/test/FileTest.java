/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.file.test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.ai.datalab.adx.file.FileAdapter;
import org.ai.datalab.adx.file.FileResourcePool;

import org.ai.datalab.core.DataJob;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.visual.DataLabVisualUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public class FileTest {

    public FileTest() {
    }

    @Test
    public void testCsv() throws Exception {
        //TODO delete the outpool after use
        FileResourcePool pool = new FileResourcePool(Paths.get("src", "test", "resources", "input.csv").toFile());
        FileResourcePool outpool = new FileResourcePool(File.createTempFile("test", "file"));
        ResourceFactory.addResourcePool(pool);
        ResourceFactory.addResourcePool(outpool);
        DataJob job = DataJob.getJob("testJob ", null);

        String format = "${EMPLOYEE_ID},${FIRST_NAME},${LNAME},${EMAIL},${PHONE_NUMBER},${H_DATE},${JOB_ID},${SALARY},${COMMISSION_PCT},${MANAGER_ID},${DEPARTMENT_ID}";
        job.setReader("Reader", FileAdapter.createReader(pool, null, true, format, 1024 * 20, "\""))
                .addExecutor("testWrite", FileAdapter.createWriter(outpool, null, true, format, 1024, "\"", false));

        System.out.println("Starting Job " + new Date());

        try {
            DataLabVisualUtil.visualize(job);
        } catch (Exception ex) {
            Logger.getLogger(FileTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("completed Job " + new Date());
        try (Resource<File> in = pool.getResource()) {
            try (Resource<File> out = outpool.getResource()) {
                Assert.assertEquals(FileUtils.readFileToString(in.get()), FileUtils.readFileToString(out.get()));
                out.get().delete();
            }
        }

        // Thread.sleep(1000000);
    }
}
