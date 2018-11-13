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
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.SingleMapping;
import org.ai.datalab.core.misc.Type;
import org.ai.datalab.core.misc.TypeUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public class SimpleJavaCodeGenerator extends JavaCodeGenerator {

    public SimpleJavaCodeGenerator(String clazzName, String extraImport, String method,MappingHelper mapping) {
        super(clazzName, IMPORT_DECLARATION, EXECUTE);

        setPreSection(IMPORT_DECLARATION, "package test;\n"
                + "import java.util.*;\n"
                + "import org.ai.datalab.core.Data;\n"
                + "import org.ai.datalab.core.ExecutionConfig;\n"
                + "" //TODO add standard libs
                + extraImport);

        setPostSection(IMPORT_DECLARATION, method);

        setPreSection(EXECUTE, generateDeclarations(mapping));

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

    private String generateDeclarations(MappingHelper mapping) {
        StringBuilder sb = new StringBuilder(300);
        for (Object o : mapping.getIdList(null)) {
            SingleMapping s=(SingleMapping) o;
            Type type=s.getConverter().getResultType();
            sb.append("final ").append(type.getClazz().getName()).append(" ").append(s.getFieldKey()).append("=(")
                    .append(type.getClazz().getName()).append(")data.getValue(\"").append(s.getFieldKey()).append("\");\n");
        }
        return sb.toString();
    }

}
