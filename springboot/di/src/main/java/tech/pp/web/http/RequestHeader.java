package tech.pp.web.http;

import java.util.Map;

public class RequestHeader {
    private HttpMethod method;
    private String path;
    private Long contentLength;
    private Map<String, String> otherHeaders;

    private RequestHeader(HttpMethod method, String path, Long contentLength, Map<String, String> otherHeaders) {
        this.method = method;
        this.path = path;
        this.contentLength = contentLength;
        this.otherHeaders = otherHeaders;
    }

    public static class RequestHeaderBuilder {
        private HttpMethod method;
        private String path;
        private Long contentLength;
        private Map<String, String> otherHeaders;

        public RequestHeaderBuilder() {
        }

        public RequestHeaderBuilder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public RequestHeaderBuilder path(String path) {
            this.path = path;
            return this;
        }

        public RequestHeaderBuilder contentLength(Long contentLength) {
            this.contentLength = contentLength;
            return this;
        }

        public RequestHeaderBuilder otherHeaders(Map<String, String> otherHeaders) {
            this.otherHeaders = otherHeaders;
            return this;
        }

        public RequestHeader build() {
            return new RequestHeader(this.method, this.path, this.contentLength, this.otherHeaders);
        }
    }

    public static RequestHeaderBuilder newBuilder() {
        return new RequestHeaderBuilder();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getOtherHeaders() {
        return otherHeaders;
    }

    public Long getContentLength() {
        return contentLength;
    }
}
