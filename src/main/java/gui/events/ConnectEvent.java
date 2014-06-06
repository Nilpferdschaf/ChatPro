package gui.events;

/**
 * Enthaelt Informationen über die IP und VerbindungsPorts des
 * Servers, mit dem sich ein User verbinden will.
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ConnectEvent {
    private String ip;
    private int portIn;
    private int portOut;
    
    /**
     * Erzeugt ein neues ConnectEvent mit den angegebenen Information zu IP, portIn und PortOut des Servers
     * 
     * @param ip Die IP des anderen Servers
     * @param portIn Der Port, der nach eingehenden Nachrichten angehoert werden soll
     * @param portOut Der Port, ueber den Nachrichten gesendet werden sollen
     */
    public ConnectEvent(String ip, int portIn, int portOut) {
        this.ip = ip;
        this.portIn = portIn;
        this.portOut = portOut;
    }
    
    /**
     * @return Die IP des externen Servers
     */
    public String getIP() {
        return ip;
    }
    
    /**
     * @return Der Port, der nach Nachrichten abgehoert werden soll
     */
    public int getPortIn() {
        return portIn;
    }
    
    /**
     * @return Der Port, der zum Senden von Nachrichten benutzt werden soll
     */
    public int getPortOut() {
        return portOut;
    }
}
