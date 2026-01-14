package tech.pp.web.http;

public class HttpRequest {
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(RequestHeader requestHeader, RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
