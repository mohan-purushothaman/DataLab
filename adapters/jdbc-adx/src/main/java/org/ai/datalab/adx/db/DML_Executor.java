/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.Executor;
import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.adx.misc.SingleMapping;
import org.ai.datalab.core.adx.misc.ValueMapper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Processor;
import org.ai.datalab.core.executor.Writer;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class DML_Executor extends DB_Provider {

    private final int batchSize;

    private final ExecutorType type;

    DML_Executor(ResourcePool<Connection> pool, int batchSize, String query, MappingHelper mapping, ExecutorType type) {
        super(query,mapping,pool.getResourceId());
        this.batchSize = batchSize;
        this.type = type;
    }

    @Override
    public ExecutorType getProvidingType() {
        return type;
    }

    @Override
    public Executor getNewExecutor() {
        return new DML_Executor_Instance(batchSize, getQuery(), getMapping());
    }
}

class SimpleUpdateMapper implements ValueMapper {   

    private int index;
    private final int[] updateCount;

    public SimpleUpdateMapper(int[] updateCount) {
        this.updateCount = updateCount;
        index = 0;
    }

    @Override
    public Object[] getValue(Object id) throws Exception {
        return new Object[]{updateCount[index++]};
    }

}

class DML_Executor_Instance implements Processor, Writer {

    private final int batchSize;
    private final MappingHelper mapping;
    private final PreparedQuery batchQuery;

    public DML_Executor_Instance(int batchSize, String query,  MappingHelper mapping) {
        this.batchSize = batchSize;
        this.batchQuery = new PreparedQuery(query);
        this.mapping = mapping;
    }

    @Override
    public Data[] processData(Data[] data, ExecutionConfig config) throws Exception {
        writeData(data, config, true);
        return data;
    }

    @Override
    public int getMaximumBatchSize() {
        return batchSize;
    }

    public void writeData(Data[] data, ExecutionConfig config, boolean isProcessor) throws Exception {
        try (Resource<Connection> r = ((ResourcePool<Connection>) config.getResourcePool()).getResource()) {
            try (PreparedStatement p = r.get().prepareStatement(batchQuery.getPreparedQuery())) {
                for (Data d : data) {
                    batchQuery.populateParams(p, d);
                    p.addBatch();
                }
                int[] updateCount = p.executeBatch();
                if (isProcessor&& mapping!=null) {
                    SimpleUpdateMapper update = new SimpleUpdateMapper(updateCount);
                    for (Data d : data) { 
                        mapping.map(update, d);
                    }
                }
            }
        }
    }

    @Override
    public void writeData(Data[] data, ExecutionConfig config) throws Exception {
        writeData(data, config, false);

    }

}
