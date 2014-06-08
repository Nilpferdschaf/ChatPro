package io.net.events;

import java.io.Serializable;

/**
 * Enthaelt Informationen über eine Nachricht, die ein User sende will
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class Message implements Serializable {
    
    private static final long serialVersionUID = -3882988477894037325L;
    private long time;
    private String author;
    private String message;
    
    /**
     * Erzeugt ein neues MessageEvent mit der gegebenen Nachricht vom User
     * 
     * @param time Die Zeit, zu der die Nachricht erstellt wurde
     * @param author Der Autor der Nachricht
     * @param message Die Nachricht, die der User senden will
     */
    public Message(long time, String author, String message) {
        this.time = time;
        this.author = author;
        this.message = message;
    }
    
    /**
     * @return Die Zeit, zu der diese Nachricht erstellt wurde in Millisekunden
     * nach Epoche
     */
    public long getTime() {
        return time;
    }
    
    /**
     * @return Den Autor der Nachricht.
     */
    public String getAuthor() {
        return author;
    }
    
    /**
     * @return Die Nachricht, die gesendet werden soll
     */
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return message;
    }
}
