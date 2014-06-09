package io.net;

import io.net.encryption.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Der Transmitter-Teil eines Chatservers
 * 
 * @author nicklas-kulp
 * @version 2.0
 */
public class Transmitter extends ServerComponent {
    
    private ObjectOutputStream oos;
    private String ip;
    private int portOut;
    
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
                oos = new ObjectOutputStream(socket.getOutputStream());
                connected = true;
            } catch (IOException e) {
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
            oos.writeObject(message);
        } catch (IOException e) {
            notifyStatusListeners(ServerStatus.DISCONNECTED);
        }
        
        return false;
    }
    
    @Override
    public void run() {
        connect();
    }
}
