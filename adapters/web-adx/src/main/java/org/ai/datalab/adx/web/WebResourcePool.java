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
public class WebResourcePool extends AbstractResourcePool<WebUrl>{

    private final String url;
    
    public WebResourcePool(String url, int maxCount, Class<WebUrl> resourceClass) throws Exception {
        super(url, maxCount, resourceClass);
        this.url=url;
    }

    
    
    
    
    @Override
    protected WebUrl createResource() throws Exception {
         return new WebUrl(url);
    }

    @Override
    protected void releaseResource(WebUrl resource) throws Exception {
         
    }
    
}
