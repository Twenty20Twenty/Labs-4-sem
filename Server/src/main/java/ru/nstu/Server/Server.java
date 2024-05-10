package ru.nstu.Server;

import ru.nstu.javafx_labs_lipatov_v2.Client.MessageBox;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Server {
    protected static final TreeMap<Integer, TCPConnection> connections = new TreeMap<>();
    public static void main(String[] args){
        System.out.println("Server started...\n");
        try(ServerSocket serverSocket =new ServerSocket(TCPConnection.PORT)){
            while(true){
                try{
                    new TCPConnection(serverSocket.accept());
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        }
        catch (IOException e){
            System.out.println(e.getCause().toString());
        }
    }

    public static void onConnectionReady(TCPConnection tcpConnection){
        connections.put(tcpConnection.getSocket().getPort(), tcpConnection);
        update();
        System.out.println("Client connected: " + tcpConnection + "\n");
    }

    public static void onDisconnect(TCPConnection tcpConnection){
        connections.remove(tcpConnection.getSocket().getPort());
        update();
        System.out.println("Client disconnected: " + tcpConnection + "\n");
        System.out.println("Current connections: " + connections.size() + "\n");
    }

    private static void update(){
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<Integer, TCPConnection> entry: connections.entrySet()){
            list.add(entry.getValue().toString());
        }
        sendToAllConnections(new MessageBox(list, -1));
    }

    private static void sendToAllConnections(Object object){
        for (Map.Entry<Integer, TCPConnection> entry : connections.entrySet()){
            entry.getValue().sendObject(object);
        }
    }
}
