/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core;

import java.io.PrintWriter;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.core.misc.ConsoleJobListener;

/**
 *
 * @author Mohan Purushothaman
 */
public interface DataLabJobListener {

    
    public void jobStarting(ExecutionUnit executor,ExecutionConfig config);
    
    public default void initCompleted(){};

    public void jobEnded(ExecutionUnit executor);

    public void jobInterrupted(ExecutionUnit executor, int threadNo, Throwable t);

    public void updateProgress(ExecutionUnit executor, int threadNo, ExecutionResult result);

    public void updateErrorProgress(ExecutionUnit executor, int threadNo, Throwable t, Data... data);

    public void jobEnded();

    public void updateState(ExecutionUnit executor, int threadNo, JobState state);

    public void jobInterrupted(Throwable ex);


    public static final DataLabJobListener CONSOLE_PRINTER = new ConsoleJobListener();
    public static final DataLabJobListener EMPTY = new DataLabJobListener() {

        @Override
        public void jobStarting(ExecutionUnit executor,ExecutionConfig config) {
        }

        @Override
        public void jobEnded(ExecutionUnit executor) {
        }

        @Override
        public void jobInterrupted(ExecutionUnit executor, int threadNo, Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void updateProgress(ExecutionUnit executor, int threadNo, ExecutionResult result) {
        }

        @Override
        public void updateErrorProgress(ExecutionUnit executor,  int threadNo,Throwable t, Data... data) {
            t.printStackTrace();
        }

        @Override
        public void jobEnded() {
        }

        @Override
        public void updateState(ExecutionUnit executor, int threadNo, JobState state) {
        }

        @Override
        public void jobInterrupted(Throwable ex) {
            ex.printStackTrace();
        }

       
    };

}
