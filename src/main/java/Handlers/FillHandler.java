package Handlers;

import DataAccess.DataAccessException;
import Request.FillRequest;
import Result.FillResult;
import Service.FillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if(httpExchange.getRequestMethod().toUpperCase().equals("POST")) {
                String urlPath = httpExchange.getRequestURI().toString();

                FillRequest r = null;
                StringBuilder sb = new StringBuilder(urlPath);
                String[] strings = sb.toString().split("/");
                if(strings.length == 3) {
                    r = new FillRequest(strings[2]);
                } else {
                    int gen = Integer.parseInt(strings[3]);
                    r = new FillRequest(strings[2], gen);
                }
                FillResult result = (new FillService()).fill(r);
                if(result.success) {
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
}
