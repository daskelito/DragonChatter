import javax.swing.*;
import java.awt.*;

public class GUItest {


    public GUItest(){
        JFrame frame = new JFrame("dlkjohftg");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,700);
        frame.setLayout(new FlowLayout());
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea(20, 50);
        textArea.append("hi");
        JButton button = new JButton("fitta");
        JPanel listpanel = new JPanel();


        frame.add(textArea);
        frame.add(button);



        frame.setVisible(true);

    }
}
