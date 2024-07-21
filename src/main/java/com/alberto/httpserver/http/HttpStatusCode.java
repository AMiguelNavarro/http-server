package com.alberto.httpserver.http;

public enum HttpStatusCode {
    BAD_REQUEST(400, "Bad Request"),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),
    URI_TOO_LONG(414, "URI too long"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    NOT_IMPLEMENTED(501, "Not implemented")
    ;

    private final int STATUS_CODE;
    private final String MESSAGE;

    HttpStatusCode(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }

    public String getMessage() {
        return MESSAGE;
    }
}
