/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db;

import java.sql.Connection;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.SingleMapping;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class DB_Adapter {

    public static AbstractExecutorProvider createReader(ResourcePool<Connection> pool, String query, MappingHelper<String> mapping, int fetchSize) {
        return new DB_Reader(pool, query, mapping, fetchSize);
    }

        public static AbstractExecutorProvider createDML_Processor(ResourcePool<Connection> pool, int batchSize, String query, MappingHelper mapping) {
        return new DML_Executor(pool, batchSize, query, mapping, ExecutorType.PROCESSOR);
    }

    public static AbstractExecutorProvider createGroupProcessor(ResourcePool<Connection> pool, int batchSize, String query, MappingHelper<String> mapping, String groupDataKey, String groupDbKey) {
        return new GroupedQueryExecutor(pool, query, mapping, groupDataKey, groupDbKey, batchSize);
    }

    public static AbstractExecutorProvider createWriter(ResourcePool<Connection> pool, int batchSize, String query) {
        return new DML_Executor(pool, batchSize, query, null, ExecutorType.WRITER);
    }
    
    public static ResourcePool<Connection> createJdbcResourcePool(String jdbcUrl, String userName, String pwd, String driverName, int maxResourceCount) throws Exception {
        DBResourcePool dbResourcePool = new DBResourcePool(jdbcUrl, userName, pwd, driverName, maxResourceCount);
        return dbResourcePool;
    }
    
}
