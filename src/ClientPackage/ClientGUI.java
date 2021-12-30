package ClientPackage;

import ClientPackage.Client;
import Message.Message;
import Message.ChatMessage;
import User.User;
import javax.imageio.ImageIO;
import javax.swing.*;
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
    private JTextArea textArea1;
    private ImageIcon userPicture;
    private JLabel userPic;
    private String username;
    private ArrayList<JCheckBox> onlineList;
    private ArrayList<JCheckBox> contactList;
    private Client client;
    private boolean isPicAttached;
    private ImageIcon attachedPic;

    public ClientGUI(Client client) {
        this.client = client;
        onlineList = new ArrayList<>();
        contactList = new ArrayList<>();
        userPicture = new ImageIcon("C:\\Users\\Dragon\\IdeaProjects\\DragonChatter\\default.png");
        isPicAttached = false;

        frame = new JFrame("DragonChatter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1150, 600);
        frame.setLayout(new BorderLayout());
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel leftPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel(new GridLayout(1, 2));
        frame.add(leftPanel, BorderLayout.LINE_START);
        frame.add(rightPanel, BorderLayout.LINE_END);

        //Text area for chat history
        textArea1 = new JTextArea(25, 40);
        textArea1.setLineWrap(true);
        textArea1.setEditable(false);
        leftPanel.add(textArea1, BorderLayout.PAGE_START);

        //lower panel with text entering and buttons
        JPanel lowerTextPanel = new JPanel();
        JTextArea textArea2 = new JTextArea(5, 40);
        textArea2.setLineWrap(true);
        JButton sendButton = new JButton("Send");
        JButton addPicButton = new JButton("Attach picture");
        JButton addContactsButton = new JButton(("Add contacts"));
        Image resizedUserPic = userPicture.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        userPicture = new ImageIcon(resizedUserPic);
        userPic = new JLabel(new ImageIcon(resizedUserPic));

        lowerTextPanel.add(userPic);
        lowerTextPanel.add(textArea2);
        lowerTextPanel.add(sendButton);
        lowerTextPanel.add(addPicButton);
        lowerTextPanel.add(addContactsButton);

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


        //login frame
        JFrame loginframe = new JFrame("Login");
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginframe.setSize(300, 200);
        loginframe.setLayout(new GridLayout(4, 0));
        loginframe.setResizable(true);
        loginframe.setLocationRelativeTo(null);
        JLabel usernameTitle = new JLabel("Username:");
        usernameTitle.setFont(new Font("Arial", Font.BOLD, 18));
        JTextField usernameField = new JTextField("Henrik", 10);
        usernameField.setFont(new Font("Arial", Font.BOLD, 18));
        JButton loginButton = new JButton("Log in");
        JButton setPicButton = new JButton("Set picture");
        loginframe.add(usernameTitle);
        loginframe.add(usernameField);
        loginframe.add(setPicButton);
        loginframe.add(loginButton);
        loginframe.setVisible(true);


        //Actionlisteners for buttons
        //Login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                username = usernameField.getText();
                loginframe.setVisible(false);
                frame.setTitle("DragonChatter - User.User: " + username);
                frame.setVisible(true);
                System.out.println("Client: Client logged in with username: " + username);
                client.setCurrentUser(new User(username, userPicture));
                try {
                    client.connect(5252);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        //Set picture button
        setPicButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("D:\\Datorn\\Documents\\Skola\\Trådar och Datakomunikation\\DragonChatter-master");
                int result = fileChooser.showOpenDialog(fileChooser);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        userPicture = new ImageIcon(ImageIO.read(selectedFile));
                        Image resizedUserPic = userPicture.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
                        userPic.setIcon(new ImageIcon(resizedUserPic));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        //Send button
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isPicAttached) {
                    client.sendPicMessage(getListofReceivers(), textArea2.getText(), attachedPic);
                    isPicAttached = false;
                } else {
                    client.sendChatMessage(getListofReceivers(), textArea2.getText());
                }
                textArea2.setText("");
            }
        });


        //Attach picture button
        addPicButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("D:\\Datorn\\Documents\\Skola\\Trådar och Datakomunikation\\DragonChatter-master");
                int result = fileChooser.showOpenDialog(fileChooser);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        attachedPic = new ImageIcon(ImageIO.read(selectedFile));
                        isPicAttached = true;
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        //Add contacts button
        addContactsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (JCheckBox obox : onlineList) {
                    if (obox.isSelected()) {
                        boolean willAdd = true;
                        for (JCheckBox cbox : contactList) {
                            if (obox.getName() == cbox.getName()) {
                                willAdd = false;
                            }
                        }
                        if (willAdd) addContact(obox.getName());
                        client.addContact(obox.getName());
                    }
                }
                contactsListPanel.repaint();
                SwingUtilities.updateComponentTreeUI(frame);
            }
        });

    }

    public void displayMessage(ChatMessage message){
        String textToDisplay = "";
        textToDisplay += message.getTimestamp().toString();
        textToDisplay += " - ";
        textToDisplay += message.getSender();
        textToDisplay += ": ";
        textToDisplay += message.getText();
        textToDisplay += "\n";
        textArea1.append(textToDisplay);

       //SwingUtilities.updateComponentTreeUI(frame);
    }

    public void addContact(String title) {
        boolean willAdd = true;
        for (JCheckBox cbox : contactList) {
            if (title.equals(cbox.getName())) {
                willAdd = false;
            }
        }
        if (willAdd){
        JCheckBox newBox = new JCheckBox(title);
        newBox.setName(title);
        contactList.add(newBox);
        contactsListPanel.add(newBox);
        SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    public void addOnline(String title) {
        boolean willAdd = true;
        for (JCheckBox obox : onlineList) {
            if (title.equals(obox.getName())) {
                willAdd = false;
            }
        }
        if (willAdd){
            System.out.println("ChatGui:addOnline:" + title);
            JCheckBox newBox = new JCheckBox(title);
            newBox.setName(title);
            onlineList.add(newBox);
            onlineListPanel.add(newBox);
            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    public void removeOnline(String title) {
        for (Component c : onlineListPanel.getComponents()) {
            if (c.getName() == title) {
                onlineListPanel.remove(c);
            }
        }
    }

    public ArrayList<String> getListofReceivers() {
        ArrayList<String> receivers = new ArrayList<>();
        for (JCheckBox b : onlineList) {
            if (b.isSelected()) {
                receivers.add(b.getName());
            }
        }
        for (JCheckBox b : contactList) {
            if (b.isSelected()) {
                receivers.add(b.getName());
            }
        }
        return receivers;
    }


    public String getUsername() {
        return username;
    }

    public ImageIcon getUserPic() {
        return userPicture;
    }

    public static void main(String[] args) throws IOException {
        ClientGUI gui = new ClientGUI(new Client(888));
        ChatMessage msg = new ChatMessage("person 1", new ArrayList<String >(), "hello");
        ChatMessage msg2 = new ChatMessage("person 2", new ArrayList<String >(), "hi");
        gui.displayMessage(msg);
        gui.displayMessage(msg2);
    }

}
