package ServerPackage;

import Message.ChatMessage;
import Message.Message;
import Message.InfoMessage;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * log is a class that log all traffic that
 * happens on the server in both a message and string format.
 *
 * @author Elliot Lind Palmstadius
 * @version 1.0
 *
 */
public class Logger {
    private ArrayList<String> showList;
    private ArrayList<Message> list;
    private HashMap<String,ArrayList<Message>> logHash;
    private final Server server;

    public Logger(Server server) {
        this.server = server;
        loadFromFile();
    }
    public String[] addToLog(Message message){
        StringWriter stringWriter = new StringWriter();
        list.add(message);
        if (message instanceof ChatMessage){
            
            stringWriter.append("Chat Message,");
            stringWriter.append(((ChatMessage) message).getSender()).append(" Sending data to ");
            for (String username:((ChatMessage) message).getReceivers()) {
                stringWriter.append(username);
                stringWriter.append(" ");
                if(!logHash.containsKey((username))){
                    logHash.put(username,new ArrayList<>());
                }
                logHash.get(username).add(message);
            }
            
        }
        else if(message instanceof InfoMessage){
            stringWriter.append("Info Message, ");
            stringWriter.append(message.getText());
        }
        String time = " || "+message.getTimestamp();
        stringWriter.append(time);
        showList.add(stringWriter.toString());
        String[] tempArray = new String[showList.size()];
        int count = 0;
        for(String s : showList){
            tempArray[count] = showList.get(count);
            count++;
        }
        saveToFile();
        return tempArray;
    }
    public ArrayList<Message> getMessageList(String username) {
        return logHash.get(username);
    }
    /**
     * A method for filtering message within a time span.
     *
     * TODO: Make it work.
     * @param first start time
     * @param last stop time
     */
    /*
    public String[] filter(Timestamp first, Timestamp last){
        int f = 0;
        int l = 0;
        for(int i = 0; i<list.size(); i++){
            if (list.get(i).getTimeSent().getTime() > first.getTime()){
                f = i;
                while(list.get(i).getTimeSent().getTime() < last.getTime()||i<list.size()){
                    i++;
                }
                l = i;
                break;
            }
        }
        System.out.println(l-f);
        String[] temparray=new String[l-f];
        for(int i = f; i<l;i++){
            temparray[f-i] = showList.get(i);
        }
        return temparray;
    }

     */

    public synchronized void saveToFile(){
        try {
            File fileList = new File("server/logShowList.dat");
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileList)));
            oos.writeObject(showList);
            oos.flush();
            oos.close();
            File fileHash = new File("server/logHash.dat");
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileHash)));
            oos.writeObject(logHash);
            oos.flush();
            oos.close();
            File filelistMessage = new File("server/logList.dat");
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filelistMessage)));
            oos.writeObject(list);
            oos.flush();
            oos.close();

        }catch (IOException e) {
            System.out.println("can't write to file");
        }
    }
    public synchronized void loadFromFile(){
        try {
            ObjectInputStream oisList = new ObjectInputStream(new BufferedInputStream(new FileInputStream("server/logList.dat")));
            showList = (ArrayList<String>) oisList.readObject();
            String[] tempArray = new String[showList.size()];
            for(int i = 0; i< showList.size(); i++){
                tempArray[i] = showList.get(i);
            }
            server.setLog(tempArray);
            
            ObjectInputStream oisHash = new ObjectInputStream(new BufferedInputStream(new FileInputStream("server/logHash.dat")));
            logHash = (HashMap<String, ArrayList<Message>>) oisHash.readObject();

            ObjectInputStream oisMessageList = new ObjectInputStream(new BufferedInputStream(new FileInputStream("server/logListMessage.dat")));
            list = (ArrayList<Message>) oisMessageList.readObject();
            oisMessageList.close();
            oisHash.close();
            oisList.close();


        }
        catch (ClassNotFoundException | IOException e) {
            logHash = new HashMap<String,ArrayList<Message>>();
            showList = new ArrayList<String>();
            list = new ArrayList<>();
            System.out.println("No log files found");
        }
    }
}
