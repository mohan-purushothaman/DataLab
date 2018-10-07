/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.misc;

import java.io.PrintWriter;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.DataLabJobListener;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.ExecutionResult;
import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.core.JobState;
import org.ai.datalab.core.builder.ExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public class ConsoleJobListener implements DataLabJobListener {



    @Override
    public void jobEnded(ExecutionUnit primitive) {
        System.out.println(primitive + " job ended");
    }

    @Override
    public void jobInterrupted(ExecutionUnit primitive, int threadNo, Throwable t) {
        System.out.println(primitive + " job interrupted " + t.getMessage());
        t.printStackTrace();
    }

    @Override
    public void jobStarting(ExecutionUnit primitive,ExecutionConfig config) {
        System.out.println(primitive + " job ended");
    }

    @Override
    public void updateProgress(ExecutionUnit primitive,int threadNo, ExecutionResult result) {
        System.out.println(primitive + "("+threadNo+") progressed " + result.getProcessedCount()+" and generated "+result.getOutputCount()+" in "+result.getProcessedNanoSeconds());
    }

    @Override
    public void jobEnded() {
        System.out.println("Job Execution Ended");
    }

    @Override
    public void jobInterrupted(Throwable ex) {
        ex.printStackTrace();
    }

    @Override
    public void updateState(ExecutionUnit primitive, int threadNo, JobState state) {
        System.out.println(primitive + "( " + threadNo + " ) Status updated to " + state);
    }

//    @Override
//    public PrintWriter getWriter() {
//        return new PrintWriter(System.out);
//    }
//
//    @Override
//    public PrintWriter getErrorWriter() {
//        return new PrintWriter(System.err);
//    }

    @Override
    public void updateErrorProgress(ExecutionUnit primitive, int threadNo, Throwable t, Data... data) {
        System.out.println(primitive + " " + t.getMessage() + "  " + data);
        t.printStackTrace();
    }

}
