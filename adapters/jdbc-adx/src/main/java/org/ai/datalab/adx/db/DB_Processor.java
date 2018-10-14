/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.sql.DataSource;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.Executor;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.ValueMapper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Processor;
import org.ai.datalab.core.executor.impl.SimpleUpdateProcessor;
import org.ai.datalab.core.misc.SimpleData;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class DB_Processor extends DB_Provider {


    public DB_Processor(ResourcePool<Connection> pool, String query, MappingHelper<String> mapping) {
        super(query,mapping, pool.getResourceId());
    }

    @Override
    public ExecutorType getProvidingType() {
        return ExecutorType.PROCESSOR;
    }

    @Override
    public Executor getNewExecutor() {
        return new SimpleUpdateProcessor() {
            private PreparedQuery pQuery;
            private final MappingHelper<String> mapping = getMapping();

            @Override
            public void init(ExecutionConfig config) throws Exception {
                this.pQuery = new PreparedQuery(getQuery());
            }

            @Override
            public void updateData(Data data, ExecutionConfig config) throws Exception {
                try (Resource<Connection> r = ((ResourcePool<Connection>) config.getResourcePool()).getResource()) {
                    try (PreparedStatement p = r.get().prepareStatement(pQuery.getPreparedQuery())) {

                        pQuery.populateParams(p, data);
                        boolean isSelect = p.execute();
                        if (mapping != null) {
                            if (isSelect) {
                                ResultSet set = p.getResultSet();
                                if (set.next()) {
                                    mapping.map(new ValueMapper<String>() {
                                        @Override
                                        public Object getValue(String id) throws Exception {
                                            return set.getObject(id);
                                        }
                                    }, data);

                                }
                            } else {
                                mapping.map(new ValueMapper<String>() {
                                    @Override
                                    public Object getValue(String id) throws Exception {
                                        return p.getUpdateCount();
                                    }
                                }, data);

                            }

                        }
                    };
                }
            }
        };
    }

   

}
