package views;

import controlador.ClientController;
import model.ProjectManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
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
    private JScrollPane jScrollPane;
    private JList<String> userColumns;

    private ProjectManager projectManager;

    public VistaTest(){

        initComponents();
        initVista();
        initDragDrop();
        initListeners();
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

        userColumns = new JList<>();     //Clase que contendra la info de la DB
        userColumns.setFixedCellHeight(35);
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

            //JPanel scrollable = new JPanel();
            //scrollable.setLayout(new BoxLayout(scrollable, BoxLayout.Y_AXIS));
            auxPanel.setMaximumSize(new Dimension(200, 500));
            auxPanel.setMinimumSize(new Dimension(200,400));

            jScrollPane = new JScrollPane(userColumns);
            //jScrollPane.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.RIGHT, TitledBorder.TOP, new Font("Arial",Font.PLAIN,12), Color.WHITE));
            jScrollPane.getVerticalScrollBar().setOpaque(false);
            jScrollPane.setOpaque(false);
            jScrollPane.getViewport().setOpaque(false);
            jScrollPane.setMaximumSize(new Dimension(200, userColumns.getModel().getSize()* 35));
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

        System.out.println(userColumns.getName());

    }

    private void initDragDrop() {

        userColumns.setDragEnabled(true);
        userColumns.setDropMode(DropMode.INSERT);
        userColumns.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        userColumns.setTransferHandler(new TransferHandler() {

            int index;
            boolean beforeIndex = false;

            @Override
            public int getSourceActions(JComponent comp) {
                return COPY_OR_MOVE;
            }

            @Override
            public Transferable createTransferable(JComponent comp) {
                index = userColumns.getSelectedIndex();
                return new StringSelection((String) userColumns.getSelectedValue());
            }

            @Override
            public void exportDone(JComponent comp, Transferable trans, int action) {
                if (action == MOVE) {
                    if (beforeIndex) {
                        dataUser.remove(index + 1);
                    } else {
                        dataUser.remove(index);
                    }
                    jScrollPane.updateUI();
                }
            }

            @Override
            public boolean canImport(TransferHandler.TransferSupport support) {
                // Data =? String
                return support.isDataFlavorSupported(DataFlavor.stringFlavor);
            }

            @Override
            public boolean importData(TransferHandler.TransferSupport support) {
                try {
                    // Data to String
                    String s = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
                    dataUser.add(dl.getIndex(), s);
                    beforeIndex = dl.getIndex() < index ? true : false;
                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                }
                return false;
            }
        });
    }

    private void jListUserValueChanged(javax.swing.event.ListSelectionEvent evt) {
        //set text on right here
        String s = (String) userColumns.getSelectedValue();
    }

    private void initListeners() {

        //Action Listeners de las dos listas, User y Shared -- Más abajo están los procedimientos a seguir
        userColumns.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                jListUserValueChanged(evt);
            }
        });
    }

    public void registerController(ClientController controllerClient) {

        jbNew.setActionCommand("NEW_PROJECT");
        jbNew.addActionListener(controllerClient);
    }
}
