/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web;

import org.ai.datalab.core.resource.AbstractResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class WebResourcePool extends AbstractResourcePool<String>{

    private final String url;
    
    public WebResourcePool(String url, int maxCount, Class<String> resourceClass) throws Exception {
        super(url, maxCount, resourceClass);
        this.url=url;
    }

    
    
    
    
    @Override
    protected String createResource() throws Exception {
         return url;
    }

    @Override
    protected void releaseResource(String resource) throws Exception {
         
    }
    
}
