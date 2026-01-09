package com.pp.javanw;

import java.io.BufferedReader;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        System.out.println("Java Networking Demo");

        try {
            URL url = new URL("https://picsum.photos/200/300");
            var connection = url.openConnection();
            connection.connect();
            System.out.println("Connection established to: " + url);

            System.out.println("host: " + url.getHost());
            System.out.println("protocol: " + url.getProtocol());
            System.out.println("port: " + url.getPort());
            System.out.println("path: " + url.getPath());
            System.out.println("Query: " + url.getQuery());

            System.out.println("***************************************");

            var contentLength = connection.getContentLength();

            System.out.println("Content Length: " + contentLength + " bytes");
            System.out.println("Content Length: " + contentLength / 1024 + " KB");

            var contentType = connection.getContentType();
            System.out.println("Content Type: " + contentType);

            File file = new File("image." + contentType.split("/")[1]);
            java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = connection.getInputStream().read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.close();
            System.out.println("Image saved to: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
