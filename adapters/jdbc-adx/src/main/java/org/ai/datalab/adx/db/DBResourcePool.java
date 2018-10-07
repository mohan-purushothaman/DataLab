/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db;

import java.sql.Connection;
import java.util.Collection;
import org.apache.commons.dbcp2.BasicDataSource;
import org.ai.datalab.core.resource.AbstractResourcePool;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
class DBResourcePool extends AbstractResourcePool<Connection> {

    private transient BasicDataSource ds;

    private final String jdbcUrl;
    private final String userName;
    private final String pwd;
    private final String driverName;

    public DBResourcePool(String jdbcUrl, String userName, String pwd, String driverName, int maxResourceCount) throws Exception {
        super(jdbcUrl + (userName == null ? "" : "(" + userName + ")"), maxResourceCount,Connection.class);
        this.jdbcUrl = jdbcUrl;
        this.userName = userName;
        this.pwd = pwd;
        this.driverName = driverName;
    }

    @Override
    public Connection createResource() throws Exception {
        return getDs().getConnection();
    }

    @Override
    public void releaseResource(Connection resource) throws Exception {
        resource.close();
    }

    @Override
    public void setMaxCount(int maxCount) {
        super.setMaxCount(maxCount);
        getDs().setMaxTotal(maxCount);
    }

    

    private BasicDataSource getDs() {
        if (ds == null) {
            ds = new BasicDataSource();
            ds.setUrl(jdbcUrl);
            ds.setUsername(userName);
            ds.setPassword(pwd);
            if (driverName != null) {
                ds.setDriverClassName(driverName);
            }
            ds.setMaxTotal(getMaxCount());
        }
        return ds;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUserName() {
        return userName;
    }
    
    
    
    

    public static Collection<ResourcePool> findResourcePools() {
        return ResourceFactory.findResourcePools(Connection.class,null);
    }

}
