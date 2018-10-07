/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.test;

import java.time.Duration;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ai.datalab.core.DataJob;
import org.ai.datalab.core.DataLabJobListener;
import org.ai.datalab.core.ExecutorProvider;

/**
 *
 * @author Mohan Purushothaman
 */
public class BasicTest {

    public BasicTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void simpleTest() {
        System.out.println("Initiaiting Job " + new Date());

        DataJob job = DataJob.getJob("testJob ", null);

        job.setReader("Reader", ExecutorProvider.getReaderInstance(NumberReader.class))
                .addExecutor("Processor", ExecutorProvider.getProcessorInstance(NumberProcessor.class))
                .addExecutor("Writer", ExecutorProvider.getWriterInstance(NumberWriter.class));

        System.out.println("Starting Job " + new Date());

        try {
            job.startJob(DataLabJobListener.EMPTY).get();

        } catch (Exception ex) {
            Logger.getLogger(BasicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Job Ended " + new Date());

    }

    //@Test
    public void stressTest() {
        List<Long> timeTaken = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            long s = System.currentTimeMillis();
            simpleTest();
            timeTaken.add(System.currentTimeMillis() - s);

        }
        System.err.println(timeTaken);
    }

    
}
