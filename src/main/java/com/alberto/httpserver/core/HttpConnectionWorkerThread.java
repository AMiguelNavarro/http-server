package com.alberto.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/** Worker to manage connections in another thread */
public class HttpConnectionWorkerThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private static final String CRLF = "\n\r"; // 13, 10

    private final Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
            ) {

            int _byte;
            while ((_byte = inputStream.read()) >= 0) {
                System.out.print((char) _byte);
            }

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + getDefaultHTMLPage().getBytes().length + CRLF + // HEADER
                            CRLF +
                            getDefaultHTMLPage() +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());
            LOGGER.info("Connection processing finished");
        } catch (IOException e) {
            LOGGER.error("Error with communication", e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    LOGGER.error("Error closing socket", e);
                }
            }
        }
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
