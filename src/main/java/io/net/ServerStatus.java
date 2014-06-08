package io.net;

/**
 * Stellt verschiedene Serverstatus zur Verfuegung.
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public enum ServerStatus {
    /**
     * Der Server wurde gerade initiiert
     */
    STARTED("Waiting for connection data"),
    /**
     * Der Server beginnt sich mit einem externen Server zu verbinden
     */
    CONNECTING("Connecting"),
    /**
     * Der Server hoert nun einen Port ab
     */
    LISTENING("Waiting for external server"),
    /**
     * Der Server hat einen externen Server erreicht, aber diese konnte noch
     * keine Verbindung zu ihm aufbauen.
     */
    CAN_SEND("Reached external server"),
    /**
     * Ein externer Server hat diesen erreicht, aber es konnte noch keine
     * Verbindung zu ihm aufgebaut werden.
     */
    CAN_RECEIVE("Receiving from external server"),
    /**
     * Der Server kann an einen externen Server Nachrichten senden und
     * Nachrichten von ihm empfangen
     */
    CONNECTED("Connection established"),
    /**
     * Die Verbindung zum Server wurde verloren
     */
    DISCONNECTED("Connection lost");
    
    private String statusMessage;
    
    /**
     * Erzeugt einen neuen ServerStatus mit der angegebenen Statusnachricht
     * 
     * @param statusMessage Eine textuelle Beschreibung des Serverstatus
     */
    ServerStatus(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    
    /**
     * @return Eine textuelle Beschreibung des Serverstatus
     */
    public String getStatusMessage() {
        return statusMessage;
    }
}
