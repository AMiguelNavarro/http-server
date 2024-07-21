package com.alberto.httpserver.http;

public enum HttpMethod {
    GET, HEAD;

    public static final int MAX_LENGTH;

    static {
        int tempMaxLength = -1;
        for (HttpMethod method : values()) {
            if (method.name().length() > tempMaxLength) {
                tempMaxLength = method.name().length();
            }
        }
        MAX_LENGTH = tempMaxLength;
    }

    public static HttpMethod getFromMethodName(String methodName) throws HttpParsingException {
        for (HttpMethod method : values()) {
            if (method.name().equals(methodName)) {
                return method;
            }
        }

        throw new HttpParsingException(HttpStatusCode.NOT_IMPLEMENTED);
    }
}
