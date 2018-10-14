/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web;

import java.util.Map;
import org.ai.datalab.core.AbstractExecutorProvider;
import org.ai.datalab.core.adx.misc.MappingHelper;

/**
 *
 * @author Mohan Purushothaman
 */
public abstract class WebProvider extends AbstractExecutorProvider {

    private final Map<String, String> header;
    private final Map<String, String> params;
    private final HttpMethodType requestType;
    private String requestBody;

    public WebProvider(Map<String, String> header, Map<String, String> params, HttpMethodType requestType, String requestBody, MappingHelper mapping, String resourceId) {
        super(mapping, resourceId);
        this.header = header;
        this.params = params;
        this.requestType = requestType;
        this.requestBody = requestBody;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public HttpMethodType getRequestType() {
        return requestType;
    }
}
