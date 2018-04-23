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
        dataUser.addElement("Item4");

        stringsUser = new JList<String>(dataUser);

        jbNew = new JButton("New Project");

        jbUser = new JButton("User");

    }

    private void initVista() {

        File image = new File("images/bg1.jpg");

        JPanel boxPanel = new JPanel() {
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

        JPanel jpButtons = new JPanel(new BorderLayout());
        jpButtons.setOpaque(false);
        jpButtons.add(jbNew, BorderLayout.LINE_START);
        jpButtons.add(jbUser, BorderLayout.LINE_END);

        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.X_AXIS));
        //boxPanel.setPreferredSize(new Dimension(1200, 600));
        //boxPanel.setMaximumSize(new Dimension(1200 , 600));

        for (int i = 0; i < dataUser.size(); i++){

            JPanel auxPanel = new JPanel();
            auxPanel.setLayout(new BoxLayout(auxPanel, BoxLayout.Y_AXIS));

            JPanel titlePanel = new JPanel(new FlowLayout());
            titlePanel.setMaximumSize(new Dimension(150,50));

            JButton nameButton = new JButton(dataUser.get(i));
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

            titlePanel.add(nameButton);
            titlePanel.add(deleteButton);

            auxPanel.add(titlePanel);

            for (int j = 0; j < dataUser.size(); j++) {

                JButton auxButton2 = new JButton(dataUser.get(j));
                auxButton2.setBorderPainted(false);
                auxButton2.setAlignmentY(boxPanel.LEFT_ALIGNMENT);
                //auxButton2.setBorder(BorderFactory.createEmptyBorder(10,5,10,0));
                auxPanel.add(auxButton2);
            }

            JTextField auxTextField = new JTextField("Afegeix tasca...");
            auxTextField.setPreferredSize(new Dimension(200,25));
            auxTextField.setMaximumSize(new Dimension(200,25));

            auxPanel.add(auxTextField);
            auxPanel.setPreferredSize(new Dimension(200, 400));
            auxPanel.setMaximumSize(new Dimension(200, 400));
            //auxPanel.setBorder((BorderFactory.createEmptyBorder(20,20,0,0)));
            auxPanel.setAlignmentY(boxPanel.TOP_ALIGNMENT);

            boxPanel.add(auxPanel);
        }

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.add(jpButtons, BorderLayout.NORTH);
        totalPanel.add(boxPanel, BorderLayout.CENTER);

        getContentPane().add(totalPanel);
    }

    public void registerController(ClientController controllerClient) {

        jbNew.setActionCommand("NEW_PROJECT");
        jbNew.addActionListener(controllerClient);

    }
}
