/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.executor.impl;

import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.executor.Writer;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class SimpleWriter implements Writer {

    @Override
    public final void writeData(Data[] data,ExecutionConfig config) throws Exception {
        assert data.length == 1 : "expected length is 1 but found " + data.length;
        writeData(data[0],config);
    }

    public abstract void writeData(Data data,ExecutionConfig config) throws Exception;

    @Override
    public final int getMaximumBatchSize() {
        return 1;
    }

}
