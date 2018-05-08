package views;

import controlador.ClientController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by xaviamorcastillo on 17/4/18.
 */
public class VistaTest extends JFrame{

    private DefaultListModel<String> dataUser;

    private JButton jbNew;
    private JButton jbUser;
    private JList<String> stringsUser;

    public VistaTest(){

        initComponents();
        initVista();
        this.setSize(1200, 600);
        this.setTitle("ProjectManager");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {

        //Habrá que coger de la base de datos los panels e irlos creando
        //De momento hecho con listas de strings, reaprovechamos nombres

        dataUser = new DefaultListModel<>();
        dataUser.addElement("Item1");
        dataUser.addElement("Item2");
        dataUser.addElement("Item3");
        dataUser.addElement("Item3");
        dataUser.addElement("Item3");
        dataUser.addElement("Item3");
        dataUser.addElement("Item3");
        dataUser.addElement("Item3");
        dataUser.addElement("Item3");
        dataUser.addElement("Item3");
        dataUser.addElement("Item3");
        dataUser.addElement("Item3");

        stringsUser = new JList<String>(dataUser);

        jbNew = new JButton("New Project");
        jbUser = new JButton("User");

    }

    private void initVista() {

        this.setResizable(false);

        JPanel totalPanel;

        File image = new File("images/bg1.jpg");

        totalPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(image),0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        JPanel boxPanel = new JPanel(new BorderLayout());

        JPanel jpButtons = new JPanel(new BorderLayout());
        jpButtons.setOpaque(false);
        jpButtons.add(jbNew, BorderLayout.LINE_START);
        jpButtons.add(jbUser, BorderLayout.LINE_END);

        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.X_AXIS));

        for (int i = 0; i < dataUser.size(); i++){

            JPanel auxPanel = new JPanel();
            auxPanel.setLayout(new BoxLayout(auxPanel, BoxLayout.Y_AXIS));

            JPanel titlePanel = new JPanel(new FlowLayout());
            titlePanel.setMaximumSize(new Dimension(150,50));

            JButton nameButton = new JButton(dataUser.get(i));
            nameButton.setForeground(Color.WHITE);
            nameButton.setBorderPainted(false);
            JButton deleteButton = new JButton();
            deleteButton.setBorderPainted(false);

            try {
                Image img = ImageIO.read(new File("icons/delete_icon.png")).
                        getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                deleteButton.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println(ex);
            }

            titlePanel.setOpaque(false);
            titlePanel.add(nameButton);
            titlePanel.add(deleteButton);

            auxPanel.setOpaque(false);
            auxPanel.add(titlePanel, BorderLayout.NORTH);

            JPanel scrollable = new JPanel();
            scrollable.setLayout(new BoxLayout(scrollable, BoxLayout.Y_AXIS));
            auxPanel.setMaximumSize(new Dimension(200, 500));
            auxPanel.setMinimumSize(new Dimension(200,400));

            JList userColumns = new JList<>();     //Clase que contendra la info de la DB
            userColumns.setFixedCellHeight(25);
            userColumns.setOpaque(false);
            userColumns.setCellRenderer(new TransparentListCellRenderer());

            userColumns.setModel(new AbstractListModel() {

                @Override
                public int getSize() {
                    return stringsUser.getModel().getSize();
                }

                @Override
                public Object getElementAt(int i) {
                    return stringsUser.getModel().getElementAt(i);
                }
            });

            JScrollPane jScrollPane = new JScrollPane(userColumns);
            //jScrollPane.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.RIGHT, TitledBorder.TOP, new Font("Arial",Font.PLAIN,12), Color.WHITE));
            jScrollPane.getVerticalScrollBar().setOpaque(false);
            jScrollPane.setOpaque(false);
            jScrollPane.getViewport().setOpaque(false);
            jScrollPane.setMinimumSize(new Dimension(200, dataUser.size()* 40));
            jScrollPane.setMaximumSize(new Dimension(200, dataUser.size()* 40));
            jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            auxPanel.add(jScrollPane);

            JTextField auxTextField = new JTextField("Afegeix tasca...");
            auxTextField.setPreferredSize(new Dimension(200,25));
            auxTextField.setMaximumSize(new Dimension(200,25));

            auxPanel.add(auxTextField);
            JButton jbaux = new JButton("Afegeix");
            jbaux.setName(i+"");

            auxPanel.setPreferredSize(new Dimension(200, 530));
            auxPanel.setMaximumSize(new Dimension(200, 530));
            auxPanel.setBorder((BorderFactory.createEmptyBorder(20,20,0,0)));
            auxPanel.setAlignmentY(boxPanel.TOP_ALIGNMENT);

            boxPanel.add(auxPanel);
            boxPanel.setOpaque(false);
        }


        JScrollPane jScrollPane2 = new JScrollPane(boxPanel);
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setOpaque(false);

        totalPanel.add(jpButtons, BorderLayout.NORTH);
        totalPanel.add(jScrollPane2, BorderLayout.CENTER);

        getContentPane().add(totalPanel);

        /*JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        File image = new File("images/bg1.jpg");

        box = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(image),0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        getContentPane().add(box);
        */

    }

    public void registerController(ClientController controllerClient) {

        jbNew.setActionCommand("NEW_PROJECT");
        jbNew.addActionListener(controllerClient);

    }
}
