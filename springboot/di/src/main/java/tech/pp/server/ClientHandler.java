package tech.pp.server;

import java.io.BufferedInputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Handling client in thread: " + Thread.currentThread().getName());
        try (var inputStream = socket.getInputStream()) {

            BufferedInputStream bis = new BufferedInputStream(inputStream);
            // Get Header
            StringBuilder headerBuilder = new StringBuilder();
            int c;
            while ((c = bis.read()) != -1) {
                headerBuilder.append((char) c);
                if (headerBuilder.toString().endsWith("\r\n\r\n")) {
                    break;
                }
            }
            String header = headerBuilder.toString();
            System.out.println("Received header:\n" + header);
            // Get Body (if any)
            StringBuilder bodyBuilder = new StringBuilder();
            while (bis.available() > 0) {
                c = bis.read();
                bodyBuilder.append((char) c);
            }
            String body = bodyBuilder.toString();
            if (!body.isEmpty()) {
                System.out.println("Received body:\n" + body);
            }

            BuildHttpResponse response = new BuildHttpResponse(200, "text/plain", "13");
            String httpResponse = response.buildResponseHeader() + "Hello, World!";
            socket.getOutputStream().write(httpResponse.getBytes());
            socket.getOutputStream().flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
