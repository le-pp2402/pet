package tech.pp.web;

import tech.pp.web.constants.Commons;

public class BuildHttpResponse {
    private int statusCode;
    private String contentType;
    private String contentLength;

    public BuildHttpResponse(int statusCode, String contentType, String contentLength) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.contentLength = contentLength;
    }

    public String buildResponse(String body) {
        StringBuilder responseHeader = new StringBuilder();
        responseHeader.append(Commons.HTTP_VERSION).append(" ").append(statusCode).append("\r\n");
        responseHeader.append(Commons.CONTENT_TYPE).append(": ").append(contentType).append("\r\n");
        responseHeader.append(Commons.CONTENT_LENGTH).append(": ").append(contentLength).append("\r\n");
        responseHeader.append("\r\n");
        responseHeader.append(body);
        return responseHeader.toString();
    }
}
