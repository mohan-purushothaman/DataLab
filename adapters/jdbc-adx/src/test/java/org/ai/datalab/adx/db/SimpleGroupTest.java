package org.ai.datalab.adx.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.ai.datalab.core.DataJob;
import org.ai.datalab.core.resource.Resource;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.visual.DataLabVisualUtil;
import org.ai.datalab.core.resource.ResourcePool;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mohan Purushothaman
 */
public class SimpleGroupTest {
    public SimpleGroupTest() {
                org.hsqldb.jdbcDriver d;

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void test() throws Exception {
        System.out.println("Initiaiting Job " + new Date());

        DataJob job = DataJob.getJob("testJob ", null);

        ResourcePool<Connection> p = DB_Adapter.createJdbcResourcePool("jdbc:hsqldb:mem:test_db" + new Random().nextInt(100000), null, null, "org.hsqldb.jdbcDriver",50);

        try (Resource<Connection> r = p.getResource()) {
            Connection c = r.get();
            try (Statement s = c.createStatement()) {
                s.execute("CREATE table testTable( i int, j int,x varchar(20))");
                s.execute("create index testIndex_i on testTable(i)");
            }
            int noOfRuns = 10;
            int batchSize = 1000;

            try (PreparedStatement pstmt = c.prepareStatement("insert into testTable values(?,1,1)")) {
                for (int i = 0; i < noOfRuns; i++) {
                    for (int j = 0; j < batchSize; j++) {
                        pstmt.setInt(1, i * batchSize + j);
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
            }

        }

        ResourceFactory.addResourcePool(p);
        MappingHelper<String> m = new MappingHelper<>();
        m.addIdMap("test", "X",null,null);
        job.setReader("Reader", DB_Adapter.createReader(p, "select I,J from testTable", null,1000))
                .addExecutor("Testing group select", DB_Adapter.createGroupProcessor(p, 1000, "select concat(i,'000') as test,i from testTable where i in ($[I(  ,)])", m, "I", "I")).setThreadCount(20)
                .addExecutor("Test pro", DB_Adapter.createDML_Processor(p, 1000, "update testTable set j=${I}, x=${X} where i=${I}", null)) 
                //.setThreadCount(20).getParent().addExecutor("Testing", DB_Adapter.createDML_Processor(p, 1000, "update testTable set j=100 where i>97000", null))
                ;

        System.out.println("Starting Job " + new Date());

        try {
            DataLabVisualUtil.visualize(job);
        } catch (Exception ex) {
            Logger.getLogger(SimpleDBTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (Resource<Connection> r = p.getResource()) {
            Connection c = r.get();

            ResultSet rs = c.createStatement().executeQuery("select i,j,x from testTable");

            while (rs.next()) {
                int i = rs.getInt("i");
                int j = rs.getInt("j");
                String x=rs.getString("x");
                
                //System.out.println(i + "-" + j+ "-"+x);
                assert i == j;
            }

        }
        System.out.println("Job Ended " + new Date());
    }
}
