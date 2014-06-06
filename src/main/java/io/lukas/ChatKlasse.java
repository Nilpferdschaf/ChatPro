package io.lukas;

import io.net.Server;
import io.net.listeners.MessageListener;
import io.net.listeners.StatusListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatKlasse implements Server {
    
    public static void main(String[] args) {
        
        try {
            String localHost = InetAddress.getLocalHost().getHostName();
            for (InetAddress ia : InetAddress.getAllByName(localHost)) {
                System.out.println(ia);
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // hier beim anderen port-1
        ReceiverThread receiver = new ReceiverThread(5027);
        
        receiver.start();
        System.out.println("amStarten");
        // SenderThread Sender = new SenderThread("25.190.93.112", 5015);
        // hier beim anderen port+1
        SenderThread sender = new SenderThread("25.190.93.112", 5028);
        
        sender.start();
        System.out.println("StartedAu�en");
        
    }

    @Override
    public void addStatusListener(StatusListener listener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addMessageListener(MessageListener listener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean sendMessage(String message) {
        return false;
        // TODO Auto-generated method stub
        
    }

    @Override
    public void connect(String ip, int portIn, int portOut) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void disconnect() {
        // TODO Auto-generated method stub
        
    }
}
