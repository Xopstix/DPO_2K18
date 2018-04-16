package views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by xaviamorcastillo on 15/3/18.
 */
public class NewProjectView extends JFrame{

    private JLabel jlProjectName;
    private JTextField jtfProjectName;
    private JLabel jlContributors;
    private JScrollPane jscContributors;
    private JList<String> contributors;
    private JButton jbCreate;
    private JButton jbCancel;

    private DefaultListModel<String> data;

    public NewProjectView(){

        initComponents();
    }

    private void initComponents() {

        jlProjectName = new JLabel("Insert a name for your project:");
        jtfProjectName = new JTextField("Project name");
        jtfProjectName.setColumns(20);

        jlContributors = new JLabel("Contributors:");
        // Esta lista habra que cogerla de la base de datos directamente
        data = new DefaultListModel<>();
        data.addElement("Item1");
        data.addElement("Item2");
        data.addElement("Item3");
        data.addElement("Item4");
        data.addElement("Item5");
        data.addElement("Item6");
        data.addElement("Item7");
        data.addElement("Item8");
        data.addElement("Item9");
        data.addElement("Item10");
        data.addElement("Item11");
        data.addElement("Item12");


        contributors = new JList<>(data);
        contributors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contributors.setLayoutOrientation(JList.VERTICAL);

        jscContributors = new JScrollPane();
        jscContributors.setViewportView(contributors);
        jscContributors.setBorder(BorderFactory.createEmptyBorder());
        jscContributors.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jscContributors.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        jbCreate = new JButton("Create");
        jbCancel = new JButton("Cancel");

        JPanel jpName = new JPanel(new FlowLayout());
        jpName.add(jlProjectName);
        jpName.add(jtfProjectName);

        JPanel jpContributors = new JPanel(new BorderLayout());
        jpContributors.add(jlContributors, BorderLayout.NORTH);
        jpContributors.add(jscContributors, BorderLayout.CENTER);

        JPanel jpButtons = new JPanel(new FlowLayout());
        jpButtons.add(jbCreate);
        jpButtons.add(jbCancel);

        JPanel jpAux = new JPanel(new BorderLayout());
        jpAux.add(jpName, BorderLayout.NORTH);
        jpAux.add(jpContributors, BorderLayout.CENTER);
        jpAux.add(jpButtons, BorderLayout.SOUTH);

        getContentPane().add(jpAux);

        this.setSize(600, 250);
        this.setTitle("MarksManagement");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
