/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.core.adx.misc;

/**
 *
 * @author Mohan Purushothaman
 * @param <ID>
 */
public interface ValueMapper<ID> {
    public Object[] getValue(ID id) throws Exception;
}
