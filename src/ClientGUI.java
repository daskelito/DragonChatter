import jdk.jshell.execution.JdiExecutionControl;

import javax.accessibility.AccessibleContext;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ClientGUI {
    private JFrame frame;
    private JPanel rightPanel;
    private JPanel onlineListPanel;
    private JPanel contactsListPanel;
    private ImageIcon userPicture;
    private String username;
    private ArrayList<JCheckBox> onlineList;
    private ArrayList<JCheckBox> contactList;
    private Client client;

    public ClientGUI(Client client) {
        this.client = client;
        onlineList = new ArrayList<>();
        contactList = new ArrayList<>();

        frame = new JFrame("DragonChatter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel leftPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel(new GridLayout(1, 2));
        frame.add(leftPanel, BorderLayout.LINE_START);
        frame.add(rightPanel, BorderLayout.LINE_END);

        //Text area for chat history
        JTextArea textArea1 = new JTextArea(25, 40);
        textArea1.append("chat history here");
        textArea1.setLineWrap(true);
        textArea1.setEditable(false);
        leftPanel.add(textArea1, BorderLayout.PAGE_START);

        //lower panel with text entering and buttons
        JPanel lowerTextPanel = new JPanel();
        JTextArea textArea2 = new JTextArea(5, 40);
        textArea2.setLineWrap(true);
        JButton sendButton = new JButton("Send");
        JButton addPicButton = new JButton("add picture");
        sendButton.addActionListener(e -> System.out.println("send"));
        addPicButton.addActionListener(e -> System.out.println("add pic"));


        lowerTextPanel.add(textArea2);
        lowerTextPanel.add(sendButton);
        lowerTextPanel.add(addPicButton);
        leftPanel.add(lowerTextPanel, BorderLayout.PAGE_END);

        //Online list
        onlineListPanel = new JPanel(new GridLayout(30, 1));
        JLabel onlineTitle = new JLabel("Online:");
        onlineListPanel.add(onlineTitle);
        rightPanel.add(onlineListPanel);

        //Contacts checkbox list
        contactsListPanel = new JPanel(new GridLayout(30, 1));
        JLabel contactTitle = new JLabel("Contacts:");
        contactsListPanel.add(contactTitle);
        rightPanel.add(contactsListPanel);

        addOnline("theo");
        addOnline("hora");

        System.out.println(getListofOnlineReceivers().get(0));

        //login frame
        JFrame loginframe = new JFrame("Login");
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginframe.setSize(300, 200);
        loginframe.setLayout(new GridLayout(4, 0));
        loginframe.setResizable(true);
        loginframe.setLocationRelativeTo(null);
        JLabel usernameTitle = new JLabel("Username:");
        usernameTitle.setFont(new Font("Arial", Font.BOLD, 18));
        JTextField usernameField = new JTextField("Hitler", 10);
        usernameField.setFont(new Font("Arial", Font.BOLD, 18));
        JButton loginButton = new JButton("Log in");
        JButton setPicButton = new JButton("Set picture");
        loginframe.add(usernameTitle);
        loginframe.add(usernameField);
        loginframe.add(setPicButton);
        loginframe.add(loginButton);
        loginframe.setVisible(true);


        //Actionlisteners for buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                username = usernameField.getText();
                loginframe.setVisible(false);
                frame.setTitle("DragonChatter - User: " + username);
                frame.setVisible(true);
                System.out.println("Client logged in with username: " + username);
            }
        });

        setPicButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("C:/Users/Dragon/IdeaProjects/DragonChatter");
                int result = fileChooser.showOpenDialog(fileChooser);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        userPicture = new ImageIcon(ImageIO.read(selectedFile));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

    }

    public void addContact(String title) {
        JCheckBox newBox = new JCheckBox(title);
        newBox.setName(title);
        onlineList.add(newBox);
        contactsListPanel.add(newBox);
    }

    public void addOnline(String title) {
        JCheckBox newBox = new JCheckBox(title);
        newBox.setName(title);
        newBox.setSelected(true);
        onlineList.add(newBox);
        onlineListPanel.add(newBox);
    }

    public void removeOnline(String title) {
        for (Component c : onlineListPanel.getComponents()) {
            if (c.getName() == title) {
                onlineListPanel.remove(c);
            }
        }
    }

    public ArrayList<String> getListofOnlineReceivers() {
        ArrayList<String> receivers = new ArrayList<>();
        for (JCheckBox b : onlineList) {
            if (b.isSelected()) {
                receivers.add(b.getName());
            }
        }
        return receivers;
    }

    public ArrayList<String> getListofContactReceivers() {
        ArrayList<String> receivers = new ArrayList<>();
        for (JCheckBox b : contactList) {
            if (b.isSelected()) {
                receivers.add(b.getName());
            }
        }
        return receivers;
    }

    public String getUsername(){
        return username;
    }

    public ImageIcon getUserPic(){
        return userPicture;
    }


}
