/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.core.simple;

import java.util.Map.Entry;
import java.util.Set;
import org.ai.datalab.adx.java.JavaCodeGenerator;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.CodeSegment;
import static org.ai.datalab.core.adx.CodeSegment.*;
import org.ai.datalab.core.misc.Type;
import org.ai.datalab.core.misc.TypeUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public class SimpleJavaCodeGenerator extends JavaCodeGenerator {

    public SimpleJavaCodeGenerator(String clazzName, String extraImport, String method, Data sampleData) {
        super(clazzName, IMPORT_DECLARATION, EXECUTE);

        setPreSection(IMPORT_DECLARATION, "package test;\n"
                + "import java.util.*;\n"
                + "import org.ai.datalab.core.Data;\n"
                + "import org.ai.datalab.core.ExecutionConfig;\n"
                + "" //TODO add standard libs
                + extraImport);

        setPostSection(IMPORT_DECLARATION, method);

        setPreSection(EXECUTE, generateDeclarations(sampleData));

        getCodeSegmentHandler().setCodeSegment(EXECUTE, "//* Type content here*/");

        setPostSection(EXECUTE, "    \n\n\n\n/** Class end  */}}\n");
    }

    public String getSourceContentExcludingExecute() {
        StringBuilder sb = new StringBuilder(preferredCodeSize());
        for (CodeSegment codeSegment : segmentOrder) {
            sb.append(formatPreValue(pre.get(codeSegment)));
            if (EXECUTE != codeSegment) {
                sb.append(formatValue(codeSegmentHandler.getCodeSegment(codeSegment)));
            }
            sb.append(formatPostValue(post.get(codeSegment)));
        }
        return sb.toString();
    }

    public int findExecuteIndex(String content) {
        int lastIndexOf = content.lastIndexOf(getPostSection(EXECUTE));
        return lastIndexOf;
    }

    private String generateDeclarations(Data sampleData) {
        StringBuilder sb = new StringBuilder(300);
        for (Entry<String, Object> e : sampleData.getEntrySet()) {
            Type type = TypeUtil.detectType(e.getValue());

            sb.append("final ").append(type.getClazz().getName()).append(" ").append(e.getKey()).append("=(")
                    .append(type.getClazz().getName()).append(")data.getValue(\"").append(e.getKey()).append("\");\n");
        }
        return sb.toString();
    }

}
