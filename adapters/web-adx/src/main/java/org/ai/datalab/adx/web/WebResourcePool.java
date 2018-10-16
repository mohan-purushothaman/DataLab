/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web;

import java.net.URL;
import org.ai.datalab.core.resource.AbstractResourcePool;

/**
 *
 * @author Mohan Purushothaman
 */
public class WebResourcePool extends AbstractResourcePool<URL>{

    private final String url;
    
    public WebResourcePool(String url, int maxCount, Class<URL> resourceClass) throws Exception {
        super(url, maxCount, resourceClass);
        this.url=url;
    }

    
    
    
    
    @Override
    protected URL createResource() throws Exception {
         return new URL(url);
    }

    @Override
    protected void releaseResource(URL resource) throws Exception {
         
    }
    
}
