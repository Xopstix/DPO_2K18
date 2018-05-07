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


        JPanel boxPanel = new JPanel(new BorderLayout());
        JScrollPane jScrollPane2 = new JScrollPane(boxPanel);
        jScrollPane2.setMaximumSize(new Dimension(600,500));
        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        File image = new File("images/bg1.jpg");

        /*
        boxPanel = new JPanel() {
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
*/

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

            JPanel scrollable = new JPanel();
            scrollable.setLayout(new BoxLayout(scrollable, BoxLayout.Y_AXIS));
            auxPanel.add(titlePanel);
            auxPanel.setMaximumSize(new Dimension(200, 500));
            auxPanel.setMinimumSize(new Dimension(200,400));

            for (int j = 0; j < dataUser.size(); j++) {

                JPanel leftAlignment = new JPanel(new BorderLayout());
                leftAlignment.setMaximumSize(new Dimension(180,40));
                JButton auxButton2 = new JButton(dataUser.get(j));
                auxButton2.setBorderPainted(false);
                leftAlignment.add(auxButton2, BorderLayout.LINE_START);
                auxButton2.setBorder(BorderFactory.createEmptyBorder(10,5,10,0));
                scrollable.add(leftAlignment);
            }


            JScrollPane jScrollPane = new JScrollPane(scrollable);
            //jScrollPane.setMinimumSize(new Dimension(200, dataUser.size()* 42));
            //jScrollPane.setMaximumSize(new Dimension(200, dataUser.size()* 42));
            jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            auxPanel.add(jScrollPane);

            JTextField auxTextField = new JTextField("Afegeix tasca...");
            auxTextField.setPreferredSize(new Dimension(200,25));
            auxTextField.setMaximumSize(new Dimension(200,25));

            auxPanel.add(auxTextField);
            JButton jbaux = new JButton("Afegeix");
            jbaux.setName(i+"");

            //auxPanel.setPreferredSize(new Dimension(200, 530));
            //auxPanel.setMaximumSize(new Dimension(200, 530));
            auxPanel.setBackground(Color.black);
            auxPanel.setBorder((BorderFactory.createEmptyBorder(20,20,0,0)));
            auxPanel.setAlignmentY(boxPanel.TOP_ALIGNMENT);

            boxPanel.add(auxPanel);
        }

        JPanel totalPanel;

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

        totalPanel.add(jpButtons, BorderLayout.NORTH);
        totalPanel.add(boxPanel, BorderLayout.CENTER);

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
