package main;

import io.net.ChatServer;
import io.net.encryption.Message;
import io.net.events.StatusChangeEvent;
import io.net.listeners.MessageListener;
import io.net.listeners.StatusListener;
import gui.ChatFrame;
import gui.events.ConnectEvent;
import gui.listeners.ConnectionListener;

/**
 * Ein Controller, der Statusupdates zwischen einem Chat Server und dem GUI hin-
 * und herschiebt und deren Interaktion verwaltet.
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ClientController {
    
    private ChatFrame ui;
    private ChatServer server;
    
    /**
     * Erzeugt einen neuen ClientController
     */
    public ClientController() {
        ui = new ChatFrame();
        server = new ChatServer();
        
        ui.addConnectionListener(new ConnectionListener() {
            
            @Override
            public void connectPressed(ConnectEvent evt) {
                server.connect(evt.getIP(), evt.getPortIn(), evt.getPortOut());
            }
            
        });
        
        ui.addMessageListener(new MessageListener() {

            @Override
            public void transmitMassage(Message message) {
                server.forwardMessage(message);
            }
        });
        
        server.addStatusListener(new StatusListener() {
            
            @Override
            public void statusChanged(StatusChangeEvent e) {
                ui.setStatus(e.getStatus().getStatusMessage());
            }
            
        });
        
        server.addMessageListener(new MessageListener() {
            
            @Override
            public void transmitMassage(Message message) {
                ui.forwardMessage(message);
            }
            
        });
        
        ui.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                server.disconnect();
                System.exit(0);
            }
        });
        
        ui.setVisible(true);
    }
}
