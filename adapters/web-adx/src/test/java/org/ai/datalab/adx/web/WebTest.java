/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.util.concurrent.Future;
import org.ai.datalab.core.DataJob;
import org.ai.datalab.core.ExecutorProvider;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.resource.ResourceFactory;
import org.ai.datalab.visual.DataLabVisualUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Mohan Purushothaman
 */
public class WebTest {

    private static TestServer server;
    private static int port;

    public static final int MAX = 100;

    public WebTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        server = new TestServer();
        server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        
        port = server.getListeningPort();
        
    }

    @AfterClass
    public static void tearDownClass() {
        server.stop();
    }

    @Test
    public void simpleTest() throws Exception {

        WebResourcePool pool1 = new WebResourcePool("http://localhost:" + port + "/process", 3, WebUrl.class);
        WebResourcePool pool2 = new WebResourcePool("http://localhost:" + port + "/write", 3, WebUrl.class);
        ResourceFactory.addResourcePool(pool1);
        ResourceFactory.addResourcePool(pool2);
        MappingHelper mapping=new MappingHelper();
        mapping.addIdMap(WebUtil.RESPONSE,"value2",null,"1.2");
        
        DataJob job = DataJob.getJob("testJob", null);
        job.setReader("dummy numbers", ExecutorProvider.getReaderInstance(SimpleNumberReader.class))
                .addExecutor("process through http", new HttpProcessor(null, null, HttpMethodType.POST, "${value1}", mapping, pool1.getResourceId())).setThreadCount(12)
                .addExecutor("write to http", new HttpWriter(null, null, HttpMethodType.POST, "${value1},${value2}", null, pool2.getResourceId())).setThreadCount(12);
        
        Future<?> f = DataLabVisualUtil.startVisualize(job);
        //Thread.sleep(10000);
        f.get();
        Assert.assertEquals("map should be empty", 0, server.getMap().size());
    }
}
