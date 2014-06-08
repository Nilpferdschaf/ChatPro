package io.net.events;

import io.net.ServerStatus;

/**
 * Enthaelt Informationen ueber den aktuellen Serverstatus
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class StatusChangeEvent {
    ServerStatus status;
    
    /**
     * Erzeugt ein neues StatusChangeEvent mit der angegebenen Statusnachricht
     * 
     * @param status Der neue ServerStatus
     */
    public StatusChangeEvent(ServerStatus status) {
        this.status = status;
    }
    
    /**
     * @return Der neue Serverstatus
     */
    public ServerStatus getStatus() {
        return status;
    }
}
