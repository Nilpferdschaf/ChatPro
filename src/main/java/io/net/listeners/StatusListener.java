package io.net.listeners;

import io.net.events.StatusChangeEvent;

import java.util.EventListener;

/**
 * Ein Beobachter, der benachrichtigt werden soll, wenn sich der Status eines Servers aendert
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public abstract class StatusListener implements EventListener {
    
    /**
     * Diese Methode wird aufgerufen, wenn sich der Serverstatus aendert
     * 
     * @param evt Informationen ueber die Statusaenderung
     */
    public abstract void statusChanged(StatusChangeEvent evt);
}
