package views;

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

        JPanel panel = new JPanel(new GridBagLayout());

    }

    private void initVista() {

        File image = new File("/Users/xaviamorcastillo/Desktop/DPO_2K18/images/bg1.jpg");

        JPanel panel = new JPanel(new GridBagLayout()) {
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

        getContentPane().add(panel);
    }
}
