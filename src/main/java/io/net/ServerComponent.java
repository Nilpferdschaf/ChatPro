package io.net;

import io.net.events.StatusChangeEvent;
import io.net.listeners.StatusListener;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Eine Abstrakte Klasse, die ein Serverkomponent beschreibt. Sie implementiert
 * bereits unter anderem die Methoden zum Benachrichtigen der Statuslistener
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ServerComponent extends Thread {
    protected Socket socket;
    private List<StatusListener> statusListeners;
    
    /**
     * Kreiert ein neues ServerComponent, ohne angemeldete StatusListener
     */
    public ServerComponent() {
        statusListeners = new ArrayList<StatusListener>();
    }
    
    /**
     * Benachrichtigt alle angemeldeten StatusListener, dass sich der Serverstatus geaendert hat
     * 
     * @param status Der neue ServerStatus
     */
    protected void notifyStatusListeners(ServerStatus status) {
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
        
        notifyStatusListeners(ServerStatus.DISCONNECTED);
    }
}
