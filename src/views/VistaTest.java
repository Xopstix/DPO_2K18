package views;

import controlador.ClientController;
import controlador.CustomListSelectionListener;
import controlador.CustomTransferHandler;
import model.ProjectManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xaviamorcastillo on 17/4/18.
 */
public class VistaTest extends JFrame{

    private DefaultListModel<String> dataUser;

    private JButton jbNew;
    private JButton jbUser;
    private JList<String> stringsUser;
    private ArrayList<JList<String>> userColumns;
    private CustomListSelectionListener customListSelectionListener;

    private ProjectManager projectManager;

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

        projectManager = new ProjectManager();

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

        userColumns = new ArrayList<>();

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

            //JPanel scrollable = new JPanel();
            //scrollable.setLayout(new BoxLayout(scrollable, BoxLayout.Y_AXIS));
            auxPanel.setMaximumSize(new Dimension(200, 500));
            auxPanel.setMinimumSize(new Dimension(200,400));

            JList<String> userColumn = new JList<>(dataUser);     //Clase que contendra la info de la DB
            userColumn.setFixedCellHeight(35);
            userColumn.setOpaque(false);
            userColumn.setCellRenderer(new TransparentListCellRenderer());

            userColumn.setName(i+"");
            System.out.println(userColumn.getName());

            userColumn.setModel(new AbstractListModel() {

                @Override
                public int getSize() {
                    return stringsUser.getModel().getSize();
                }

                @Override
                public Object getElementAt(int i) {
                    return stringsUser.getModel().getElementAt(i);
                }
            });
            userColumns.add(userColumn);

            JScrollPane jScrollPane = new JScrollPane(userColumn);
            //jScrollPane.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.RIGHT, TitledBorder.TOP, new Font("Arial",Font.PLAIN,12), Color.WHITE));
            jScrollPane.getVerticalScrollBar().setOpaque(false);
            jScrollPane.setOpaque(false);
            jScrollPane.getViewport().setOpaque(false);
            jScrollPane.setMaximumSize(new Dimension(200, userColumn.getModel().getSize()* 35));
            jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            auxPanel.add(jScrollPane);
            initDragDrop(userColumn, jScrollPane);
            //initListeners(userColumn);

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

        //System.out.println(userColumns.getName());
        //getSource

    }

    private void initDragDrop(JList<String> userColumn, JScrollPane jScrollPane) {

        userColumn.setDragEnabled(true);
        userColumn.setDropMode(DropMode.INSERT);
        userColumn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        CustomTransferHandler customTransferHandler = new CustomTransferHandler(userColumn, dataUser, jScrollPane);
        userColumn.setTransferHandler(customTransferHandler);
    }

    /*private void jListUserValueChanged(ListSelectionEvent evt, JList<String> userColumn) {
        //set text on right here
        String s = (String) userColumn.getSelectedValue();
    }

    private void initListeners(JList<String> userColumn) {

        //Action Listeners de las dos listas, User y Shared -- Más abajo están los procedimientos a seguir
        userColumn.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                jListUserValueChanged(evt, userColumn);
            }
        });
    }*/

    public void initPopupTasca(int columna, int fila){

        JPopupMenu popup = new JPopupMenu();

        JMenuItem deleteButton = new JMenuItem("Delete", new ImageIcon(((new ImageIcon("icons/delete_icon.png"))
                .getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        deleteButton.setMnemonic(KeyEvent.VK_P);
        deleteButton.getAccessibleContext().setAccessibleDescription("Delete task");

        deleteButton.setMinimumSize(new Dimension(200,50));
        deleteButton.setMaximumSize(new Dimension(200,50));

        JTextField nameTextField = new JTextField("Nou nom");

        nameTextField.setMinimumSize(new Dimension(200,50));
        nameTextField.setMaximumSize(new Dimension(200,50));

        JColorChooser colorChooser = new JColorChooser(Color.WHITE);
        colorChooser.setBorder(null);
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                //colorChanged(); // change background color of "button"
                System.out.println("Hola");
            }
        });

        colorChooser.setPreviewPanel(new JPanel());

        AbstractColorChooserPanel panels[] = { new ColorChooserPanel() };
        colorChooser.setChooserPanels(panels);

        colorChooser.setMaximumSize(new Dimension(200, 40));

        popup.add(nameTextField);
        popup.add(deleteButton);
        popup.add(colorChooser);

        popup.setPopupSize(new Dimension(200,300));
        popup.show(this, columna * 200 + 25, fila * 35 + 120);
    }

    public void registerController(ClientController controllerClient, CustomListSelectionListener listSelectionListener) {

        jbUser.setActionCommand("POPUP_PANEL");
        jbUser.addActionListener(controllerClient);

        jbNew.setActionCommand("NEW_PROJECT");
        jbNew.addActionListener(controllerClient);

        for (int i = 0; i < userColumns.size(); i++){
            userColumns.get(i).addListSelectionListener(listSelectionListener);
        }
    }
}
