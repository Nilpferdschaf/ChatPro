package io.net;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.net.encryption.Message;
import io.net.events.StatusChangeEvent;
import io.net.listeners.MessageListener;
import io.net.listeners.StatusListener;

/**
 * Ein Chatserver, zum Senden und empfangen von Nachrichten
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ChatServer implements Server, ChatMember {
    
    private Receiver receiver;
    private Transmitter transmitter;
    private List<StatusListener> statusListeners;
    private ServerStatus status;
    
    /**
     * Kreiert einen neuen ChatServer
     */
    public ChatServer() {
        this.status = ServerStatus.STARTED;
        this.receiver = new Receiver();
        this.transmitter = new Transmitter();
        this.statusListeners = new ArrayList<StatusListener>();
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
        
        listener.statusChanged(new StatusChangeEvent(this.status));
    }
    
    private synchronized void notifyStatusListeners(ServerStatus status) {
        switch (this.status) {
            case STARTED:
            case DISCONNECTED:
            case CONNECTED:
            case CONNECTING:
            case LISTENING:
                this.status = status;
            break;
            case CAN_SEND:
                if (status == ServerStatus.CAN_RECEIVE) {
                    this.status = ServerStatus.CONNECTED;
                }
            break;
            case CAN_RECEIVE:
                if (status == ServerStatus.CAN_SEND) {
                    this.status = ServerStatus.CONNECTED;
                }
            break;
            default:
            break;
        }
        for (Iterator<StatusListener> i = statusListeners.iterator(); i.hasNext();) {
            StatusListener listener = (StatusListener) i.next();
            listener.statusChanged(new StatusChangeEvent(this.status));
        }
    }
    
    @Override
    public void addMessageListener(MessageListener listener) {
        receiver.addMessageListener(listener);
        
    }
    
    @Override
    public boolean forwardMessage(Message message) {
        return transmitter.send(message);
    }
    
    @Override
    public void connect(String ip, int portIn, int portOut) {
        receiver.listen(portIn);
        transmitter.connect(ip, portOut);
    }
    
    @Override
    public void disconnect() {
        receiver.close();
        transmitter.close();
    }
    
}
