package io.net;

import io.net.encryption.Message;
import io.net.listeners.MessageListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Der Receiver-Teil eines ChatServers
 * 
 * @author nicklas-kulp
 * @version 2.0
 */
public class Receiver extends ServerComponent {
    
    private int portIn;
    private List<MessageListener> messageListeners;
    
    /**
     * Kreiert einen neuen Receiver
     */
    public Receiver() {
        portIn = 5001;
        messageListeners = new ArrayList<MessageListener>();
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
                ObjectInputStream ois = new ObjectInputStream(inStream);
                
                Message message;
                while ((message = (Message) ois.readObject()) != null) {
                    notifyMessageListeners(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            notifyStatusListeners(ServerStatus.DISCONNECTED);
        }
    }
    
    private void notifyMessageListeners(Message message) {
        for (Iterator<MessageListener> i = messageListeners.iterator(); i.hasNext();) {
            MessageListener listener = (MessageListener) i.next();
            listener.transmitMassage(message);
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
    
    @Override
    public void run() {
        listen();
    }
}
