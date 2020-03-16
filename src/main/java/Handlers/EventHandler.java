package Handlers;

import DataAccess.DataAccessException;
import Result.EventResult;
import Result.EventResults;
import Service.EventService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if(httpExchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers reqHeaders = httpExchange.getRequestHeaders();
                if(reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    String urlPath = httpExchange.getRequestURI().toString();
                    String out = null;
                    if(urlPath.equals("/event")) {
                        EventResults result = (new EventService()).event(authToken);
                        out = (new JsonSerializer()).serialize(result);
                        if(result.isSuccess()) {
                            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        } else {
                            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                    } else {
                        StringBuilder sb = new StringBuilder(urlPath);
                        String[] strings = sb.toString().split("/");
                        EventResult result = (new EventService()).event(strings[2], authToken);
                        out = (new JsonSerializer()).serialize(result);
                        if(result.isSuccess()) {
                            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                        } else {
                            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                    }
                    OutputStream respBody = httpExchange.getResponseBody();
                    writeString(out, respBody);
                    httpExchange.getResponseBody().close();
                }
            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
            httpExchange.getResponseBody().close();
        } catch (DataAccessException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
            httpExchange.getResponseBody().close();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
