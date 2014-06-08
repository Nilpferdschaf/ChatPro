package gui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * Ein Panel mit zum Anzeigen des ServerStatus
 * 
 * @author nicklas-kulp
 * @version 1.0
 */
public class StatusPanel extends JPanel {
    private static final long serialVersionUID = -709769066513152202L;
    private JTextField statusField;
    
    /**
     * Create the panel.
     */
    public StatusPanel() {
        initComponents();
    }
    
    /**
     * Setzt den angezeigten Status auf en gegebenen Status
     * 
     * @param status Der aktualisierte Serverstatus
     */
    public void setStatus(String status) {
        statusField.setText(status);
    }
    
    private void initComponents() {
        JLabel lblStatus = new JLabel("Status:");
        
        statusField = new JTextField();
        statusField.setEditable(false);
        statusField.setColumns(10);
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(lblStatus)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(statusField, GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addComponent(statusField, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        );
        setLayout(groupLayout);
    }
    
}
