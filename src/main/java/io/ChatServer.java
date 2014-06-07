package io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.net.Server;
import io.net.events.StatusChangeEvent;
import io.net.listeners.MessageListener;
import io.net.listeners.StatusListener;

/**
 * Ein Chatserver, zum Senden und empfangen von Nachrichten
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ChatServer implements Server {
    
    private Receiver receiver;
    private Transmitter transmitter;
    private List<StatusListener> statusListeners;
    
    /**
     * Kreiert einen neuen ChatServer
     */
    public ChatServer() {
        receiver = new Receiver();
        transmitter = new Transmitter();
        statusListeners = new ArrayList<StatusListener>();
    }
    
    @Override
    public void addStatusListener(StatusListener listener) {
        
        statusListeners.add(listener);
        
        StatusListener combinedStatus = new StatusListener() {
            @Override
            public void statusChanged(StatusChangeEvent evt) {
                notifyStatusListeners(evt.getStatus());
            }
        };
        receiver.addStatusListener(combinedStatus);
        transmitter.addStatusListener(combinedStatus);
    }
    
    private void notifyStatusListeners(String status) {
        for (Iterator<StatusListener> i = statusListeners.iterator(); i.hasNext();) {
            StatusListener listener = (StatusListener) i.next();
            listener.statusChanged(new StatusChangeEvent(status));
        }
    }
    
    @Override
    public void addMessageListener(MessageListener listener) {
        receiver.addMessageListener(listener);
        
    }
    
    @Override
    public boolean sendMessage(String message) {
        return transmitter.send(message);
    }
    
    @Override
    public void connect(String ip, int portIn, int portOut) {
        receiver.listen(portIn);
        System.out.println("send");
        transmitter.connect(ip, portOut);
    }
    
    @Override
    public void disconnect() {
        receiver.close();
        transmitter.close();
    }
    
}
