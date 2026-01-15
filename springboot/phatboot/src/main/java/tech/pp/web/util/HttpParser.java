package tech.pp.web.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tech.pp.web.http.HttpMethod;
import tech.pp.web.http.RequestBody;
import tech.pp.web.http.RequestHeader;

public class HttpParser {
    private static final Logger log = LogManager.getLogger(HttpParser.class);

    public static RequestHeader fromRawHeader(String rawHeader) {
        log.info("Parsing raw HTTP request header:\n" + rawHeader);

        String[] headerLines = rawHeader.split("\r\n");
        String requestLine = headerLines[0];
        String[] requestLineParts = requestLine.split(" ");
        if (requestLineParts.length < 3) {
            log.warn("Invalid HTTP request line: " + requestLine);
            throw new IllegalArgumentException("Invalid HTTP request line: " + requestLine);
        }

        String method = requestLineParts[0];
        String path = requestLineParts[1];

        Map<String, String> otherHeaders = new HashMap<>();

        for (int i = 1; i < headerLines.length; i++) {
            String line = headerLines[i];
            int colonIndex = line.indexOf(":");
            if (colonIndex != -1) {
                String headerName = line.substring(0, colonIndex).trim().toUpperCase();
                String headerValue = line.substring(colonIndex + 1).trim();
                otherHeaders.put(headerName, headerValue);
            }
        }

        Long contentLength = 0L;

        if (otherHeaders.containsKey("CONTENT-LENGTH")) {
            try {
                contentLength = Long.parseLong(otherHeaders.get("CONTENT-LENGTH"));
            } catch (NumberFormatException e) {
                log.warn("Invalid Content-Length value: " + otherHeaders.get("CONTENT-LENGTH"));
                contentLength = 0L;
            }
        }

        var headerRequest = RequestHeader
                .newBuilder()
                .method(HttpMethod.valueOf(method))
                .path(path)
                .otherHeaders(otherHeaders)
                .contentLength(contentLength)
                .build();

        return headerRequest;
    }

    public static RequestBody fromRawBody(String rawBody) {
        log.info("Parsing raw HTTP request body:\n" + rawBody);
        return new RequestBody(rawBody);
    }
}
