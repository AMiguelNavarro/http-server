package com.alberto.httpserver.http;

public class HttpRequest extends HttpMessage {
    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

    // Only other classes of this package can instantiate objects of this type
    // Maybe builder pattern latter?
    HttpRequest() {
    }

    /*
    * 3 important things to keep in mind from RFC 7230 to parse request:
    * - Method toke is case-sensitive
    * - All general-purpose servers MUST support GET and HEAD methods. All others are OPTIONAL
    * - The set of methods allowed by a target resource can be listed in an allow header field
    * */

    public HttpMethod getMethod() {
        return method;
    }

    void setMethod(String methodName) throws HttpParsingException {
        this.method = HttpMethod.getFromMethodName(methodName);
    }
}
