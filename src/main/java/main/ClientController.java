package main;

import javax.swing.JFrame;

import io.ChatServer;
import io.net.Server;
import io.net.events.MessageEvent;
import io.net.events.StatusChangeEvent;
import io.net.listeners.MessageListener;
import io.net.listeners.StatusListener;
import gui.ChatFrame;
import gui.ChatGui;
import gui.events.ConnectEvent;
import gui.events.SubmissionEvent;
import gui.listeners.ConnectionListener;
import gui.listeners.SubmitListener;

/**
 * Ein Controller, der Statusupdates zwischen einem Chat Server und dem GUI hin-
 * und herschiebt und deren Interaktion verwaltet.
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ClientController {
    
    private ChatGui ui;
    private Server server;
    
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
        
        ui.addSubmitListener(new SubmitListener() {
            
            @Override
            public void submitPressed(SubmissionEvent e) {
                server.sendMessage(e.getMessage());
            }
            
        });
        
        server.addStatusListener(new StatusListener() {
            
            @Override
            public void statusChanged(StatusChangeEvent e) {
                ui.setStatus(e.getStatus());
            }
            
        });
        
        server.addMessageListener(new MessageListener() {
            
            @Override
            public void messageReceived(MessageEvent evt) {
                ui.printMessage(evt.getMessage());
            }
            
        });
        
        ((JFrame) ui).addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                server.disconnect();
                System.exit(0);
            }
        });
    }
}
