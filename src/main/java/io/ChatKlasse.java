package io;

import io.net.Server;
import io.net.listeners.MessageListener;
import io.net.listeners.StatusListener;

public class ChatKlasse implements Server {
    
    ReceiverThread Receiver;
    SenderThread Sender;
    boolean connected = false;
    
    public static void main(String[] args) {
        
    }
    
    public ChatKlasse(String IP, int portIn, int portOut) {
        connect(IP, portIn, portOut);
    }
    
    public void connect(String IP, int portIn, int portOut) {
        Receiver = new ReceiverThread(portIn); // hier beim anderen port-1
        Receiver.start();
        Sender = new SenderThread(IP, portOut); // hier beim anderen port+1
        Sender.start();
        connected = true;
    }
    
    public void disconnect() {
        Receiver.close();
        Sender.close();
        connected = false;
    }
    
    public boolean sendMessage(String message) {
        if (connected == true) {
            Sender.send("String", message);
        } else {
            System.out.println("Es ist noch keine Verbindung etabliert!");
        }
        return false;
    }
    
    public void addMessageListener(MessageListener listener) {
        Receiver.addMessageListener(listener);
    }
    
    public void addStatusListener(StatusListener listener) {
        Receiver.addStatusListener(listener);
        Sender.addStatusListener(listener);
    }
}
