/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.file;

import java.io.BufferedWriter;
import java.io.File;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Writer;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
class FileWriter extends File_Provider {

    private final boolean append;

    FileWriter(ResourcePool<File> pool, boolean writeHeader, String outputFormat, int bufferSize, String quoteString, boolean append) {
        super(null, pool.getResourceId(), bufferSize, writeHeader, outputFormat, quoteString);
        this.append = append;
    }

    @Override
    public ExecutorType getProvidingType() {
        return ExecutorType.WRITER;
    }

    @Override
    public Writer getNewExecutor() {
        return new Writer() {
            private final String quoteString = getQuoteString();
            private Resource<File> resource;
            private BufferedWriter writer;

            @Override
            public void init(ExecutionConfig config) throws Exception {
                if (hasHeader()) {
                    resource = config.getResourcePool().getResource();
                    writer = new BufferedWriter(new java.io.FileWriter(resource.get(), append), getBufferSize());

                    StrSubstitutor substitutor = new StrSubstitutor(new StrLookup() {
                        @Override
                        public String lookup(String string) {
                            return quoteValue(string, quoteString);
                        }
                    });
                    writer.write(substitutor.replace(getLineFormat()) + System.lineSeparator());
                }

            }

            @Override
            public void shutdown(ExecutionConfig config) throws Exception {
                writer.close();
                resource.close();
            }

            @Override
            public void writeData(Data[] dataArr, ExecutionConfig config) throws Exception {
                for (Data data : dataArr) {
                    StrSubstitutor substitutor = new StrSubstitutor(new StrLookup() {
                        @Override
                        public String lookup(String string) {
                            return quoteValue(ObjectUtils.toString(data.getValue(string)), quoteString);
                        }
                    });
                    writer.write(substitutor.replace(getLineFormat()) + System.lineSeparator());
                }
                writer.flush();
            }

            @Override
            public int getMaximumBatchSize() {
                return 1000;
            }

        };
    }

    public static String quoteValue(String value, String quoteString) {
        assert value != null;

        return quoteString + value + quoteString;

    }
    

}
