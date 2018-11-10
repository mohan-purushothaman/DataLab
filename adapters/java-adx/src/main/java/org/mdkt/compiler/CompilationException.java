package org.mdkt.compiler;

import javax.tools.DiagnosticCollector;

public class CompilationException extends Exception {

    private final DiagnosticCollector diagnostic;

    public CompilationException(DiagnosticCollector diagnostic, String msg) {
        super(msg);
        this.diagnostic = diagnostic;
    }

    public DiagnosticCollector getDiagnostics() {
        return diagnostic;
    }

}
