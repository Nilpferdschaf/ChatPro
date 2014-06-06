package io.net.events;

/**
 * Enthaelt Informationen über eine Nachricht, die ein User sende will
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class MessageEvent {
    String message;
    
    /**
     * Erzeugt ein neues MessageEvent mit der gegebenen Nachricht vom User
     * 
     * @param message Die Nachricht, die der User senden  will
     */
    public MessageEvent(String message) {
        this.message = message;
    }
    
    /**
     * @return Die Nachricht, die gesendet werden soll
     */
    public String getMessage() {
        return message;
    }
}
