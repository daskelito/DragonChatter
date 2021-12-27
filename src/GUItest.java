import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUItest {


    public GUItest(){
        JFrame frame = new JFrame("DragonChatter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,700);
        frame.setLayout(new BorderLayout());
        frame.setResizable(true);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setSize(700, 700);
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setSize(300, 700);

        frame.add(leftPanel, BorderLayout.LINE_START);
        frame.add(rightPanel, BorderLayout.LINE_END);

        //Text area for chat history
        JTextArea textArea1 = new JTextArea(33, 40);
        textArea1.append("chat history here");
        textArea1.setLineWrap(true);
        textArea1.setEditable(false);
        leftPanel.add(textArea1, BorderLayout.PAGE_START);

        //lower panel with text entering and buttons
        JPanel lowerTextPanel = new JPanel();
        JTextArea textArea2 = new JTextArea(5, 40);
        textArea2.setLineWrap(true);
        JButton sendButton = new JButton("Send");
        lowerTextPanel.add(textArea2);
        lowerTextPanel.add(sendButton);
        leftPanel.add(lowerTextPanel, BorderLayout.PAGE_END);

        //Online list
        JPanel onlineList = new JPanel(new BorderLayout());
        JLabel onlineTitle = new JLabel("Contacts:");
        JCheckBox online1 = new JCheckBox("person 1");
        onlineList.add(online1, BorderLayout.PAGE_START);
        rightPanel.add(onlineList, BorderLayout.LINE_START);


        //Contacts checkbox list
        JPanel contactsList = new JPanel(new GridLayout(30, 1));
        JLabel contactTitle = new JLabel("Contacts:");
        JCheckBox contact1 = new JCheckBox("Contact 1");
        JCheckBox contact2 = new JCheckBox("Contact 2");
        JCheckBox contact3 = new JCheckBox("Contact 3");
        contactsList.add(contactTitle);
        contactsList.add(contact1);
        contactsList.add(contact2);
        contactsList.add(contact3);

        rightPanel.add(contactsList, BorderLayout.LINE_END);




        frame.setVisible(true);

    }
}
