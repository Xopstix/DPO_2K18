package Vistas;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xaviamorcastillo on 12/3/18.
 */
public class VistaPrincipal extends JFrame {

    private JButton jbNew;
    private JButton jbUser;
    private JScrollPane jsc1;
    private JScrollPane jsc2;

    public VistaPrincipal(){

        JButton jbNew = new JButton("New Project");
        JButton jbUser = new JButton("User");

        JPanel jpButtons = new JPanel(new BorderLayout());

        jpButtons.add(jbNew, BorderLayout.LINE_START);
        jpButtons.add(jbUser, BorderLayout.LINE_END);

        //JList<JButton> userProjects = new JList<JButton>();     //Clase que contendra la info de la DB
        //JList<JButton> sharedProjects = new JList<JButton>();

        String[] data = {"onebshdhsdnsadjnkdasknjasdjnkasdknjasdknjasknjasdnjkasdnjkasdknjasdnjkasdnjkasdknjas", "two", "three", "four", "one", "two", "three", "four", "one", "two", "three", "four", "one", "two", "three", "four"};
        String[] data2 = {"onejhnzxnjkx<njkdsknjakndasnjasddasnjdsanjdasndasnjdasnjasdnjdsanjkdasnjkasdnjsadnjk", "two", "three", "four", "one", "two", "three", "four", "one", "two", "three", "four", "one", "two", "three", "four"};
        JList<String> myList = new JList<String>(data);
        JList<String> myList2 = new JList<String>(data2);

        JScrollPane jsc1 = new JScrollPane(myList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane jsc2 = new JScrollPane(myList2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        jsc1.setBorder(BorderFactory.createTitledBorder("Your Projects"));
        jsc2.setBorder(BorderFactory.createTitledBorder("Shared Projects"));

        JPanel jpLists = new JPanel(new GridLayout(1,2));

        jpLists.add(jsc1);
        jpLists.add(jsc2);

        this.getContentPane().add(jpLists, BorderLayout.CENTER);
        this.getContentPane().add(jpButtons, BorderLayout.NORTH);

        this.setSize(900, 300);
        this.setTitle("LSOrganizer");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
