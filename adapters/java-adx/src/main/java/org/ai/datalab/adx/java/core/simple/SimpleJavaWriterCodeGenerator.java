/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.core.simple;

import org.ai.datalab.core.Data;

/**
 *
 * @author Mohan Purushothaman
 */
public class SimpleJavaWriterCodeGenerator extends SimpleJavaCodeGenerator {

    public SimpleJavaWriterCodeGenerator(Data sampleData) {
        super("test.SimpleJavaWriter", "import org.ai.datalab.core.executor.impl.SimpleWriter;", "public class SimpleJavaWriter extends SimpleWriter {\n"
                + "\n"
                + "    @Override\n"
                + "    public void writeData(Data data, ExecutionConfig config) throws Exception {\n", sampleData);

    }

}
