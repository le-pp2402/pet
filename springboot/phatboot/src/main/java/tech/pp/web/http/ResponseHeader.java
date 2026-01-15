package tech.pp.web.http;

public class ResponseHeader {
    private int statusCode;
    private String statusMessage;
    private Long contentLength;
    private String contentType;

    public ResponseHeader(int statusCode, String statusMessage, Long contentLength, String contentType) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.contentLength = contentLength;
        this.contentType = contentType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public static ResponseHeaderBuilder newBuilder() {
        return new ResponseHeaderBuilder();
    }

    public static class ResponseHeaderBuilder {
        private int statusCode;
        private String statusMessage;
        private Long contentLength;
        private String contentType;

        public ResponseHeaderBuilder() {
        }

        public ResponseHeaderBuilder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResponseHeaderBuilder statusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public ResponseHeaderBuilder contentLength(Long contentLength) {
            this.contentLength = contentLength;
            return this;
        }

        public ResponseHeaderBuilder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public ResponseHeader build() {
            return new ResponseHeader(this.statusCode, this.statusMessage, this.contentLength, this.contentType);
        }
    }

}