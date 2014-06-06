package gui.listeners;

import gui.events.ConnectEvent;

import java.util.EventListener;

/**
 * Ein Beobachter, der benachrichtigt werden soll, wenn der User eine neue
 * Verbindung aufbauen will
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public abstract class ConnectionListener implements EventListener {
    
    /**
     * Diese Methode wird aufgerufen, wenn der Nutzer eine neue Verbindung
     * aufbauen will. In evt stehen Informationen ueber die Verbindung.
     * 
     * @param evt Die Verbindungsinformationen
     */
    public abstract void connectPressed(ConnectEvent evt);
}
