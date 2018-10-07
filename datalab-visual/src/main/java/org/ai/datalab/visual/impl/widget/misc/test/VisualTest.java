package org.ai.datalab.visual.impl.widget.misc.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ai.datalab.core.DataJob;
import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.visual.DataLabVisualUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public class VisualTest {

    public static void main(String[] args) {
      
        System.out.println("Initiaiting Job " + new Date());

        DataJob job = DataJob.getJob("testJob ", null);

         job.setReader("Reader",ExecutorProvider.getReaderInstance(NumberReader.class))
                .addExecutor("Processor1",ExecutorProvider.getProcessorInstance(NumberProcessor.class))
                .getParent().addExecutor("Processor2",ExecutorProvider.getProcessorInstance(NumberProcessor.class))
                .getParent().addExecutor("Processor3",ExecutorProvider.getProcessorInstance(NumberProcessor.class))
                .getParent().addExecutor("Processor4",ExecutorProvider.getProcessorInstance(NumberProcessor.class))
                .getParent().addExecutor("Processor5",ExecutorProvider.getProcessorInstance(NumberProcessor.class))
                .addExecutor("Writer5",ExecutorProvider.getWriterInstance(NumberWriter.class));

        System.out.println("Starting Job " + new Date());

        try {
            Future<?> f = DataLabVisualUtil.startVisualize(job);
            Thread.sleep(10000);
            f.get();
        } catch (Exception ex) {
            Logger.getLogger(VisualTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Job Ended " + new Date());

    }
    
  
    
    
}
