/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.editor;

import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author Mohan Purushothaman
 */
public class VariableLangLexer implements Lexer<VarLangToken> {

    private final LexerRestartInfo<VarLangToken> info;

    public VariableLangLexer(LexerRestartInfo<VarLangToken> info) {
        this.info = info;
    }

    @Override
    public Token<VarLangToken> nextToken() {

        switch (info.input().read()) {
            case LexerInput.EOF: {
                return null;
            }
            case '$': {
                return detectVariable(info.input());
            }
            default:
                return createDummyToken(info.input());
        }
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }

    private Token<VarLangToken> detectVariable(LexerInput input) {
        if (input.read() == '{') {

            if (Character.isJavaIdentifierStart(input.read())) {
                int c = 0;
                int length = 0;
                while (length < SimpleEditor.MAX_VARIABLE_LENGTH && Character.isJavaIdentifierPart((c = input.read()))) {
                    length++;
                }
                if (c == '}') {
                    return info.tokenFactory().createToken(VarLangToken.VARIABLE);
                }
            }
        }
        return info.tokenFactory().createToken(VarLangToken.EVERY_THING_ELSE);
    }

    private Token<VarLangToken> createDummyToken(LexerInput input) {
        return info.tokenFactory().createToken(VarLangToken.EVERY_THING_ELSE);
    }

}
