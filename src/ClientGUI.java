import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame{
    private JPanel panel1;
    private JButton Send;
    private JTextField textField1;
    private JTextArea textArea1;
    private JButton Connect;

    public ClientGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        this.setLocation(300,300);
        this.setSize(500,400);
        this.setVisible(true);

        Connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
