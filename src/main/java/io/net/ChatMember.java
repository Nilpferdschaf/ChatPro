package io.net;

import io.net.encryption.Message;
import io.net.listeners.MessageListener;

/**
 * Ein Interface, das Methoden zum senden und empfangen von Nachrichten
 * bereitstellt
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public interface ChatMember {
    /**
     * Fuegt einen MessageListener zu der Liste der Beobachter hinzu, der benachrichtigt werden soll, wenn
     * eine ausgehende Nachricht weitergeleitet werden muss.
     * 
     * @param listener Der hinzuzufuegende MessageListener
     */
    void addMessageListener(MessageListener listener);
    
    /**
     * Leitet die gegebene Nachricht weiter
     * 
     * @param message Ein MessageEvent mit der Nachricht.
     * @return true, wenn die Nachricht weitergereicht werden konnte, false, wenn nicht.
     */
    boolean forwardMessage(Message message);
}
