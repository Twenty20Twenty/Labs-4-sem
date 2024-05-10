package ru.nstu.javafx_labs_lipatov_v2.Client;

import javafx.application.Platform;
import ru.nstu.javafx_labs_lipatov_v2.mvc.HabitatModel;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class TCPClient {
    private static String IP_ADDRESS = "127.0.0.1";
    private static int PORT = 8001;
    private Socket socket;
    private Thread rxThread;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private HabitatModel model;

    public TCPClient(HabitatModel model) throws IOException {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            this.model = model;
            rxThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (!rxThread.isInterrupted()) {
                            onReceiveObject();
                        }
                    } catch (IOException e) {
                        rxThread.interrupt();
                    } finally {
                        disconnect();
                    }
                }
            });
            rxThread.start();
        } catch (ConnectException e) {
            socket = null;
            out = null;
            in = null;
            rxThread = null;
        }
    }

    public void onReceiveObject() throws IOException {
        try {
            MessageBox receiveMessage = (MessageBox) in.readObject();
            switch (receiveMessage.getCommand()) {
                case 0:
                    if (model.isStartFlag())
                        model.stopGeneration();
                    break;
                case 1:
                    if (!model.isStartFlag())
                        model.startGeneration();
                    break;
                case 2:
                    if (model.isStartFlag())
                        model.pauseMaleAI();
                    break;
                default:
                    Platform.runLater(()->{
                        model.getView().getConnectedClientsList().getItems().clear();
                        for (String client: receiveMessage.getClientList()){
                            model.getView().getConnectedClientsList().getItems().add(client);
                        }
                    });
                    break;
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            return;
        }
    }

    public synchronized void sendObject(Object object) {
        try {
            out.writeObject(object);
            out.flush();
        } catch (IOException e) {
            onException(e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        if (!rxThread.isInterrupted())
            rxThread.interrupt();
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            onException(e);
        }
    }

    public void onException(Exception e) {
        System.out.println("TCPConnection exception: " + e + "\n");
    }

    public static String getIpAddress() {
        return IP_ADDRESS;
    }

    public static void setIpAddress(String ipAddress) {
        IP_ADDRESS = ipAddress;
    }

    public static int getPORT() {
        return PORT;
    }

    public static void setPORT(int PORT) {
        TCPClient.PORT = PORT;
    }

    public Socket getSocket() {
        return socket;
    }
}