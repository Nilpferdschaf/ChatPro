package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import io.net.events.Message;
import io.net.listeners.MessageListener;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * Ein Panel mit Kontrollelementen zum Senden/Empfangen von Nachrichten
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class MessagePanel extends JPanel {
    private static final long serialVersionUID = 6728099095008165201L;
    private JTextArea messageArea;
    private JTextField messageText;
    private JTextField nameText;
    private JButton btnSubmit;
    private List<MessageListener> listeners;
    
    /**
     * Create the panel.
     */
    public MessagePanel() {
        initComponents();
        
        listeners = new ArrayList<MessageListener>();
    }
    
    /**
     * Schreibt die gegebene Nachricht in das vorgesehene textArea
     * 
     * @param message Die zu schreibende Nachricht.
     */
    public void printMessage(Message message) {
        messageArea.setText(messageArea.getText()
                + formatTime(message.getTime()) + " "
                + message.getAuthor() + ": "
                + message + "\n");
    }
    
    private static String formatTime(long time) {
        String format = "(HH:mm)";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(time));
    }
    
    private void submit() {
        
        if (!messageText.getText().equals("")) {
            
            Message message = new Message(
                    System.currentTimeMillis(),
                    nameText.getText(),
                    messageText.getText());
            printMessage(message);
            notifyMessage(message);
            messageText.setText("");
        }
    }

    private void notifyMessage(Message message) {
        for (Iterator<MessageListener> i = listeners.iterator(); i.hasNext();) {
            MessageListener listener = (MessageListener) i.next();
            listener.transmitMassage(message);
        }
    }

    /**
     * Fuegt einen neuen MessageListener zu der Liste der Beobachter hinzu
     * 
     * @param listener der hinzuzufuegende MessageListener
     */
    public void addMessageListener(MessageListener listener) {
        listeners.add(listener);
    }
    
    private void initComponents() {
        
        ActionListener submitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        };
        
        btnSubmit = new JButton("Submit");
        
        messageText = new JTextField();
        messageText.setColumns(10);
        
        nameText = new JTextField();
        nameText.setColumns(10);
        
        btnSubmit.addActionListener(submitListener);
        messageText.addActionListener(submitListener);
        
        JScrollPane scrollPane = new JScrollPane();
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                .addComponent(nameText, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(messageText, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(btnSubmit))
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnSubmit)
                                        .addComponent(messageText, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nameText, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                );
        
        messageArea = new JTextArea();
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setEditable(false);
        scrollPane.setViewportView(messageArea);
        setLayout(groupLayout);
    }
}
