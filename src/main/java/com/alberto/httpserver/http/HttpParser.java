package com.alberto.httpserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    // https://www.ascii-code.com/
    private static final int SP = 0x20; // 32 hexadecimal
    private static final int CR = 0x0D; // 13 hexadecimal
    private static final int LF = 0x0A; // 10 hexadecimal

    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        // Bridge bytes to character streams. Read bytes from input stream and decode its into specific charset
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        HttpRequest request = new HttpRequest();

        try {
            parseStartLine(reader, request);
            parseHeaders(reader, request);
            parseBody(reader, request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return request;
    }

    private void parseStartLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        StringBuilder builder = new StringBuilder();

        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        int _byte;
        while ((_byte = reader.read()) >= 0) {
            if (CR == _byte) { // We can compare with == because it returns char and all chars are int
                _byte = reader.read();
                if (LF == _byte) {
                    LOGGER.info("Request line HTTP VERSION to process: {}", builder.toString());
                    if (!methodParsed || !requestTargetParsed) {
                        throw new HttpParsingException(HttpStatusCode.BAD_REQUEST);
                    }
                    return;
                }
            }

            if (SP == _byte) {
                // PRocess previous data
                if (!methodParsed) {
                    LOGGER.info("Request line METHOD to process: {}", builder.toString());
                    request.setMethod(builder.toString());
                    methodParsed = true;
                } else if (!requestTargetParsed) {
                    LOGGER.info("Request line REQ TARGET to process: {}", builder.toString());
                    requestTargetParsed = true;
                } else {
                    throw new HttpParsingException(HttpStatusCode.BAD_REQUEST);
                }
                builder.delete(0, builder.length());
            } else {
                // Otherwise store that char
                builder.append((char) _byte);
                if (!methodParsed) {
                    if (builder.length() > HttpMethod.MAX_LENGTH) {
                        throw new HttpParsingException(HttpStatusCode.NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }

    private void parseHeaders(InputStreamReader reader, HttpRequest request) {

    }

    private void parseBody(InputStreamReader reader, HttpRequest request) {

    }
}
