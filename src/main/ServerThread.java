package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import dao.impl.OrderDaoImpl;
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

    private final String CONFIRM = "1";
    private final String REFUSE = "0";
    private final String REQUEST_LOGIN = "0";
    private final String REQUEST_GET_ORDERS = "1";
    private final String REQUEST_START_EXECUTION = "2";
    private final String REQUEST_FINISH_EXECUTION = "3";

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
                        case REQUEST_LOGIN:
                            login();
                            break;
                        case REQUEST_GET_ORDERS:
                            getOrders();
                            break;
                        case REQUEST_START_EXECUTION:
                            startExecution();
                            break;
                        case REQUEST_FINISH_EXECUTION:
                            finishExecution();
                            break;
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
                input.close();
                output.close();
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
        Order order = DaoFactory.getInstance().getOrderDAOImpl().getOrder(id);
        DaoFactory.getInstance().getOrderDAOImpl().changeStatus(order, OrderDaoImpl.FINISHED);
    }

    private void startExecution() throws SQLException, IOException {
        int id = Integer.valueOf(dividedClientMassage[1]);
        Order order = DaoFactory.getInstance().getOrderDAOImpl().getOrder(id);
        if (order.getStatus() == OrderDaoImpl.FREE) {
            DaoFactory.getInstance().getOrderDAOImpl().changeStatus(order, OrderDaoImpl.RUNTIME);
            output.println(CONFIRM);
        } else {
            output.println(REFUSE);
        }
    }

    private void login() throws SQLException {
        String login = dividedClientMassage[1];
        String password = dividedClientMassage[2];
        User user = DaoFactory.getInstance().getDriverDAOImpl().getUser(login);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                System.out.println("Sending server answer: confirm");
                output.println(CONFIRM);
            } else {
                System.out.println("Sending server answer: refuse");
                output.println(REFUSE);
            }
        } else {
            System.out.println("Sending server answer: refuse");
            output.println(REFUSE);
        }
    }

}
