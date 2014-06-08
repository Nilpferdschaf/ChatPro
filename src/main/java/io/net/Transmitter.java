package io.net;

import io.net.events.Message;
import io.net.events.StatusChangeEvent;
import io.net.listeners.StatusListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Der Transmitter-Teil eines Chatservers
 * 
 * @author nicklas-kulp
 * 
 */
public class Transmitter extends Thread {
    
    private Socket socket;
    private String ip;
    private int portOut;
    private List<StatusListener> statusListeners;
    
    /**
     * Kreiert einen neuen Transmitter
     */
    public Transmitter() {
        statusListeners = new ArrayList<StatusListener>();
    }
    
    /**
     * Startet den Thread und verbindet den transmitter mit der angegebenen IP
     * und Portangabe
     * 
     * @param ip Die IP des externen servers
     * @param portOut Der Port zum Senden von Nachrichten
     */
    public void connect(String ip, int portOut) {
        this.ip = ip;
        this.portOut = portOut;
        this.start();
    }
    
    private void connect() {
        boolean connected = false;
        while (!connected) {
            try {
                socket = new Socket(ip, portOut);
                connected = true;
            } catch (UnknownHostException e) {
                System.out.println(e.getMessage());
                connected = false;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                connected = false;
            }
        }
        
        notifyStatusListeners(ServerStatus.CAN_SEND);
    }
    
    /**
     * Sendet eine Nachricht an den externen Server
     * 
     * @param message Die zu sendende Nachricht
     * @return true, wenn erfolgreich gesendet werden konnte, false, wenn nicht
     */
    public boolean send(Message message) {
        try {
            OutputStream outStream = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(outStream);
            dos.writeLong(message.getTime());
            dos.writeUTF(message.getAuthor());
            dos.writeUTF(message.getMessage());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    private void notifyStatusListeners(ServerStatus status) {
        for (Iterator<StatusListener> i = statusListeners.iterator(); i.hasNext();) {
            StatusListener listener = (StatusListener) i.next();
            listener.statusChanged(new StatusChangeEvent(status));
        }
    }
    
    /**
     * Fuegt einen neuen StatusListener zu der Liste der Beobachter hinzu
     * 
     * @param listener Der hinzuzufuegende StatusListener
     */
    public void addStatusListener(StatusListener listener) {
        statusListeners.add(listener);
    }
    
    /**
     * Schließt den Transmitter und alle zugehörigen Ressourcen
     */
    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        connect();
    }
}
