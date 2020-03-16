import Handlers.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class FamilyMapServer {
    private HttpServer server;
    private void startServer(int port) {
        try {
            server = HttpServer.create(
                    new InetSocketAddress(port),
                    12);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        registerHandlers(server);
        server.start();
        System.out.println("FamilyMapServer listening on port " + port);
    }

    private void registerHandlers(HttpServer server) {
        server.createContext("/", new Handler());
        server.createContext("/index.html", new Handler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
    }

    public static void main(String[] args) {
        String portNumber = args[0];
        int port = Integer.parseInt(portNumber);
        new FamilyMapServer().startServer(port);
    }
}
