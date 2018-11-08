/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.core.adv;

import org.ai.datalab.adx.java.JavaCodeGenerator;
import org.ai.datalab.core.adx.CodeSegment;
import static org.ai.datalab.core.adx.CodeSegment.EXECUTE;
import static org.ai.datalab.core.adx.CodeSegment.EXECUTOR_INIT;
import static org.ai.datalab.core.adx.CodeSegment.EXECUTOR_SHUTDOWN;
import static org.ai.datalab.core.adx.CodeSegment.EXTRA_SEGMENT_1;
import static org.ai.datalab.core.adx.CodeSegment.IMPORT_DECLARATION;
import static org.ai.datalab.core.adx.CodeSegment.VARIABLE_DECLARATION;

/**
 *
 * @author Mohan Purushothaman
 */
public class AdvancedJavaGenerator extends JavaCodeGenerator {

    public AdvancedJavaGenerator(String extraImport, String classHeader, String executeMethod, String clazzName, CodeSegment... segmentOrder) {
        super(clazzName, segmentOrder);
        setPreSection(IMPORT_DECLARATION, "package test;\n"
                + "/** <editor-fold defaultstate=\"collapsed\" desc=\"Imports\">*/");
        getCodeSegmentHandler().setCodeSegment(IMPORT_DECLARATION, "\nimport org.ai.datalab.core.Data;\n"
                + "import org.ai.datalab.core.ExecutionConfig;\n" + extraImport);
        setPostSection(IMPORT_DECLARATION, "/** </editor-fold> */");
        setPreSection(VARIABLE_DECLARATION, classHeader
                + "\n/** <editor-fold defaultstate=\"collapsed\" desc=\"Variables\">*/");
        setPostSection(VARIABLE_DECLARATION, "/**</editor-fold>*/");
        setPreSection(EXECUTOR_INIT, "/**<editor-fold defaultstate=\"collapsed\" desc=\"Init\">*/"
                + "\n    @Override\n"
                + "    public void init(ExecutionConfig config) throws Exception {");
        setPostSection(EXECUTOR_INIT, "  }// /**</editor-fold>*/");

        setPreSection(EXECUTOR_SHUTDOWN, "/**<editor-fold defaultstate=\"collapsed\" desc=\"Shutdown\">*/"
                + "\n    public void shutdown(ExecutionConfig config) throws Exception {");
        setPostSection(EXECUTOR_SHUTDOWN, "}/** </editor-fold>*/");
        setPreSection(EXECUTE, " /**\n"
                + "     * EXECUTE START\n"
                + "     */\n"
                + "    @Override\n"
                + executeMethod);
        setPostSection(EXECUTE, "\n}\n"
                + "\n"
                + "    /**\n"
                + "     * EXECUTE END\n"
                + "     */");

        setPostSection(EXTRA_SEGMENT_1, "}");
    }

}
