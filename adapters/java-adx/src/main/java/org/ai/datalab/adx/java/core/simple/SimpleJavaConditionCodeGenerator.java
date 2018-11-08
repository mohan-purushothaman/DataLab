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
public class SimpleJavaConditionCodeGenerator extends SimpleJavaCodeGenerator {

    public SimpleJavaConditionCodeGenerator(Data sampleData) {
        super("test.SimpleJavaCondition", "import org.ai.datalab.core.executor.Condition;", "public class SimpleJavaCondition implements Condition{\n"
                + "\n"
                + "    @Override\n"
                + "    public boolean checkCondition(Data data, ExecutionConfig config) throws Exception {\n", sampleData);

    }

}
