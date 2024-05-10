package ru.nstu.javafx_labs_lipatov_v2.Client;

import javafx.application.Platform;
import ru.nstu.javafx_labs_lipatov_v2.mvc.HabitatModel;
import ru.nstu.javafx_labs_lipatov_v2.mvc.HabitatView;

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
            HabitatView view = model.getView();
            switch (receiveMessage.getCommand()) {
                case 0:
                    model.setInformationWindowFlag(false);
                    if (model.isStartFlag()) {
                        model.stopGeneration();
                        if (!model.isStartFlag()) {
                            view.getButtonStart().setDisable(false);
                            view.getButtonStop().setDisable(true);
                            view.getLiveObjButton().setDisable(true);
                            view.getLiveObjMenuItem().setDisable(true);
                        }
                    }
                    view.getApplyMaleProp().setDisable(false);
                    view.getMaleSpawnTimeTextField().setDisable(false);
                    view.getMaleSpawnProbabilityBox().setDisable(false);
                    view.getApplyFemaleProp().setDisable(false);
                    view.getFemaleSpawnTimeTextField().setDisable(false);
                    view.getFemaleSpawnProbabilityBox().setDisable(false);
                    view.getMaleLifeTimeTextField().setDisable(false);
                    view.getFemaleLifeTimeTextField().setDisable(false);
                    view.getStartMenuItem().setDisable(false);
                    view.getStopMenuItem().setDisable(true);
                    model.setInformationWindowFlag(true);
                    break;
                case 1:
                    model.startGeneration();
                    view.getButtonStart().setDisable(true);
                    view.getButtonStop().setDisable(false);
                    view.getApplyMaleProp().setDisable(true);
                    view.getMaleSpawnTimeTextField().setDisable(true);
                    view.getMaleSpawnProbabilityBox().setDisable(true);
                    view.getApplyFemaleProp().setDisable(true);
                    view.getFemaleSpawnTimeTextField().setDisable(true);
                    view.getFemaleSpawnProbabilityBox().setDisable(true);
                    view.getMaleLifeTimeTextField().setDisable(true);
                    view.getFemaleLifeTimeTextField().setDisable(true);
                    view.getLiveObjButton().setDisable(false);
                    view.getLiveObjMenuItem().setDisable(false);
                    view.getStartMenuItem().setDisable(true);
                    view.getStopMenuItem().setDisable(false);
                    break;
                case 2:
                    break;
                default:
                    Platform.runLater(() -> {
                        model.getView().getConnectedClientsList().getItems().clear();
                        for (String client : receiveMessage.getClientList()) {
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