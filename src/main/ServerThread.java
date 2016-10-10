package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import models.Area;
import models.Driver;

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
    private int driverId = -1;

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
                        case "1"://logout
                            logout();
                            break;
                        case "2"://start execution
                            startExecution();
                            break;
                        case "3"://finish execution
                            finishExecution();
                        case "4"://set my area
                            setMyArea();
                            break;
                        case "5"://get areas
                            getAreas();
                            break;
                        case "6"://get drivers count in area
                            countDriversInArea();
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
                if (holdAlive) {
                    try {
                        DaoFactory.getInstance().getDriverDAOImpl().setStatus(driverId, 0);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getAreas() throws SQLException, IOException {
        List<Area> areaList = DaoFactory.getInstance().getAreaDAOImpl().getAreas();
        String massage = mapper.writeValueAsString(areaList);
        System.out.println("Sending server answer: " + massage);
        output.println(massage);
    }

    private void setMyArea() {
        int id = Integer.valueOf(dividedClientMassage[1]);
    }

    private void finishExecution() throws SQLException {
        int id = Integer.valueOf(dividedClientMassage[1]);
        DaoFactory.getInstance().getDriverDAOImpl().setStatus(id, 1);
    }

    private void startExecution() throws SQLException {
        int id = Integer.valueOf(dividedClientMassage[1]);
        DaoFactory.getInstance().getDriverDAOImpl().setStatus(id, 2);
        //TODO: set driver in order
    }

    private void logout() throws SQLException {
        //driverId = Integer.valueOf(dividedClientMassage[1]);
        DaoFactory.getInstance().getDriverDAOImpl().setStatus(driverId, 0);
        System.out.println("User with id: " + driverId + " disconnected.");
        holdAlive = false;
    }

    private void login() throws SQLException {
        String login = dividedClientMassage[1];
        String password = dividedClientMassage[2];
        Driver driver = DaoFactory.getInstance().getDriverDAOImpl().getDriver(login);
        if (driver != null) {
            if (driver.getPassword().equals(password)) {
                driverId = driver.getId();
                DaoFactory.getInstance().getDriverDAOImpl().setStatus(driver.getId(), 1);
                System.out.println("Sending server answer: 1:id");
                output.println("1:" + driverId);
            } else {
                System.out.println("Sending server answer: 0");
                output.println("0");
            }
        } else {
            System.out.println("Sending server answer: 0");
            output.println("0");
        }
    }

    private void countDriversInArea() throws SQLException {
        int idArea = Integer.valueOf(dividedClientMassage[1]);
        int count = DaoFactory.getInstance().getDriverDAOImpl().countDriversInArea(idArea);
        System.out.println("Sending server answer: " + count);
        output.println(count);
    }
}
