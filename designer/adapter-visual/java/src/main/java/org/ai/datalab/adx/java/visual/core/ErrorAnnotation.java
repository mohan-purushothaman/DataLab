/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.java.visual.core;

import javax.tools.Diagnostic;
import org.openide.text.Annotation;

/**
 *
 * @author Mohan Purushothaman
 */
public class ErrorAnnotation extends Annotation {

    private final Diagnostic diagnostic;
    private final String desc;

    public ErrorAnnotation(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
        this.desc = diagnostic.getMessage(null) + " : " + diagnostic.getCode();

    }

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    @Override
    public String getAnnotationType() {
        return "org-netbeans-spi-editor-hints-parser_annotation_err";
    }

    @Override
    public String getShortDescription() {
        return desc;
    }

}
