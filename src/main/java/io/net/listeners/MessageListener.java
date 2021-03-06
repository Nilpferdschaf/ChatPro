package io.net.listeners;

import io.net.encryption.Message;

import java.util.EventListener;

/**
 * Ein Beobachter, der benachrichtigt werden soll, wenn eine neue Nachricht bei
 * einem Server eingegangen ist
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public abstract class MessageListener implements EventListener {
    
    /**
     * Diese Methode wird aufgerufen, wenn eine neue Nachricht beim Server
     * eingeht
     * 
     * @param message Informationen ueber die Nachricht
     */
    public abstract void transmitMassage(Message message);
}
