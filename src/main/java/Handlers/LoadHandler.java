package Handlers;

import DataAccess.DataAccessException;
import Request.LoadRequest;
import Result.LoadResult;
import Service.LoadService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if(httpExchange.getRequestMethod().toUpperCase().equals("POST")) {
                LoadRequest request = null;
                InputStream reqBody = httpExchange.getRequestBody();
                String reqData = readString(reqBody);
                request = (new JsonSerializer()).deserialize(reqData, LoadRequest.class);
                LoadResult result = (new LoadService()).load(request);
                if(result.isSuccess()) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                OutputStream respBody = httpExchange.getResponseBody();
                String out = (new JsonSerializer()).serialize(result);
                writeString(out, respBody);
                httpExchange.getResponseBody().close();
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
