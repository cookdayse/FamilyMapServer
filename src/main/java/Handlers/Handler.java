package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (!httpExchange.getRequestMethod().toUpperCase().equals("GET")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                httpExchange.getResponseBody().close();
            } else {
                String urlPath = httpExchange.getRequestURI().toString();
                if (urlPath.equals("/") || urlPath.equals("")) {
                    urlPath = "/index.html";
                }
                String filePath = "web" + urlPath;
                File file = new File(filePath);
                if (!file.exists()) {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    Files.copy(Paths.get("web/HTML/404.html"), respBody);
                    httpExchange.getResponseBody().close();
                } else {
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);
                    httpExchange.getResponseBody().close();
                }
            }
        } catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
            httpExchange.getResponseBody().close();
        }
    }
}
