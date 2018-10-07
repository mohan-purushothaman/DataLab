/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.core;

import static org.ai.datalab.core.adx.CodeSegment.*;

/**
 *
 * @author Mohan Purushothaman
 */
public class JavaReaderCodeGenerator extends ExecutorJavaGenerator {

    public JavaReaderCodeGenerator() {
        super("import org.ai.datalab.core.executor.Reader;",
                "public class JavaReader implements Reader {",
                "    public Data readData(ExecutionConfig config) throws Exception {",
                "test.JavaReader",
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
