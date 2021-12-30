package ServerPackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class ServerGui extends JFrame implements ActionListener {
    private final JList<String> list;
    private final JTextField firstTime;
    private final JTextField lastTime;
    private final Server server;

    public ServerGui(Server server) throws HeadlessException {
        super("Server log");
        this.server = server;
        JPanel mainPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        list = new JList<String>();
        JScrollPane s = new JScrollPane(list);
        firstTime = new JTextField("0:0");
        JLabel firstLabel = new JLabel("Start time");
        lastTime = new JTextField("23:59");
        JLabel lastLabel = new JLabel("Last time");
        JButton buttonFind = new JButton("Find");
        mainPanel.setPreferredSize(new Dimension(400,700));
        inputPanel.setPreferredSize(new Dimension(400,300));
        lastTime.setPreferredSize(new Dimension(300,100));
        firstTime.setPreferredSize(new Dimension(300,100));
        buttonFind.setPreferredSize(new Dimension(300,50));
        buttonFind.addActionListener(this);
        s.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        s.setPreferredSize(new Dimension(400, 500));
        s.setHorizontalScrollBar(null);
        s.setBorder(BorderFactory.createEmptyBorder());
        s.setWheelScrollingEnabled(true);
        inputPanel.add(firstLabel);
        inputPanel.add(firstTime);
        inputPanel.add(lastLabel);
        inputPanel.add(lastTime);
        inputPanel.add(buttonFind);
        mainPanel.add(s);
        mainPanel.add(inputPanel);
        setPreferredSize(new Dimension(440,860));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        add(mainPanel);
        pack();
        setVisible(true);
    }
    public void filter(){
        String[] f = firstTime.getText().split(":");
        String[] l = lastTime.getText().split(":");
        int[] fin = new int[f.length];
        int[] lin = new int[l.length];
        for(int x = 0; x<l.length; x++){
            fin[x] = Integer.parseInt(f[x]);
            lin[x] = Integer.parseInt(l[x]);
            System.out.println("fin["+x+"]:"+fin[x]);
            System.out.println("lin["+x+"]:"+lin[x]);
        }
        Timestamp first = new Timestamp(2021,3,24,fin[0],fin[1],0,0);
        Timestamp last = new Timestamp(2021,3,24,lin[0],lin[1],0,0);
        System.out.println("first: "+ first.getHours());
        System.out.println("last: "+ last.getHours());
        //server.filter(first,last);
    }
    public void setList(String[] tempArray){
        list.setListData(tempArray);
        list.updateUI();
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        filter();
    }
}
