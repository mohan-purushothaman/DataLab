package org.ai.datalab.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ai.datalab.core.DataJob;
import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.core.builder.ExecutionUnit;
//import org.ai.datalab.core.misc.HazelcastHelper;
import org.ai.datalab.visual.DataLabVisualUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public class VisualTest {

    public VisualTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //System.out.println("Starting instance "+ HazelcastHelper.PRIVATE_INSTANCE.getName());
    }

    @After
    public void tearDown() {
        //Hazelcast.shutdownAll();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void simpleTest() {
        System.out.println("Initiaiting Job " + new Date());

        DataJob job = DataJob.getJob("testJob ", null);

        ExecutionUnit p = job.setReader("Reader",ExecutorProvider.getReaderInstance(NumberReader.class))
                .addExecutor("Processor1",ExecutorProvider.getProcessorInstance(NumberProcessor.class))
                .getFirstParent().addExecutor("Processor2",ExecutorProvider.getProcessorInstance(NumberProcessor.class));
                p.getFirstParent().addExecutor("Processor3",ExecutorProvider.getProcessorInstance(NumberProcessor.class))
                .getFirstParent().addExecutor("Processor4",ExecutorProvider.getProcessorInstance(NumberProcessor.class))
                .getFirstParent().addExecutor("Processor5",ExecutorProvider.getProcessorInstance(NumberProcessor.class))
                .addExecutor("Writer5",ExecutorProvider.getWriterInstance(NumberWriter.class));

        System.out.println("Starting Job " + new Date());

        try {
            
            DataLabVisualUtil.visualize(job);

        } catch (Exception ex) {
            Logger.getLogger(VisualTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Job Ended " + new Date());

    }
    
  
    
    
}
