package ru.nstu.Server;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ru.nstu.javafx_labs_lipatov_v2.Client.MessageBox;

public class TCPConnection {
    public static final String IP_ADRESS = "127.0.0.1";
    public static final  int PORT = 8001;
    private final Socket socket;
    private final Thread rxThread;
    private final ObjectInput in;
    private final ObjectOutputStream out;

    public TCPConnection (Socket socket) throws IOException{
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.onConnectionReady(TCPConnection.this);
                    while (!rxThread.isInterrupted()) {
                        onReceiveObject(TCPConnection.this);
                    }
                }
                catch (IOException e){
                    rxThread.interrupt();
                } finally {
                    Server.onDisconnect(TCPConnection.this);
                }
            }
        });
        rxThread.start();
    }

    public Socket getSocket(){
        return socket;
    }

    public void onReceiveObject(TCPConnection tcpConnection) throws IOException{
        try {
            MessageBox receiveMessage = (MessageBox) in.readObject();
            TCPConnection client = Server.connections.get(receiveMessage.getClient());
            MessageBox sendMessage = new MessageBox(receiveMessage.getCommand());
            client.sendObject(sendMessage);
        } catch (ClassNotFoundException e){
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e){
            return;
        }
    }

    public synchronized void sendObject(Object object){
        try{
            out.writeObject(object);
            out.flush();
        }  catch (IOException  e){
            onException(e);
            disconnect();
        }
    }

    public synchronized void disconnect(){
        rxThread.interrupt();
        try{
            in.close();
            out.close();
            socket.close();
        } catch (IOException e){
            onException(e);
        }
    }

    @Override
    public String toString(){
        return "Client " + socket.getPort();
    }

    public void onException(Exception e){
        System.out.println("TCPConnection exception: " + e + "\n");
    }
}
