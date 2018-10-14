/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web;

import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.executor.Reader;

/**
 *
 * @author Mohan Purushothaman
 */
public class SimpleNumberReader implements Reader{

    int i=0;
    
    @Override
    public Data readData(ExecutionConfig config) throws Exception {
        if(i>=WebTest.MAX){
            return null;
        }
        Data data=Data.newInstance();
        data.setValue("value1", null, i++);
        return data;
    }
    
}
