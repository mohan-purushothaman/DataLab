/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.db;

import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.adx.misc.MappingHelper;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class DB_Provider extends AbstractExecutorProvider{

   private String query;

    public DB_Provider(String query, MappingHelper mapping, String resourceId) {
        super(mapping, resourceId);
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
   
   
    
}
