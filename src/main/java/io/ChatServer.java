package io;

import io.net.Server;
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
    
    /**
     * Kreiert einen neuen ChatServer
     */
    public ChatServer() {
        receiver = new Receiver();
        transmitter = new Transmitter();
    }
    
    @Override
    public void addStatusListener(StatusListener listener) {
        receiver.addStatusListener(listener);
        transmitter.addStatusListener(listener);
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
