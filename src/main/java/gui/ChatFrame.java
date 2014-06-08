package gui;

import gui.events.ConnectEvent;
import gui.listeners.ConnectionListener;
import io.ChatMember;
import io.net.events.Message;
import io.net.listeners.MessageListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.JScrollPane;

/**
 * Der JFrame, der zur Interaktion mit dem User dient.
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class ChatFrame extends JFrame implements ChatGui, ChatMember {
    
    private static final long serialVersionUID = 4688472858038683926L;
    private JPanel contentPane;
    private JLabel lblStatus;
    private JLabel lblPortOut;
    private JScrollPane scrollPane;
    private JButton btnSubmit;
    private JButton btnConnect;
    private JTextField ipText;
    private JTextField portInText;
    private JTextField statusText;
    private JTextField submitText;
    private JTextArea chatArea;
    private JLabel lblIp;
    private JLabel lblPort;
    
    private List<MessageListener> messageListeners;
    private List<ConnectionListener> connectListeners;
    private JTextField portOutText;
    private JTextField nameText;
    
    /**
     * Create the frame.
     */
    public ChatFrame() {
        initComponents();
        layoutComponents();
        
        messageListeners = new ArrayList<MessageListener>();
        connectListeners = new ArrayList<ConnectionListener>();
        
        setVisible(true);
    }
    
    @Override
    public void setStatus(String status) {
        statusText.setText(status);
    }
    
    @Override
    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }
    
    @Override
    public void addConnectionListener(ConnectionListener listener) {
        connectListeners.add(listener);
    }
    
    @Override
    public boolean forwardMessage(Message message) {
        printMessage(message);
        return true;
    }
    
    private void printMessage(Message message) {
        chatArea.setText(chatArea.getText()
                + formatTime(message.getTime()) + " "
                + message.getAuthor() + ": "
                + message + "\n");
    }
    
    private void submit() {
        
        if (!submitText.getText().equals("")) {
            
            Message message =
                    new Message(System.currentTimeMillis(), nameText.getText(), submitText.getText());
            printMessage(message);
            notifyMessage(message);
            submitText.setText("");
        }
    }
    
    private static String formatTime(long time) {
        String format = "(HH:mm)";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(time));
    }
    
    private void notifyMessage(Message message) {
        for (Iterator<MessageListener> i = messageListeners.iterator(); i.hasNext();) {
            MessageListener listener = (MessageListener) i.next();
            listener.messageReceived(message);
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
    
    private void layoutComponents() {
        JPanel panel = new JPanel();
        
        JPanel panel1 = new JPanel();
        GroupLayout glContentPane = new GroupLayout(contentPane);
        glContentPane.setHorizontalGroup(
                glContentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                );
        glContentPane.setVerticalGroup(
                glContentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(glContentPane.createSequentialGroup()
                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE))
                );
        GroupLayout glPanel1 = new GroupLayout(panel1);
        glPanel1.setHorizontalGroup(
                glPanel1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(glPanel1.createSequentialGroup()
                                .addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.
                                        DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(submitText, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(btnSubmit))
                        .addComponent(scrollPane, Alignment.LEADING)
                );
        glPanel1.setVerticalGroup(
                glPanel1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(glPanel1.createSequentialGroup()
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(glPanel1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnSubmit)
                                        .addComponent(submitText, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nameText, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                );
        
        statusText = new JTextField();
        statusText.setEditable(false);
        statusText.setColumns(10);
        
        GroupLayout glPanel = new GroupLayout(panel);
        glPanel.setHorizontalGroup(
                glPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPanel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(glPanel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(glPanel.createSequentialGroup()
                                                .addComponent(lblIp)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(ipText, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
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
                                                .addGap(96))
                                        .addGroup(glPanel.createSequentialGroup()
                                                .addComponent(lblStatus)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(statusText, GroupLayout.DEFAULT_SIZE,
                                                        679, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(Alignment.TRAILING, glPanel.createSequentialGroup()
                                .addContainerGap(644, Short.MAX_VALUE)
                                .addComponent(btnConnect))
                );
        glPanel.setVerticalGroup(
                glPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPanel.createSequentialGroup()
                                .addGroup(glPanel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(glPanel.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(btnConnect)
                                                .addComponent(lblPortOut)
                                                .addComponent(portInText, GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblPort)
                                                .addComponent(ipText, GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(portOutText, GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(glPanel.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(lblIp)))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(glPanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(statusText, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblStatus))
                                .addContainerGap(11, Short.MAX_VALUE))
                );
        
        panel.setLayout(glPanel);
        panel1.setLayout(glPanel1);
        
        contentPane.setLayout(glContentPane);
    }
    
    private void initComponents() {
        setTitle("Chat Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 468);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        ActionListener connectListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notifyConnect();
            }
            
        };
        
        ActionListener submitListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        };
        
        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(submitListener);
        
        submitText = new JTextField();
        submitText.addActionListener(submitListener);
        submitText.setColumns(10);
        
        scrollPane = new JScrollPane();
        
        nameText = new JTextField();
        nameText.setColumns(10);
        
        chatArea = new JTextArea();
        chatArea.setWrapStyleWord(true);
        scrollPane.setViewportView(chatArea);
        chatArea.setLineWrap(true);
        chatArea.setEditable(false);
        
        lblIp = new JLabel("IP:");
        
        ipText = new JTextField();
        ipText.addActionListener(connectListener);
        ipText.setColumns(10);
        
        lblPort = new JLabel("Port In:");
        
        portInText = new JTextField();
        portInText.addActionListener(connectListener);
        portInText.setColumns(10);
        
        btnConnect = new JButton("Connect");
        btnConnect.addActionListener(connectListener);
        
        lblStatus = new JLabel("Status:");
        
        lblPortOut = new JLabel("Port Out:");
        
        portOutText = new JTextField();
        portOutText.addActionListener(connectListener);
        portOutText.setColumns(10);
        
    }
}
