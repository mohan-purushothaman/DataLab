/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.test;

import java.net.URL;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;
import org.ai.datalab.adx.java.JavaCodeGenerator;
import org.ai.datalab.adx.java.core.JavaExecutorProvider;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Reader;

public class JavaCompileTest {

    public JavaCompileTest() {
    }

    @Test
    public void testSimpleExecutor() throws Exception {
        
        URL lib = Paths.get("src", "test", "resources", "commons-lang3-3.8.1.jar").toUri().toURL();

        checkStringUtilsClass();

        JavaCodeGenerator gen = new SimpleExecutorGenerator();
        gen.addLibUrl(lib);

        JavaExecutorProvider pro = new JavaExecutorProvider(ExecutorType.READER, gen, null);

        try {
            Reader reader = (Reader) pro.getNewExecutor();
            Data d;
            int i = 0;
            while ((d = reader.readData(null)) != null) {
                Assert.assertEquals((i = i + 2), ((int) d.getValue("test")));
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    private static void checkStringUtilsClass() {
        String clazzName = "org.apache.commons.lang3.StringUtils";
        try {
            Class.forName(clazzName);

        } catch (ClassNotFoundException ex) {
            System.out.println(clazzName + " not loaded in classpath, success");

            return;
        }
        Assert.fail(clazzName + " loaded, failing");
    }

}
