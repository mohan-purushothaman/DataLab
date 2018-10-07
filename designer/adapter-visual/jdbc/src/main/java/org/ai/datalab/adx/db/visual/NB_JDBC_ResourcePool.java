/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db.visual;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.pool2.BaseObjectPool;
import org.netbeans.api.db.explorer.ConnectionManager;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.netbeans.api.db.explorer.DatabaseException;
import org.openide.util.Exceptions;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.resource.ResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public final class NB_JDBC_ResourcePool implements ResourcePool<Connection> {

    private final String resourceId;
    private int maxCount;

    private transient BasicDataSource ds;

    public NB_JDBC_ResourcePool(String resourceId, int maxCount) throws Exception {
        this.resourceId = resourceId;
        this.maxCount = maxCount;
        init();
    }

    @Override
    public Resource<Connection> getResource() throws Exception {
        return new Resource(new BaseObjectPool<Connection>() {
            @Override
            public Connection borrowObject() throws Exception {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void returnObject(Connection obj) throws Exception {
                obj.close();
            }

            @Override
            public void invalidateObject(Connection obj) throws Exception {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }, ds.getConnection());
    }

    @Override
    public String getResourceId() {
        return resourceId;
    }

    @Override
    public int getNumActive() {
        return ds.getNumActive();
    }

    @Override
    public int getMaxCount() {
        return maxCount;
    }

    @Override
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        ds.setMaxTotal(this.maxCount);
    }

    @Override
    public void init() throws Exception{
        if (ds == null) {
            DatabaseConnection connection = ConnectionManager.getDefault().getConnection(resourceId);
            try {
                Driver driver = connection.getJDBCDriver().getDriver();
                DriverManager.registerDriver(driver);
                System.out.println(driver.getClass() + " loaded");

                ds = new BasicDataSource();
                ds.setUrl(connection.getDatabaseURL());
                ds.setUsername(connection.getUser());
                ds.setPassword(connection.getPassword());
                ds.setDriver(driver);
                ds.setMaxTotal(getMaxCount());
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
                ds = null;
                throw ex;
            }
        }
    }

    @Override
    public Class<Connection> getResourceClass() {
        return Connection.class;
    }

    @Override
    public String toString() {
        return resourceId ;
    }
    
    
    

}
