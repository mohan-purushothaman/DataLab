/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.test;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.ai.datalab.adx.java.JavaCodeGenerator;
import org.ai.datalab.adx.java.core.JavaExecutorProvider;
import org.ai.datalab.adx.java.core.simple.SimpleJavaProcessorCodeGenerator;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.CodeSegment;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.ValueConverter;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.Reader;
import org.ai.datalab.core.executor.impl.OneToOneDataProcessor;
import org.ai.datalab.core.misc.SimpleData;
import org.ai.datalab.core.misc.Type;

public class JavaCompileTest {

    public JavaCompileTest() {
    }

    @Test
    public void testExecutorWithLib() throws Exception {

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

    @Test
    public void testSimpleExecutor() throws Exception {

        MappingHelper mapping = new MappingHelper();
        StringBuilder sb = new StringBuilder();
        for (Type t : Type.values()) {
            mapping.addIdMap("", "_" + t.name(), t.getConverter(), t.getSampleValue());

            sb.append("if(Objects.equals(_").append(t.name()).append(",data.getValue(\"_").append(t.name()).append("\"))){")
                    .append("data.setValue(\"_").append(t.name()).append("\",\"").append(t.name()).append("\");}\n");

        }

        JavaCodeGenerator gen = new SimpleJavaProcessorCodeGenerator(mapping);
        gen.getCodeSegmentHandler().setCodeSegment(CodeSegment.EXECUTE, sb.toString());

        JavaExecutorProvider pro = new JavaExecutorProvider(ExecutorType.PROCESSOR, gen, null);

        try {
            OneToOneDataProcessor p = (OneToOneDataProcessor) pro.getNewExecutor();

            for (int i = 0; i < 10; i++) {
                Data d = new SimpleData();
                for (Type t : Type.values()) {
                    d.setValue("_" + t.name(), t.getSampleValue());
                }

                Data newData = p.process(d, null);
                for (Type t : Type.values()) {
                    Assert.assertEquals(newData.getValue("_" + t.name()), t.name());
                }
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
