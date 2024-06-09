package com.alberto.httpserver;

import com.alberto.httpserver.config.Configuration;
import com.alberto.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Driver class for the http server
 */
public class HttpServer {

    private static final String JSON_PATH = "src/main/resources/http.json";
    private static final String CRLF = "\n\r"; // 13, 10

    public static void main(String[] args) {
        System.out.println("Server starting...");

        ConfigurationManager configManager = ConfigurationManager.getInstance();
        configManager.loadConfigFile(JSON_PATH);
        Configuration currentConfig = configManager.getCurrentConfig();

        System.out.println("Using port: " + currentConfig.getPort());
        System.out.println("Using web root: " + currentConfig.getWebRoot());

        try (ServerSocket serverSocket = new ServerSocket(currentConfig.getPort())) {
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream(); // Read request from browser
            OutputStream outputStream = socket.getOutputStream(); // Write response to browser

            // READ

            // WRITE
            String defaultHTMLPage = getDefaultHTMLPage();
            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                    "Content-Length: " + defaultHTMLPage.getBytes().length + CRLF + // HEADER
                    CRLF +
                    defaultHTMLPage +
                    CRLF + CRLF;

            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Server finished");
    }

    private static String getDefaultHTMLPage() {
        return "<html>" +
                "<head>" +
                "<title>Simple java HTTP server</title>" +
                "</head>" +
                "<body>This page was served using my simple java HTTP server</body>" +
                "</html>";
    }
}
