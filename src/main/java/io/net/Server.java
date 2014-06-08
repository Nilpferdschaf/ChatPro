package io.net;

import io.net.listeners.StatusListener;

/**
 * Ein Interface fuer einen Chat Server. Das Interface stellt Methoden zum
 * Senden/Empfangen von Nachrichten, verbinden mit einem anderen Server und
 * empfangen von Statusupdates zur Verfuegung.
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public interface Server {
    
    /**
     * Fuegt einen neuen StatusListener zu dem Server hinzu, der bei
     * Statusänderungen des Servers benachrichtigt wird.
     * 
     * @param listener Der hinzuzufuegende StatusListener
     */
    void addStatusListener(StatusListener listener);
    
    /**
     * Verbindet den Server mit dem Server an der angegebenen IP und Port
     * 
     * @param ip Die IP des anderen Servers
     * @param portIn Der Port ueber den Nachrichten empfangen werden
     * @param portOut Der Port ueber den Nachrichten gesendet werden
     */
    void connect(String ip, int portIn, int portOut);
    
    /**
     * Gibt die Serverressourcen wieder frei und schließt die benutzten Ports
     */
    void disconnect();
}
