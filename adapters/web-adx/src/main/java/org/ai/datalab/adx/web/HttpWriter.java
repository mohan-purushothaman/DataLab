/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web;

import java.util.Map;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.ExecutionConfig;
import org.ai.datalab.core.Executor;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.executor.ExecutorType;
import org.ai.datalab.core.executor.impl.SimpleWriter;
import org.ai.datalab.core.resource.Resource;

/**
 *
 * @author Mohan Purushothaman
 */
public class HttpWriter extends WebProvider {

    public HttpWriter(Map<String, String> header, Map<String, String> params, HttpMethodType requestType, String requestBody, MappingHelper mapping, String resourceId) {
        super(header, params, requestType, requestBody, mapping, resourceId);
    }

    @Override
    public ExecutorType getProvidingType() {
        return ExecutorType.WRITER;
    }

    @Override
    public Executor getNewExecutor() {
        return new SimpleWriter() {
            @Override
            public void writeData(Data data, ExecutionConfig config) throws Exception {
                WebResourcePool pool = (WebResourcePool) config.getResourcePool();
                try (Resource<WebUrl> resource = pool.getResource()) {
                    WebUtil.getWebResponse(resource.get().getUrl(), getHeader(), getParams(), getRequestType(), getRequestBody(), data, null);
                }
            }
        };
    }

}
