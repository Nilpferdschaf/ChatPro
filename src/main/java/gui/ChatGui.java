package gui;

import gui.listeners.ConnectionListener;

/**
 * Ein Interface für ein Chat GUI, es stellt Methoden zum hinzufuegen von
 * Beobachtern fuer Verbindungseinstellungen und Uebertragungen zur Verfuegung,
 * sowie Methoden zum Anzeigen von eingehenden Nachrichten und Statusaenderungen
 * des Servers.
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public interface ChatGui {
    /**
     * Fuegt einen ConnectionListener zu der GUI hinzu. Dieser wird
     * benachrichtigt, wenn der User sich mit einem anderen Server verbinden
     * moechte.
     * 
     * @param listener Der hinzuzufuegende ConnectionListener
     */
    void addConnectionListener(ConnectionListener listener);
    
    /**
     * Zeigt den neuen ServerStatus an
     * 
     * @param status Der aktualisierte ServerStatus
     */
    void setStatus(String status);
}
