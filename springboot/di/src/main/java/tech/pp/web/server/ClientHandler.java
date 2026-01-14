package tech.pp.web.server;

import java.io.BufferedInputStream;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tech.pp.dispatch.Dispatcher;
import tech.pp.web.BuildHttpResponse;
import tech.pp.web.http.HttpRequest;
import tech.pp.web.http.RequestHeader;
import tech.pp.web.util.HttpParser;

public class ClientHandler extends Thread {
    private static Logger log = LogManager.getLogger(ClientHandler.class);
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        log.info("handling client in thread " + Thread.currentThread().getName());
        try (var inputStream = socket.getInputStream()) {

            BufferedInputStream bis = new BufferedInputStream(inputStream);
            // Get Header
            StringBuilder headerBuilder = new StringBuilder();
            int c;
            HttpRequest req = null;
            while ((c = bis.read()) != -1) {
                headerBuilder.append((char) c);
                if (headerBuilder.toString().endsWith("\r\n\r\n")) {
                    String header = headerBuilder.substring(0, headerBuilder.length() - 4);
                    log.info("Received header:\n" + header);
                    RequestHeader reqHeader = HttpParser.fromRawHeader(header);

                    if (reqHeader.getContentLength() != null && reqHeader.getContentLength() > 0) {
                        StringBuffer rawBody = new StringBuffer();
                        for (long i = 0; i < reqHeader.getContentLength(); i++) {
                            c = bis.read();
                            if (c == -1) {
                                throw new RuntimeException("Unexpected end of stream while reading body");
                            }
                            rawBody.append((char) c);
                        }
                        log.info("Received body:\n" + rawBody.toString());

                        req = new HttpRequest(reqHeader, HttpParser.fromRawBody(rawBody.toString()));
                    } else {
                        req = new HttpRequest(reqHeader, null);
                    }
                    break;
                }
            }

            if (req == null) {
                throw new RuntimeException("Failed to parse HTTP request");
            }

            var handler = Dispatcher.dispatch(req.getRequestHeader().getMethod(), req.getRequestHeader().getPath());

            // TODO: handle reponse, build response
            try {
                String result = handler.getMethod().invoke(handler.getBean()).toString();
                BuildHttpResponse response = new BuildHttpResponse(
                        200,
                        "text/plain",
                        String.valueOf(result.length()));
                socket.getOutputStream().write(response.buildResponse(result).getBytes());
                socket.getOutputStream().flush();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            log.error("Error handling client", e);
        }
    }

}
