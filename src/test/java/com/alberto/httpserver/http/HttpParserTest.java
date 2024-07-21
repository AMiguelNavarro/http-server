package com.alberto.httpserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class HttpParserTest {

    private static HttpParser httpParser;

    @BeforeAll
    public static void beforeClass() {
        httpParser = new HttpParser();
    }

    @Test
    void parseValidHTTPRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateValidHttpGETRequest());
        } catch (HttpParsingException e) {
            fail(e);
        }
        assertEquals(request.getMethod(), HttpMethod.GET);
    }

    @Test
    void parseInvalidHTTPRequest() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidHttpGETRequest());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getHttpStatusCode(), HttpStatusCode.NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseInvalidHTTPRequestWithHTTPMethodMaxLengthExceeded() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidHttpGETRequestWithMaxLengthExceeded());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getHttpStatusCode(), HttpStatusCode.NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseInvalidHTTPRequestWithInvalidItemsNumber() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidHttpGETRequestInvalidItemsNumber());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getHttpStatusCode(), HttpStatusCode.BAD_REQUEST);
        }
    }

    @Test
    void parseInvalidHTTPRequestWithEmptyRequestLine() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidHttpGETRequestEmptyRequestLine());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getHttpStatusCode(), HttpStatusCode.BAD_REQUEST);
        }
    }

    @Test
    void parseInvalidHTTPRequestWithCRButNoLF() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidHttpGETRequestWithCRNoLF());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getHttpStatusCode(), HttpStatusCode.BAD_REQUEST);
        }
    }

    private InputStream generateValidHttpGETRequest() {
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "sec-ch-ua: \"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"\r\n" +
                "sec-ch-ua-mobile: ?0\r\n" +
                "sec-ch-ua-platform: \"Linux\"\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
                "Accept-Language: es-ES,es;q=0.9,en;q=0.8\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream generateInvalidHttpGETRequest() {
        String rawData = "GeT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: es-ES,es;q=0.9,en;q=0.8\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }


    private InputStream generateInvalidHttpGETRequestWithMaxLengthExceeded() {
        String rawData = "GETTTTTTTTTTTTTTTTT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: es-ES,es;q=0.9,en;q=0.8\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream generateInvalidHttpGETRequestInvalidItemsNumber() {
        String rawData = "GET / INVALID / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: es-ES,es;q=0.9,en;q=0.8\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream generateInvalidHttpGETRequestEmptyRequestLine() {
        String rawData = "\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: es-ES,es;q=0.9,en;q=0.8\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream generateInvalidHttpGETRequestWithCRNoLF() {
        String rawData = "GET / HTTP/1.1\r" + // <------ no LF (\n)
                "Host: localhost:8080\r\n" +
                "Accept-Language: es-ES,es;q=0.9,en;q=0.8\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }
}