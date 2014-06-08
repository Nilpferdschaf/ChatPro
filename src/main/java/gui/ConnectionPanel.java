package gui;

import gui.events.ConnectEvent;
import gui.listeners.ConnectionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;

/**
 * Ein Panel mit Kontrollelementen zum Verbinden mit einem externen Server
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ConnectionPanel extends JPanel {
    private static final long serialVersionUID = -7047765592882233297L;
    private JTextField ipText;
    private JButton btnConnect;
    private JSpinner portOut;
    private JSpinner portIn;
    private List<ConnectionListener> listeners;
    
    /**
     * Create the panel.
     */
    public ConnectionPanel() {
        initComponents();
        
        listeners = new ArrayList<ConnectionListener>();
    }
    
    /**
     * Fuegt einen ConnectionListener zu der Liste der Beobachter hinzu
     * 
     * @param listener Der hinzuzufuegende ConnectionListener
     */
    public void addConnectionListener(ConnectionListener listener) {
        listeners.add(listener);
    }
    
    private void notifyConnect() {
        for (Iterator<ConnectionListener> i = listeners.iterator(); i.hasNext();) {
            ConnectionListener listener = (ConnectionListener) i.next();
            listener.connectPressed(
                    new ConnectEvent(ipText.getText(), (int) portIn.getValue(), (int) portOut.getValue()));
        }
    }
    
    private void initComponents() {
        JLabel lblPortout = new JLabel("PortOut:");
        JLabel lblPortin = new JLabel("PortIn:");
        
        ActionListener connectListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notifyConnect();
            }
        };
        
        btnConnect = new JButton("Connect");
        
        portOut = new JSpinner();
        portIn = new JSpinner();
        
        ipText = new JTextField();
        
        ipText.setColumns(10);
        ipText.addActionListener(connectListener);
        btnConnect.addActionListener(connectListener);
        
        portOut.setModel(new SpinnerNumberModel(1, 1, 65535, 1));
        portIn.setModel(new SpinnerNumberModel(1, 1, 65535, 1));
        
        JLabel lblNewLabel = new JLabel("IP:");
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(lblNewLabel)
                                .addGap(12)
                                .addComponent(ipText, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblPortin)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(portIn, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblPortout)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(portOut, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(btnConnect))
                );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(ipText, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                        .addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                        .addComponent(lblPortin, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                        .addComponent(portIn, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                        .addComponent(lblPortout, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                        .addComponent(portOut, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                        .addComponent(btnConnect, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                );
        setLayout(groupLayout);
    }
}
