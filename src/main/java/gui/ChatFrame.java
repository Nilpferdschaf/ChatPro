package gui;

import gui.events.ConnectEvent;
import gui.events.SubmissionEvent;
import gui.listeners.ConnectionListener;
import gui.listeners.SubmitListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

/**
 * Der JFrame, der zur Interaktion mit dem User dient.
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ChatFrame extends JFrame implements ChatGui {
    
    private static final long serialVersionUID = 4688472858038683926L;
    private JPanel contentPane;
    private JTextField ipText;
    private JTextField portInText;
    private JTextField statusText;
    private JTextField submitText;
    private JTextArea textArea;
    private List<SubmitListener> subListeners;
    private List<ConnectionListener> connectListeners;
    private JTextField portOutText;
    
    @Override
    public void setStatus(String status) {
        statusText.setText(status);
    }
    
    @Override
    public void addSubmitListener(SubmitListener listener) {
        subListeners.add(listener);
    }
    
    @Override
    public void addConnectionListener(ConnectionListener listener) {
        connectListeners.add(listener);
    }
    
    @Override
    public void printMessage(String message) {
        textArea.setText(textArea.getText() + "\n" + message);
    }
    
    private void notifySubmit() {
        for (Iterator<SubmitListener> i = subListeners.iterator(); i.hasNext();) {
            SubmitListener listener = (SubmitListener) i.next();
            listener.submitPressed(new SubmissionEvent(submitText.getText()));
        }
    }
    
    private void notifyConnect() {
        for (Iterator<ConnectionListener> i = connectListeners.iterator(); i.hasNext();) {
            ConnectionListener listener = (ConnectionListener) i.next();
            listener.connectPressed(new ConnectEvent(getIP(), getPortIn(), getPortOut()));
        }
    }
    
    private String getIP() {
        return ipText.getText();
    }
    
    private int getPortIn() {
        int port = 80;
        try {
            port = Integer.parseInt(portInText.getText());
        } catch (NumberFormatException e) {
            portInText.setText("80");
        }
        return port;
    }
    
    private int getPortOut() {
        int port = 80;
        try {
            port = Integer.parseInt(portOutText.getText());
        } catch (NumberFormatException e) {
            portOutText.setText("80");
        }
        return port;
    }
    
    /**
     * Create the frame.
     */
    public ChatFrame() {
        initComponents();
        
        subListeners = new ArrayList<SubmitListener>();
        connectListeners = new ArrayList<ConnectionListener>();
        
        setVisible(true);
    }
    
    private void initComponents() {
        setTitle("Chat Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 671, 463);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        JPanel panel = new JPanel();
        
        JPanel panel1 = new JPanel();
        GroupLayout glContentPane = new GroupLayout(contentPane);
        glContentPane.setHorizontalGroup(
                glContentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
                        .addComponent(panel1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
                );
        glContentPane.setVerticalGroup(
                glContentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(glContentPane.createSequentialGroup()
                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                );
        
        textArea = new JTextArea();
        
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                notifySubmit();
                submitText.setText("");
            }
            
        });
        
        submitText = new JTextField();
        submitText.setColumns(10);
        GroupLayout glPanel1 = new GroupLayout(panel1);
        glPanel1.setHorizontalGroup(
                glPanel1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(glPanel1.createSequentialGroup()
                                .addComponent(submitText, GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(btnSubmit))
                        .addComponent(textArea, GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
                );
        glPanel1.setVerticalGroup(
                glPanel1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(glPanel1.createSequentialGroup()
                                .addComponent(textArea, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(glPanel1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnSubmit)
                                        .addComponent(submitText, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                );
        panel1.setLayout(glPanel1);
        
        JLabel lblIp = new JLabel("IP:");
        
        ipText = new JTextField();
        ipText.setColumns(10);
        
        JLabel lblPort = new JLabel("Port In:");
        
        portInText = new JTextField();
        portInText.setColumns(10);
        
        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                notifyConnect();
            }
            
        });
        
        JLabel lblStatus = new JLabel("Status:");
        
        statusText = new JTextField();
        statusText.setColumns(10);
        
        JLabel lblPortOut = new JLabel("Port Out:");
        
        portOutText = new JTextField();
        portOutText.setColumns(10);
        GroupLayout glPanel = new GroupLayout(panel);
        glPanel.setHorizontalGroup(
                glPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPanel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(glPanel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(glPanel.createSequentialGroup()
                                                .addComponent(lblStatus)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(statusText, GroupLayout.DEFAULT_SIZE, 
                                                        600, Short.MAX_VALUE))
                                        .addGroup(glPanel.createSequentialGroup()
                                                .addComponent(lblIp)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(ipText, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(lblPort)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(portInText, GroupLayout.PREFERRED_SIZE, 
                                                        111, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(lblPortOut)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(portOutText, GroupLayout.PREFERRED_SIZE, 
                                                        114, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnConnect)))
                                .addContainerGap())
                );
        glPanel.setVerticalGroup(
                glPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPanel.createSequentialGroup()
                                .addGap(5)
                                .addGroup(glPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblIp)
                                        .addComponent(ipText, GroupLayout.PREFERRED_SIZE, 
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnConnect)
                                        .addComponent(portOutText, GroupLayout.PREFERRED_SIZE, 
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblPortOut)
                                        .addComponent(portInText, GroupLayout.PREFERRED_SIZE, 
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblPort))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(glPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(statusText, GroupLayout.PREFERRED_SIZE, 
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblStatus))
                                .addGap(15))
                );
        panel.setLayout(glPanel);
        contentPane.setLayout(glContentPane);
    }
}
