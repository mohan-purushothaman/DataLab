/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.adx.web;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Mohan Purushothaman
 */
public class TestServer extends NanoHTTPD {

    private final Map<String, String> map = new ConcurrentHashMap<>();

    public TestServer() {
        super(findFreePort());
    }

    public Map<String, String> getMap() {
        return map;
    }

    @Override
    public Response serve(String uri, Method method, Map<String, String> headers, Map<String, String> parms, Map<String, String> files) {
        String data = files.get("postData");
        if (method == Method.POST) {

            if (uri.equals("/process")) {
                map.put(data, String.valueOf(Math.random()));
                return newFixedLengthResponse(Status.OK, MIME_PLAINTEXT, map.get(data));
            } else if (uri.equals("/write")) {
                String[] s = data.split(",");
                if (map.get(s[0]).equals(s[1])) {
                    map.remove(s[0]);
                }
                return newFixedLengthResponse("write done");
            }
        }
        throw new UnsupportedOperationException("not defined");
    }

    private static final int MIN_PORT_NUMBER = 49000;

    // the ports above 49151 are dynamic and/or private
    private static final int MAX_PORT_NUMBER = 49151;

    /**
     * Finds a free port between {@link #MIN_PORT_NUMBER} and
     * {@link #MAX_PORT_NUMBER}.
     *
     * @return a free port
     * @throw RuntimeException if a port could not be found
     */
    public static int findFreePort() {
        for (int i = MIN_PORT_NUMBER; i <= MAX_PORT_NUMBER; i++) {
            if (available(i)) {
                return i;
            }
        }
        throw new RuntimeException("Could not find an available port between "
                + MIN_PORT_NUMBER + " and " + MAX_PORT_NUMBER);
    }

    /**
     * Returns true if the specified port is available on this host.
     *
     * @param port the port to check
     * @return true if the port is available, false otherwise
     */
    private static boolean available(final int port) {
        ServerSocket serverSocket = null;
        DatagramSocket dataSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            dataSocket = new DatagramSocket(port);
            dataSocket.setReuseAddress(true);
            return true;
        } catch (final IOException e) {
            return false;
        } finally {
            if (dataSocket != null) {
                dataSocket.close();
            }
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
