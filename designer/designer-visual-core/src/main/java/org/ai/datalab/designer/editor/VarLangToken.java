/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.editor;

import org.netbeans.api.lexer.TokenId;

/**
 *
 * @author Mohan Purushothaman
 */
public enum VarLangToken implements TokenId{

    VARIABLE("VarIdentifier"),
    
    EVERY_THING_ELSE("default");
    
    private final String category;

    private VarLangToken(String category) {
        this.category = category;
    }
    
    
 
    @Override
    public String primaryCategory() {
        return category;
    }
    
}
