package tech.pp.web.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TomPhatServer {
    private static final Logger log = LogManager.getLogger(TomPhatServer.class);
    private final ExecutorService threadPool = Executors.newFixedThreadPool(20);

    public TomPhatServer() {
    }

    public void start() {
        log.info("TomPhat server is starting...");
        try (var serverSocket = TcpConnection.getServerSocket()) {
            while (true) {
                var socket = serverSocket.accept();
                threadPool.execute(new ClientHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error in TomPhat server: ", e);
        } finally {
            threadPool.shutdown();
        }
    }
}