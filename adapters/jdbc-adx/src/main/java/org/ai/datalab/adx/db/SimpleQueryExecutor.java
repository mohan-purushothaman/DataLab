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
import org.ai.datalab.core.adx.misc.SingleMapping;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.impl.OneToManyDataProcessor;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class SimpleQueryExecutor extends DB_Provider {


    SimpleQueryExecutor(ResourcePool<Connection> pool, String query, MappingHelper<String> mapping) {
        super(query,mapping,pool.getResourceId());
    }

    @Override
    public ExecutorType getProvidingType() {
        return ExecutorType.PROCESSOR;
    }

    @Override
    public Executor getNewExecutor() {
        return new QueryExecutorInstance(getMapping(), getQuery(), getResourceID());
    }

}

class QueryExecutorInstance extends OneToManyDataProcessor {

    private final MappingHelper<String> mapping;

    private final StringSubstituteQuery query;

    public QueryExecutorInstance(MappingHelper<String> mapping, String query, String poolId) {
        this.mapping = mapping;
        this.query = new StringSubstituteQuery(query);
    }

    @Override
    public Data[] processData(Data data, ExecutionConfig config) throws Exception {
        GroupingHelper<String> helper = new GroupingHelper<>();
        try (Resource<Connection> r = ((ResourcePool<Connection>) config.getResourcePool()).getResource()) {
            Connection c = r.get();

            try (Statement s = c.createStatement()) {

                ResultSet set = s.executeQuery(query.getQuery(data));
                while (set.next()) {
                    for (SingleMapping<String> smap : mapping.getIdList(null)) {
                        String adapterID = smap.getAdapterID();
                        helper.addValue(adapterID, set.getObject(adapterID));
                    }
                }
            }
        }
        mapping.map(helper, data);
        return null;
    }

    @Override
    public void init(ExecutionConfig config) throws Exception {

    }

    @Override
    public void shutdown(ExecutionConfig config) throws Exception {

    }

}
