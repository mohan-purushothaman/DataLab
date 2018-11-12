/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.Executor;
import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Reader;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.ValueMapper;
import org.ai.datalab.core.misc.SimpleData;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class DB_Reader extends DB_Provider {


    DB_Reader(ResourcePool<Connection> pool, String query, MappingHelper<String> mapping) {
        super(query, mapping, pool.getResourceId());
    }

    @Override
    public ExecutorType getProvidingType() {
        return ExecutorType.READER;
    }

    @Override
    public Executor getNewExecutor() {
        return new Reader() {

            private Resource<Connection> resource;
            private ResultSet set;
            private MappingHelper<String> mapping = getMapping();

            @Override
            public Data readData(ExecutionConfig config) throws Exception {

                if (set.next()) {
                    Data data = new SimpleData();

                    mapping.map(new ValueMapper<String>() {
                        @Override
                        public Object getValue(String id) throws Exception {
                            return set.getObject(id);
                        }
                    }, data);

                    return data;
                }

                return null;

            }

            @Override
            public void init(ExecutionConfig config) throws Exception {
                resource = ((ResourcePool<Connection>) config.getResourcePool()).getResource();

                Statement stmt = resource.get().createStatement();
                set = stmt.executeQuery(getQuery());

                //TODO provide this option as properties
                if (mapping == null) {
                    mapping = new MappingHelper<>();
                    ResultSetMetaData meta = set.getMetaData();
                    int len = meta.getColumnCount();
                    for (int i = 0; i < len; i++) {
                        String columnName = meta.getColumnName(i + 1);
                        mapping.addIdMap(columnName, columnName, null, null);
                    }
                }
            }

            @Override
            public void shutdown(ExecutionConfig config) throws Exception {
                if (resource != null) {
                    resource.close();
                }
            }

        };
    }


    @Override
    public boolean isMultiThreadingSupported() {
        return false;
    }
}
