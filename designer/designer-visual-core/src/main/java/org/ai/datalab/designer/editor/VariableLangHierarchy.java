/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.editor;

import java.util.Collection;
import java.util.EnumSet;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author Mohan Purushothaman
 */

public class VariableLangHierarchy extends LanguageHierarchy<VarLangToken> {

    @Override
    protected Collection<VarLangToken> createTokenIds() {
        return EnumSet.allOf(VarLangToken.class);
    }

    @Override
    protected Lexer<VarLangToken> createLexer(LexerRestartInfo<VarLangToken> info) {
        return new VariableLangLexer(info);
    }

    @Override
    protected String mimeType() {
        return SimpleEditor.LANG_MIME_TYPE;
    }

}
