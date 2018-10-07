/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.file;

import java.io.File;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class FileAdapter {
      public static AbstractExecutorProvider createReader(ResourcePool<File> pool, MappingHelper<String> mapping, boolean hasHeader, String dataFormat, int bufferSize,String quoteString) {
        return new FileReader(pool, mapping, hasHeader, dataFormat, bufferSize,quoteString);
    }

   

    public static AbstractExecutorProvider createWriter(ResourcePool<File> pool, MappingHelper<String> mapping, boolean writeHeader, String outputFormat, int bufferSize, String quoteString, boolean append) {
        return new FileWriter(pool, writeHeader, outputFormat, bufferSize, quoteString,append);
    }
}
