package deprecated;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdditionClient extends JFrame implements ActionListener {

    // declare the visual components

    private JTextField firstNumber = new JTextField(3);
    private JLabel plus = new JLabel("+");
    private JTextField secondNumber = new JTextField(3);
    private JLabel equals = new JLabel("=");
    private JLabel sum= new JLabel();
    private JTextField msg = new JTextField(20);
    private JButton addButton= new JButton("Press to see the sum of the two numbers");

    // declare low level and high level objects for input
    private InputStream inStream;
    private DataInputStream inDataStream;

    // declare low level and high level objects for output
    private OutputStream outStream;
    private DataOutputStream outDataStream;

    // declare socket
    private Socket connection;

    // declare attribute to told details of remote machine and port
    private String remoteMachine;
    private int port;

    // constructor

    public AdditionClient(String remoteMachineIn, int portIn){
        remoteMachine = remoteMachineIn;
        port= portIn;

        //add the visual components
        add(firstNumber);
        add(plus);
        add(secondNumber);
        add(equals);
        add(sum);
        add(msg);
        add(addButton);

        // configure the frame
        setLayout(new FlowLayout());
        setTitle("Addtion Client");
        msg.setHorizontalAlignment(JLabel.CENTER);
        addButton.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,150);
        setVisible(true);

        //start the helper method that starts the client
        startClient();
    }

    private void startClient() {
        try{
            // attempt to create a connection to the server
            connection = new Socket(remoteMachine,port);
            msg.setText("connection establish");

            // create an input stream from the server
            inStream = connection.getInputStream();
            inDataStream = new DataInputStream(inStream);

            //create output stream to the server
            outStream = connection.getOutputStream();
            outDataStream = new DataOutputStream(outStream);

            //send the host IP to the server
            outDataStream.writeUTF(connection.getLocalAddress().getHostAddress());

        }catch (UnknownHostException e){
            e.printStackTrace();
            msg.setText("Unknow host");
        }
        catch (IOException except){
            except.printStackTrace();
            msg.setText("Network Exception");
        }
    }

    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        try{
         // send the two integers to the server
            outDataStream.writeInt(Integer.parseInt(firstNumber.getText()));
            outDataStream.writeInt(Integer.parseInt(secondNumber.getText()));

            //read and display the results sent back from the server
            //String results= inDataStream.readUTF();
            int results= inDataStream.readInt();
            sum.setText(""+results);
        }catch(IOException ie){
            ie.printStackTrace();
        }
    }

    public static void main (String args[]){
        new AdditionClient("25.163.206.71", 80);
    }
}