/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java;

import java.net.URL;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.ai.datalab.core.adx.CodeGenerator;
import org.ai.datalab.core.adx.CodeSegment;
import org.ai.datalab.core.adx.CodeSegmentHandler;

/**
 *
 * @author Mohan Purushothaman
 */
public class JavaCodeGenerator implements CodeGenerator<String, String> {

    protected final Map<CodeSegment, String> pre = new EnumMap<>(CodeSegment.class);
    protected final Map<CodeSegment, String> post = new EnumMap<>(CodeSegment.class);

    protected final LinkedHashSet<CodeSegment> segmentOrder;

    protected final List<URL> libList = new LinkedList<>();

    protected final String clazzName;

    protected final CodeSegmentHandler<String> codeSegmentHandler;

    public JavaCodeGenerator(String clazzName, CodeSegment... segmentOrder) {
        this(clazzName, new JavaCodeSegmentHandler(), segmentOrder);
    }

    public JavaCodeGenerator(String clazzName, JavaCodeSegmentHandler segmentHandler, CodeSegment... segmentOrder) {
        this.clazzName = clazzName;
        this.segmentOrder = new LinkedHashSet<>();
        this.codeSegmentHandler = segmentHandler;
        this.segmentOrder.addAll(Arrays.asList(segmentOrder));
        for (CodeSegment codeSegment : CodeSegment.values()) {
            if (!this.segmentOrder.contains(codeSegment)) {
                this.segmentOrder.add(codeSegment);
            }
        }
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder(preferredCodeSize());
        for (CodeSegment codeSegment : segmentOrder) {
            sb.append(formatPreValue(pre.get(codeSegment)));
            sb.append(formatValue(codeSegmentHandler.getCodeSegment(codeSegment)));
            sb.append(formatPostValue(post.get(codeSegment)));
        }
        return sb.toString();
    }

    public final void setPreSection(CodeSegment codeSegment, String preSection) {
        pre.put(codeSegment, preSection);
    }

    public final void setPostSection(CodeSegment codeSegment, String postSection) {
        post.put(codeSegment, postSection);
    }

    public final String getPreSection(CodeSegment codeSegment) {
        return pre.get(codeSegment);
    }

    public final String getPostSection(CodeSegment codeSegment) {
        return post.get(codeSegment);
    }

    public Set<CodeSegment> getSegmentOrder() {
        return segmentOrder;
    }

    public int preferredCodeSize() {
        return 2048;
    }

    public List<URL> getLibList() {
        return libList;
    }

    public void addLibUrl(URL url) {
        libList.add(url);
    }

    public void deleteLibUrl(URL url) {
        libList.remove(url);
    }

    public String getClazzName() {
        return clazzName;
    }

    @Override
    public final CodeSegmentHandler<String> getCodeSegmentHandler() {
        return codeSegmentHandler;
    }

    public String formatValue(String s) {
        return (s == null ? "" : s);
    }

    protected String formatPreValue(String s) {
        return "\n" + (s == null ? "" : s) + "\n";
    }

    protected String formatPostValue(String s) {
        return "\n" + (s == null ? "" : s) + "\n";
    }

}
