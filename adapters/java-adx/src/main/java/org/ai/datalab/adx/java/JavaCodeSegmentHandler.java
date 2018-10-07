/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import org.ai.datalab.core.adx.CodeSegment;
import org.ai.datalab.core.adx.impl.AbstractCodeSegmentHandler;

/**
 *
 * @author Mohan Purushothaman
 */
public class JavaCodeSegmentHandler extends AbstractCodeSegmentHandler<String> {

    private final Set<CodeSegment> disabledSegments;

    public JavaCodeSegmentHandler() {
        disabledSegments = null;
    }

    public JavaCodeSegmentHandler(CodeSegment... disabledSegments) {
        this.disabledSegments = EnumSet.noneOf(CodeSegment.class);
        this.disabledSegments.addAll(Arrays.asList(disabledSegments));
    }

    @Override
    public boolean isEnabled(CodeSegment segment) {
        return disabledSegments == null || !disabledSegments.contains(segment);
    }

}
