/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class VariableHighlighterListener {
    
    
    private int start,end;
    
    public void fireVariableChange(String variable, int start,int end){
        if(this.start==start && this.end==end){
            return;
        }
        this.start=start;
        this.end=end;
        for (char c : variable.toCharArray()) {
            if(!Character.isJavaIdentifierPart(c)){
                return;
            }
        }
        
        variableHighlighted(variable);
    }
    
    protected abstract void variableHighlighted(String variable);
    
    public void fireReset(){
        start=end=-1;
        reset();
    }
    
    protected abstract void reset();
}
