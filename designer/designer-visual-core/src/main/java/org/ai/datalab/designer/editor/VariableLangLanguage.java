/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.editor;

import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;

/**
 *
 * @author Mohan Purushothaman
 */
@LanguageRegistration(mimeType = SimpleEditor.LANG_MIME_TYPE)
public class VariableLangLanguage extends DefaultLanguageConfig {

    @Override
    public Language getLexerLanguage() {
        return new VariableLangHierarchy().language();
    }

    @Override
    public String getDisplayName() {
        return "DataLabVariable Language";
    }
    
}
