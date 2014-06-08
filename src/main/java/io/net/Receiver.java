package io.net;

import io.net.events.Message;
import io.net.events.StatusChangeEvent;
import io.net.listeners.MessageListener;
import io.net.listeners.StatusListener;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Der Receiver-Teil eines ChatServers
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class Receiver extends Thread {
    
    private Socket socket;
    private int portIn;
    private List<MessageListener> messageListeners;
    private List<StatusListener> statusListeners;
    
    /**
     * Kreiert einen neuen Receiver
     */
    public Receiver() {
        portIn = 5001;
        messageListeners = new ArrayList<MessageListener>();
        statusListeners = new ArrayList<StatusListener>();
    }
    
    /**
     * Startet den Thread und hoert den angegebenen Port nach Nachrichten ab.
     * 
     * @param portIn Der Port auf dem eingehende Nachrichten ankommen
     */
    public void listen(int portIn) {
        this.portIn = portIn;
        this.start();
    }
    
    private void listen() {
        
        try (ServerSocket serverSocket = new ServerSocket(portIn)) {
            
            while (true) {
                
                notifyStatusListeners(ServerStatus.LISTENING);
                socket = serverSocket.accept();
                notifyStatusListeners(ServerStatus.CAN_RECEIVE);
                
                InputStream inStream = socket.getInputStream();
                DataInputStream sr = new DataInputStream(inStream);
                
                while (true) {
                    Message message =
                            new Message(sr.readLong(), sr.readUTF(), sr.readUTF());
                    notifyMessageListeners(message);
                }
            }
        } catch (IOException e) {
            notifyStatusListeners(ServerStatus.DISCONNECTED);
        }
    }
    
    private void notifyMessageListeners(Message message) {
        for (Iterator<MessageListener> i = messageListeners.iterator(); i.hasNext();) {
            MessageListener listener = (MessageListener) i.next();
            listener.messageReceived(message);
        }
    }
    
    /**
     * Fuegt einen neuen MessageListener zu der Liste der Beobachter hinzu
     * 
     * @param listener Der hinzuzufuegende MessageListener
     */
    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
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
     * Schließt den Receiver und die verwendeten Ressourcen
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
        listen();
    }
}
