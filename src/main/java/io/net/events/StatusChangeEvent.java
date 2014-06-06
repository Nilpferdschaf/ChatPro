package io.net.events;

/**
 * Enthaelt Informationen ueber den aktuellen Serverstatus
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class StatusChangeEvent {
    String status;
    
    /**
     * Erzeugt ein neues StatusChangeEvent mit der angegebenen Statusnachricht
     * 
     * @param status Der neue ServerStatus
     */
    public StatusChangeEvent(String status) {
        this.status = status;
    }
    
    /**
     * @return Der neue Serverstatus
     */
    public String getStatus() {
        return status;
    }
}
