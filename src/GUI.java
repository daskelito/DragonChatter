import jdk.jshell.execution.JdiExecutionControl;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class GUI {
    private JFrame frame;
    private JPanel rightPanel;
    private JPanel onlineListPanel;
    private JPanel contactsListPanel;

    public GUI(){
        frame = new JFrame("DragonChatter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());
        frame.setResizable(true);

        JPanel leftPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel(new GridLayout(1,2));
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
        JButton changePicButton = new JButton("Change picture");
        lowerTextPanel.add(textArea2);
        lowerTextPanel.add(sendButton);
        lowerTextPanel.add(changePicButton);
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

        frame.setVisible(true);

        addOnline("theo");
        addOnline("hora");

        System.out.println(getListofOnlineReceivers().get(0));


    }

    public void addContact(String title){
        JCheckBox newBox = new JCheckBox(title);
        newBox.setName(title);
        contactsListPanel.add(newBox);
    }

    public void addOnline(String title){
        JCheckBox newBox = new JCheckBox(title);
        newBox.setName(title);
        newBox.setSelected(true);
        onlineListPanel.add(newBox);
    }

    public void removeOnline(String title){
        for(Component c: onlineListPanel.getComponents()){
            if(c.getName() == title){
                onlineListPanel.remove(c);
            }
        }
    }


    public ArrayList<String> getListofOnlineReceivers(){
        ArrayList<String> receivers = new ArrayList<>();
        JCheckBox[] boxes = (JCheckBox[]) onlineListPanel.getComponents();
        for(JCheckBox c: boxes){
            if(c.isSelected()){
                receivers.add(c.getName());
            }
        }
        return receivers;
    }

    public ArrayList<String> getListofContactReceivers(){
        ArrayList<String> receivers = new ArrayList<>();
        return null;
    }

}
