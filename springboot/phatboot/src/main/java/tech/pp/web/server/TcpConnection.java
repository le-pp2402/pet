package tech.pp.web.server;

import java.net.ServerSocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TcpConnection implements AutoCloseable {
    private static final Logger log = LogManager.getLogger(TcpConnection.class);
    private int port = 8080;
    private static TcpConnection instance = null;
    private ServerSocket serverSocket;

    private TcpConnection(int Port) {
        this.port = Port;
        log.info("TCP Connection opened on port " + port);
        open(port);
    }

    private void open(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.accept();
        } catch (Exception e) {
            log.error("Failed to open TCP Connection on port " + port, e);
        }
    }

    @Override
    public void close() throws Exception {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
            log.info("TCP Connection on port " + port + " closed.");
        } else {
            log.warn("TCP Connection on port " + port + " is already closed or was never opened.");
        }
    }

    private static TcpConnection getTcpConnection() {
        if (instance == null) {
            instance = new TcpConnection(7336);
        }
        return instance;
    }

    public static ServerSocket getServerSocket() {
        return getTcpConnection().serverSocket;
    }
}
