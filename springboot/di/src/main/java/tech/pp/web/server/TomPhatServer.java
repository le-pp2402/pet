package tech.pp.web.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TomPhatServer {
    private Logger log = LogManager.getLogger(TomPhatServer.class);

    public void start() {
        log.info("TomPhat server is starting...");
        while (true) {
            try (var serverSocket = TcpConnection.getServerSocket()) {
                while (true) {
                    var socket = serverSocket.accept();
                    new Thread(new ClientHandler(socket)).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error in TomPhat server: ", e);
                break;
            }
        }
    }
}