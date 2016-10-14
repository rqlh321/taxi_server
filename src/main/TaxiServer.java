package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class TaxiServer {
    public static void main(String[] args) throws IOException, SQLException {
        TaxiServer taxiServer = new TaxiServer();
        taxiServer.runServer();
    }

    private void runServer() throws IOException, SQLException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("Taxi Server Started...");
        boolean run = true;
        while (run) {
            System.out.println("Waiting connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Taxi Server get connection...");
            new ServerThread(socket).start();
        }
        serverSocket.close();
    }
}
