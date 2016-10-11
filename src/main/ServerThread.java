package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import models.Order;
import models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class ServerThread extends Thread {
    private Socket socket;
    private BufferedReader input;
    private PrintStream output;
    private String[] dividedClientMassage;
    private boolean holdAlive = true;
    private ObjectMapper mapper = new ObjectMapper();

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        super.run();
        try {
            while (holdAlive) {
                String clientMassage = input.readLine();
                if (clientMassage != null) {
                    System.out.println("Client massage: " + clientMassage);
                    dividedClientMassage = clientMassage.split(":");
                    switch (dividedClientMassage[0]) {
                        case "0"://login
                            login();
                            break;
                        case "1"://login
                            getOrders();
                            break;
                        case "2"://start execution
                            startExecution();
                            break;
                        case "3"://finish execution
                            finishExecution();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getOrders() throws SQLException, IOException {
        List<Order> orders = DaoFactory.getInstance().getOrderDAOImpl().getOrders();
        output.println(mapper.writeValueAsString(orders));
    }

    private void finishExecution() throws SQLException {
        int id = Integer.valueOf(dividedClientMassage[1]);
    }

    private void startExecution() throws SQLException {
        int id = Integer.valueOf(dividedClientMassage[1]);
    }

    private void login() throws SQLException {
        String login = dividedClientMassage[1];
        String password = dividedClientMassage[2];
        User user = DaoFactory.getInstance().getDriverDAOImpl().getUser(login);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                System.out.println("Sending server answer: 1");
                output.println("1");
            } else {
                System.out.println("Sending server answer: 0");
                output.println("0");
            }
        } else {
            System.out.println("Sending server answer: 0");
            output.println("0");
        }
    }

}
