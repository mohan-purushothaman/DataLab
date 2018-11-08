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
public class SimpleJavaProcessorCodeGenerator extends SimpleJavaCodeGenerator {

    public SimpleJavaProcessorCodeGenerator(Data sampleData) {
        super("test.SimpleJavaProcessor", "import org.ai.datalab.core.executor.impl.SimpleUpdateProcessor;", "public class SimpleJavaProcessor extends SimpleUpdateProcessor {\n"
                + "    @Override\n"
                + "    public void updateData(Data data, ExecutionConfig config) throws Exception {\n", sampleData);

    }

}
