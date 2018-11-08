/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.test;

import org.ai.datalab.adx.java.core.adv.JavaReaderCodeGenerator;
import org.ai.datalab.adx.java.core.*;
import static org.ai.datalab.core.adx.CodeSegment.*;

/**
 *
 * @author Mohan Purushothaman
 */
public class SimpleExecutorGenerator extends JavaReaderCodeGenerator {

    public SimpleExecutorGenerator() {
        super();
        
        
        getCodeSegmentHandler().setCodeSegment(IMPORT_DECLARATION, "import org.apache.commons.lang3.StringUtils;\n"
                + getCodeSegmentHandler().getCodeSegment(IMPORT_DECLARATION));

        getCodeSegmentHandler().setCodeSegment(VARIABLE_DECLARATION, "    private final int max = 1000;\n"
                + "\n"
                + "    private int start;");

        getCodeSegmentHandler().setCodeSegment(EXECUTOR_INIT, " start = 1;");

        getCodeSegmentHandler().setCodeSegment(EXECUTOR_SHUTDOWN, "System.out.println(\"ending\");");

        getCodeSegmentHandler().setCodeSegment(EXECUTE, " if (start > max) {\n"
                + "            return null;\n"
                + "        }\n"
                + "        Data data = Data.newInstance();\n"
                + "        data.setValue(\"test\", null, (start = start + 2));\n"
                + "        return data;");

    }

}
