package Handlers;

import DataAccess.DataAccessException;
import Result.ClearResult;
import Service.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if(httpExchange.getRequestMethod().toUpperCase().equals("POST")) {
                ClearResult result = (new ClearService()).clear();
                OutputStream respBody = httpExchange.getResponseBody();
                if(result.isSuccess()) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String out = (new JsonSerializer()).serialize(result);
                    writeString(out, respBody);
                } else {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String out = (new JsonSerializer()).serialize(result);
                    writeString(out, respBody);
                }
            } else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            }
            httpExchange.getResponseBody().close();
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
