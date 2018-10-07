/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Reader;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
class FileReader extends File_Provider {


    FileReader(ResourcePool<File> pool, MappingHelper<String> mapping, boolean hasHeader, String dataFormat,int bufferSize,String quoteString) {
        super(mapping,pool.getResourceId(),bufferSize,hasHeader,dataFormat,quoteString);
    }

    @Override
    public ExecutorType getProvidingType() {
        return ExecutorType.READER;
    }

    @Override
    public Reader getNewExecutor() {
        return new Reader() {

            private Resource<File> resource;
            private LineIterator itr;
            private final MappingHelper mapping=getMapping();
            private final FilePatternParser parser = new FilePatternParser(getLineFormat(),getQuoteString());
            
            @Override
            public void init(ExecutionConfig config) throws Exception {
                resource=config.getResourcePool().getResource();
                itr = IOUtils.lineIterator(new BufferedReader(new InputStreamReader(new FileInputStream(resource.get())),getBufferSize()));

                if (hasHeader() && itr.hasNext()) {
                    itr.nextLine();
                }
            }
            @Override
            public Data readData(ExecutionConfig config) throws Exception {
                if (itr.hasNext()) {
                    return parser.parse(itr.nextLine(),mapping);
                }
                return null;

            }

           

            @Override
            public void shutdown(ExecutionConfig config) throws Exception {
                if (resource != null) {
                    resource.close();
                }
            }

        };
    }

  
  
}
