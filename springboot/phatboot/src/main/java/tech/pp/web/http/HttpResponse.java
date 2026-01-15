package tech.pp.web.http;

public class HttpResponse<T> {
    private ResponseHeader responseHeader;
    private T responseBody;

    public HttpResponse(ResponseHeader responseHeader, T responseBody) {
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public T getResponseBody() {
        return responseBody;
    }
}
