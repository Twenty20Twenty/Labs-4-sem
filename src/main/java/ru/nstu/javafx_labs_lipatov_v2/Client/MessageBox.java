package ru.nstu.javafx_labs_lipatov_v2.Client;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MessageBox implements Serializable {
    private ArrayList<String> clientList;
    private int client;
    private int command;

    public MessageBox(ArrayList<String> list, int commandNum){
        clientList = list;
        command = commandNum;
    }

    public MessageBox(int clientNum, int commandNum){
        client = clientNum;
        command = commandNum;
    }

    public MessageBox(int commandNum) {
        command = commandNum;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public ArrayList<String> getClientList() {
        return clientList;
    }
}
