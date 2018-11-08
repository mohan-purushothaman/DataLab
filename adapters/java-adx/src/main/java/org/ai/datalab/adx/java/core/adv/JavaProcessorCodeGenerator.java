/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.core.adv;

import static org.ai.datalab.core.adx.CodeSegment.*;

/**
 *
 * @author Mohan Purushothaman
 */
public class JavaProcessorCodeGenerator extends AdvancedJavaGenerator {

    public JavaProcessorCodeGenerator() {
        super("import org.ai.datalab.core.executor.impl.OneToOneDataProcessor;",
                "public class JavaProcessor extends OneToOneDataProcessor {",
                "    public Data process(Data data,ExecutionConfig config) throws Exception {",
                "test.JavaProcessor",
                IMPORT_DECLARATION,
                VARIABLE_DECLARATION,
                EXECUTOR_INIT,
                EXECUTOR_SHUTDOWN,
                EXECUTE,
                EXTRA_SEGMENT_1,
                EXTRA_SEGMENT_2,
                EXTRA_SEGMENT_3);

    }

}
