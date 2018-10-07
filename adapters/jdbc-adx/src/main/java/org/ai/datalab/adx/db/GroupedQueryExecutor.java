/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.Executor;
import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.ValueMapper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Processor;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class GroupedQueryExecutor extends DB_Provider {

    private final int batchSize;
    private final String groupDataKey;
    private final String groupDbKey;

    GroupedQueryExecutor(ResourcePool<Connection> pool, String query, MappingHelper<String> mapping, String groupDataKey, String groupDbKey, int batchSize) {
        super(query,mapping,pool.getResourceId());
        this.batchSize = batchSize;
        this.groupDataKey = groupDataKey;
        this.groupDbKey = groupDbKey;
    }

    @Override
    public ExecutorType getProvidingType() {
        return ExecutorType.PROCESSOR;
    }

    @Override
    public Executor getNewExecutor() {
        return new GroupQueryExecutorInstance(getMapping(), getQuery(), groupDataKey, groupDbKey, getResourceID(), batchSize);
    }

   

}

class GroupQueryExecutorInstance implements Processor {

    private final MappingHelper<String> mapping;

    private final GroupedQuery query;
    private final int batchSize;

    public GroupQueryExecutorInstance(MappingHelper<String> mapping, String query, String groupDataKey, String groupDbKey, String poolId, int batchSize) {
        this.mapping = mapping;
        this.query = new GroupedQuery(query, groupDataKey, groupDbKey);
        this.batchSize = batchSize;
    }

    @Override
    public Data[] processData(Data[] data, ExecutionConfig config) throws Exception {

        try (Resource<Connection> r = ((ResourcePool<Connection>) config.getResourcePool()).getResource()) {
            Connection c = r.get();

            try (Statement s = c.createStatement()) {
                String q = query.getQuery(data);
                //System.out.println(q);
                ResultSet set = s.executeQuery(q);

                while (set.next()) {
                    query.addRow(set, mapping);
                }

            }
        }
        for (Data d : data) {
            ValueMapper<String> mapper = query.getValueMapper(d);
            mapping.map(mapper, d);
        }
        query.reset();
        return data;
    }

    @Override
    public void init(ExecutionConfig config) throws Exception {

    }

    @Override
    public void shutdown(ExecutionConfig config) throws Exception {

    }

    @Override
    public int getMaximumBatchSize() {
        return batchSize;
    }

}
