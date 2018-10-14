/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.ai.datalab.core.Data;
import org.ai.datalab.core.adx.misc.MappingHelper;
import org.ai.datalab.core.adx.misc.ValueMapper;
import org.ai.datalab.core.misc.DataUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 *
 * @author Mohan Purushothaman
 */
public class WebUtil {

    public final static String RESPONSE = "response";
    public final static String STATUS_CODE = "status_code";

    //TODO based on mapping , for writer do not read response
    public static Map<String, Object> executeMetod(HttpMethod method) throws IOException {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put(STATUS_CODE, new HttpClient().executeMethod(method));
            map.put(RESPONSE, method.getResponseBodyAsString());
            return map;
        } finally {
            method.releaseConnection();
        }
    }

    //TODO remove netbeans external dependency, use latest httpclient
    public static void getWebResponse(String url, Map<String, String> header, Map<String, String> params, HttpMethodType requestType, String requestBody, Data data, MappingHelper mapping) throws Exception {
        HttpMethod method = getMethod(requestType, url, requestBody, data);

        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                method.setRequestHeader(entry.getKey(), DataUtil.substituteData(entry.getValue(), data));
            }
        }

        HttpMethodParams param = new HttpMethodParams();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                param.setParameter(entry.getKey(), DataUtil.substituteData(entry.getValue(), data));
            }
        }
        method.setParams(param);

        Map<String, Object> result = executeMetod(method);
        if (mapping != null) {
            mapping.map(new ValueMapper<String>() {
                @Override
                public Object getValue(String id) throws Exception {
                    return result.get(id);
                }
            }, data);
        }
    }

    private static HttpMethod getMethod(HttpMethodType methodType, String url, String requestXml, Data data) throws Exception {
        switch (methodType) {
            case GET:
                return new GetMethod(DataUtil.substituteData(url, data));

            case POST:
                PostMethod m = new PostMethod(DataUtil.substituteData(url, data));
                if (requestXml != null && !requestXml.isEmpty()) {
                    m.setRequestEntity(new StringRequestEntity(DataUtil.substituteData(requestXml, data)));
                }
                return m;
        }
        throw new Exception(methodType + " is not found");
    }

}
