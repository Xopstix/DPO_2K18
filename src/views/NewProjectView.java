package views;

import javax.swing.*;
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

        jlProjectName = new JLabel("Insert a Name for your project:");
        jtfProjectName = new JTextField("Project name");
        jtfProjectName.setColumns(10);

        jlContributors = new JLabel("Select the contributors to this project:");
        // Esta lista habra que cogerla de la base de datos directamente
        data = new DefaultListModel<>();
        data.addElement("Item1");
        data.addElement("Item2");
        data.addElement("Item3");
        data.addElement("Item4");

        contributors = new JList<>(data);

        jscContributors = new JScrollPane();
        jscContributors.add(contributors);
        jscContributors.setPreferredSize(new Dimension(300,100));

        jbCreate = new JButton("Create");
        jbCancel = new JButton("Cancel");

        JPanel jpName = new JPanel(new GridLayout(2,1));
        jpName.add(jlProjectName);
        jpName.add(jtfProjectName);

        JPanel jpContributors = new JPanel(new BorderLayout());
        jpContributors.add(jlContributors, BorderLayout.NORTH);
        jpContributors.add(jscContributors, BorderLayout.CENTER);

        JPanel jpAux = new JPanel(new GridLayout(3,1));
        jpAux.add(jpName);
        jpAux.add(jpContributors);

        getContentPane().add(jpAux);

        this.setSize(600, 250);
        this.setTitle("MarksManagement");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
