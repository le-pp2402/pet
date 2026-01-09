package tech.pp.server;

public class TomPhatServer {
    public void start() {
        System.out.println("TomPhat Server started...");
        while (true) {
            try (var serverSocket = TcpConnection.getServerSocket()) {
                while (true) {
                    var socket = serverSocket.accept();
                    System.out.println("Client connected: " + socket.getInetAddress());
                    new Thread(new ClientHandler(socket)).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}