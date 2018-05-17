package views;

import controlador.ClientController;
import controlador.PopupController;
import model.Columna;
import model.Etiqueta;
import model.Project;
import model.Usuari;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xavipargela on 12/3/18.
 */
public class MainView extends JFrame {

    private String user;

    //Components de vista global de proyectos
    private JButton jbNew;
    private JButton jbUser;
    private JPopupMenu popup;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JScrollPane jsc1;
    private JScrollPane jsc2;
    private JList userProjects;
    private JList sharedProjects;
    private JPanel finalPanel;
    private JPanel jpLists;
    private JPanel jpButtons;

    private DefaultListModel<String> dataUser;
    private DefaultListModel<String> dataShared;
    private JList<String> stringsUser;
    private JList<String> stringsShared;

    //Componentes de vista de crear nuevo proyecto
    private JLabel jlProjectName;
    private JTextField jtfProjectName;
    private JLabel jlContributors;
    //private JTextField jtfContributors;   Esto está por si ponemos un buscador
    private JScrollPane jscContributors;
    private JLabel jlSelected;
    private JScrollPane jscSelected;
    private JLabel jlBackground;
    private JButton jbBackground;
    private JButton jbCreate;
    private JButton jbCancel;

    private JFileChooser jfChooser;

    private JList<String> contributors;
    private DefaultListModel<String> dataContributors;

    private JList<String> selected;
    private DefaultListModel<String> dataSelected;


    public MainView() {

        initHome();
        this.setSize(600, 300);
        this.setTitle("ProjectManager");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void initHome() {

        this.getContentPane().removeAll();
        initComponents();
        initComponentsProject();
        initHomeView();
        initListeners();
        initDragDrop();
    }

    private void initComponents(){

        //Componentes Vista Home
        // Esta lista habra que cogerla de la base de datos directamente
        dataUser = new DefaultListModel<>();
        /*dataUser.addElement("Item1");
        dataUser.addElement("Item2");
        dataUser.addElement("Item3");
        dataUser.addElement("Item4");
        dataUser.addElement("Item5");
        dataUser.addElement("Item6");
        dataUser.addElement("Item7");
        dataUser.addElement("Item8");*/
        dataUser.addElement("Item9");


        stringsUser = new JList<String>(dataUser);

        dataShared = new DefaultListModel<>();
        dataShared.addElement("Item1");
        dataShared.addElement("Item2");
        dataShared.addElement("Item3");
        dataShared.addElement("Item4");

        stringsShared = new JList<String>(dataShared);

        userProjects = new JList();     //Clase que contendra la info de la DB
        userProjects.setFixedCellHeight(25);
        userProjects.setOpaque(false);
        userProjects.setCellRenderer(new TransparentListCellRenderer());

        sharedProjects = new JList();
        sharedProjects.setFixedCellHeight(25);
        sharedProjects.setOpaque(false);
        sharedProjects.setCellRenderer(new TransparentListCellRenderer());

        // Creación de la lista, de momento con un ejemplo clicable
        userProjects.setModel(new AbstractListModel() {

            @Override
            public int getSize() {
                return stringsUser.getModel().getSize();
            }

            @Override
            public Object getElementAt(int i) {
                return stringsUser.getModel().getElementAt(i);
            }
        });

        sharedProjects.setModel(new AbstractListModel() {

            @Override
            public int getSize() {
                return stringsShared.getModel().getSize();
            }

            @Override
            public Object getElementAt(int i) {
                return stringsShared.getModel().getElementAt(i);
            }
        });

        // Adición al window de los Java Components
        userProjects.setPreferredSize(new Dimension(270,130));
        sharedProjects.setPreferredSize(new Dimension(270,130));

        jsc1 = new JScrollPane(userProjects);
        JScrollBar jScrollBar1 = new JScrollBar();
        jsc1.setVerticalScrollBar(jScrollBar1);
        jsc1.setOpaque(false);
        jsc1.getViewport().setOpaque(false);

        jsc2 = new JScrollPane(sharedProjects);
        JScrollBar jScrollBar2 = new JScrollBar();
        jsc1.setVerticalScrollBar(jScrollBar2);
        jsc2.setOpaque(false);
        jsc2.getViewport().setOpaque(false);

        jsc1.setBorder(BorderFactory.createTitledBorder(null, "Your Projects", TitledBorder.RIGHT, TitledBorder.TOP,
                new Font("Arial",Font.PLAIN,12), Color.WHITE));

        jsc2.setBorder(BorderFactory.createTitledBorder(null, "Shared Projects", TitledBorder.RIGHT, TitledBorder.TOP,
                new Font("Arial",Font.PLAIN,12), Color.WHITE));


        popup = new JPopupMenu();

        menuItem1 = new JMenuItem("Home", new ImageIcon(((new ImageIcon("icons/home_icon.png"))
                .getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        menuItem1.setMnemonic(KeyEvent.VK_P);
        menuItem1.getAccessibleContext().setAccessibleDescription(
                "Go to the Home screen");

        menuItem2 = new JMenuItem("New Project", new ImageIcon(((new ImageIcon("icons/addProject_icon.png"))
                .getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        menuItem2.setMnemonic(KeyEvent.VK_P);
        menuItem2.getAccessibleContext().setAccessibleDescription(
                "Create a New Project");

        menuItem3 = new JMenuItem("Logout", new ImageIcon(((new ImageIcon("icons/logout_icon.png"))
                .getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        menuItem3.setMnemonic(KeyEvent.VK_P);


        //Componentes Vista New Project
        jbNew = new JButton("New Project");
        jbNew.setBackground(Color.GRAY);
        jbNew.setForeground(Color.DARK_GRAY);

        jbUser = new JButton("User");
        jbUser.setOpaque(false);

        jlProjectName = new JLabel("Insert a name for your project:");
        jtfProjectName = new JTextField("Project name");
        jtfProjectName.setColumns(12);

        jtfProjectName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jtfProjectName.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = jtfProjectName.getText();
                if (text == "") {
                    jtfProjectName.setText("Project Name");
                }
            }
        });

        jlContributors = new JLabel("Contributors:");

        // Esta lista habra que cogerla de la base de datos directamente
        dataContributors = new DefaultListModel<>();
        dataContributors.addElement("Item1");
        dataContributors.addElement("Item2");
        dataContributors.addElement("Item3");
        dataContributors.addElement("Item4");
        dataContributors.addElement("Item5");
        dataContributors.addElement("Item6");
        dataContributors.addElement("Item7");
        dataContributors.addElement("Item8");
        dataContributors.addElement("Item9");
        dataContributors.addElement("Item10");
        dataContributors.addElement("Item11");
        dataContributors.addElement("Item12");

        contributors = new JList<>(dataContributors);
        contributors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contributors.setLayoutOrientation(JList.VERTICAL);
        contributors.setBorder(BorderFactory.createEmptyBorder());

        contributors.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                int selectedIx = contributors.getSelectedIndex();
                boolean exist = false;
                for (int i = 0; i < dataSelected.getSize(); i++) {
                    if (dataSelected.get(i) == dataContributors.getElementAt(selectedIx)) {
                        exist = true;
                        break;
                    }
                }
                if (!exist){
                    dataSelected.add(dataSelected.getSize(), dataContributors.getElementAt(selectedIx));
                }
            }
        });

        jscContributors = new JScrollPane();
        jscContributors.setViewportView(contributors);
        jscContributors.setBorder(BorderFactory.createEmptyBorder());
        jscContributors.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jscContributors.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        jlSelected = new JLabel("Selected Contributors:");
        dataSelected = new DefaultListModel<>();

        selected = new JList<>(dataSelected);
        selected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selected.setLayoutOrientation(JList.VERTICAL);
        selected.setBorder(BorderFactory.createEmptyBorder());

        selected.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                int selectedIndex = selected.getSelectedIndex();
                String selectedValue = selected.getSelectedValue();

                for(int i = 0; i < dataSelected.getSize(); i++) {
                    if (dataSelected.getElementAt(i) == selectedValue) {
                        dataSelected.removeElement(selectedValue);
                        System.out.println(selectedIndex);
                        break;
                    }
                }
            }
        });

        jscSelected = new JScrollPane();
        jscSelected.setViewportView(selected);
        jscSelected.setBorder(BorderFactory.createEmptyBorder());
        jscSelected.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jscSelected.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        jlBackground = new JLabel("Choose a background for your project:");
        jbBackground = new JButton("Browse");

        jbCreate = new JButton("Create");
        jbCancel = new JButton("Cancel");

    }

    private void initComponentsProject(){


    }

    public void initHomeView(){

        getContentPane().removeAll();

        jpButtons = new JPanel(new BorderLayout());
        jpButtons.setOpaque(false);
        jpButtons.add(jbNew, BorderLayout.LINE_START);
        jpButtons.add(jbUser, BorderLayout.LINE_END);

        jpLists = new JPanel(new FlowLayout());
        jpLists.setOpaque(false);
        jpLists.add(jsc1);
        jpLists.add(jsc2);

        finalPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File("images/bgMain.jpg")),
                            0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        finalPanel.add(jpLists, BorderLayout.CENTER);
        finalPanel.add(jpButtons, BorderLayout.NORTH);

        this.getContentPane().add(finalPanel);
        validate();
    }

    private void initListeners(){

        //Action Listeners de las dos listas, User y Shared -- Más abajo están los procedimientos a seguir
        userProjects.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                jListUserValueChanged(evt);
            }
        });

        sharedProjects.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                jListSharedValueChanged(evt);
            }
        });
    }

    private void initDragDrop() {

        userProjects.setDragEnabled(true);
        userProjects.setDropMode(DropMode.INSERT);
        userProjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        userProjects.setTransferHandler(new TransferHandler() {

            int index;
            boolean beforeIndex = false;

            @Override
            public int getSourceActions(JComponent comp) {
                return COPY_OR_MOVE;
            }

            @Override
            public Transferable createTransferable(JComponent comp) {
                index = userProjects.getSelectedIndex();
                return new StringSelection ((String) userProjects.getSelectedValue());
            }

            @Override
            public void exportDone( JComponent comp, Transferable trans, int action ) {
                if (action==MOVE) {
                    if (beforeIndex) {
                        dataUser.remove(index + 1);
                    }else{
                        dataUser.remove(index);
                    }
                    jsc1.updateUI();
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
                    String s = (String)support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    JList.DropLocation dl = (JList.DropLocation)support.getDropLocation();
                    dataUser.add(dl.getIndex(), s);
                    beforeIndex = dl.getIndex() < index ? true : false;
                    return true;
                }
                catch (UnsupportedFlavorException | IOException e) {}
                return false;
            }
        });

        sharedProjects.setDragEnabled(true);
        sharedProjects.setDropMode(DropMode.INSERT);
        sharedProjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        sharedProjects.setTransferHandler(new TransferHandler() {

            int index;
            boolean beforeIndex = false;

            @Override
            public int getSourceActions(JComponent comp) {
                return COPY_OR_MOVE;
            }

            @Override
            public Transferable createTransferable(JComponent comp) {
                index = sharedProjects.getSelectedIndex();
                return new StringSelection ((String) sharedProjects.getSelectedValue());
            }

            @Override
            public void exportDone( JComponent comp, Transferable trans, int action ) {
                if (action==MOVE) {
                    if (beforeIndex) {
                        dataShared.remove(index + 1);
                    }else{
                        dataShared.remove(index);
                    }
                    jsc2.updateUI();
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
                    String s = (String)support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    JList.DropLocation dl = (JList.DropLocation)support.getDropLocation();
                    dataShared.add(dl.getIndex(), s);
                    beforeIndex = dl.getIndex() < index ? true : false;
                    return true;
                }
                catch (UnsupportedFlavorException | IOException e) {}
                return false;
            }
        });

    }

    public void showPopupMenu() {

        // New project menu item
        popup.add(menuItem1);

        // New project menu item
        popup.add(menuItem2);

        // Logout menu item
        popup.add(menuItem3);

        popup.show(jbUser, -75, jbUser.getBounds().y + jbUser.getBounds().height);
    }

    public void initNewProjectView() {

        this.getContentPane().removeAll();

        JPanel jpButtons = new JPanel(new BorderLayout());
        jpButtons.add(jbNew, BorderLayout.LINE_START);
        jpButtons.add(jbUser, BorderLayout.LINE_END);

        JPanel jpName = new JPanel(new FlowLayout());
        jpName.add(jlProjectName);
        jpName.add(jtfProjectName);

        JPanel jpContributors = new JPanel(new BorderLayout());
        jpContributors.add(jlContributors, BorderLayout.NORTH);
        jpContributors.add(jscContributors, BorderLayout.CENTER);
        jpContributors.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));

        JPanel jpSelected = new JPanel(new BorderLayout());
        jpSelected.add(jlSelected, BorderLayout.NORTH);
        jpSelected.add(jscSelected, BorderLayout.CENTER);
        jpSelected.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));

        JPanel jpContributorsTotal = new JPanel(new GridLayout(1,2));
        jpContributorsTotal.add(jpContributors);
        jpContributorsTotal.add(jpSelected);

        JPanel jpButtonsBottom = new JPanel(new FlowLayout());
        jpButtonsBottom.add(jbCreate);
        jpButtonsBottom.add(jbCancel);

        JPanel jpBackground = new JPanel(new FlowLayout());
        jpBackground.add(jlBackground);
        jpBackground.add(jbBackground);

        JPanel jpAux3 = new JPanel(new BorderLayout());
        jpAux3.add(jpBackground, BorderLayout.CENTER);
        jpAux3.add(jpButtonsBottom, BorderLayout.SOUTH);

        JPanel jpAux = new JPanel(new BorderLayout());
        jpAux.add(jpName, BorderLayout.NORTH);
        jpAux.add(jpContributorsTotal, BorderLayout.CENTER);
        jpAux.add(jpAux3, BorderLayout.SOUTH);

        JPanel jpAux2 = new JPanel(new BorderLayout());
        jpAux2.add(jpButtons, BorderLayout.NORTH);
        jpAux2.add(jpAux, BorderLayout.CENTER);

        getContentPane().add(jpAux2);
        validate();

    }

    public void showBrowseMenu() {

        jfChooser = new JFileChooser();
        jfChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        jfChooser.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        int result = jfChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
    }

    private void Logout(){

    }

    public Project createProject() throws IOException {

        Project newProject = new Project();

        newProject.setName(jtfProjectName.getText());
        System.out.println(newProject.getName());
        newProject.setMembres(new ArrayList<Usuari>());
        newProject.setColumnes(new ArrayList<Columna>());
        //newProject.setBackground(jfChooser.getSelectedFile().getAbsolutePath());
        newProject.setEtiquetes(new ArrayList<Etiqueta>());
        newProject.setDate();

        return newProject;
    }

    private void jListUserValueChanged(javax.swing.event.ListSelectionEvent evt) {
        //set text on right here
        String s = (String) userProjects.getSelectedValue();

        }

    private void jListSharedValueChanged(javax.swing.event.ListSelectionEvent evt) {
        String s = (String) sharedProjects.getSelectedValue();

    }

    public void registerController(ClientController controllerClient, PopupController controllerPopUp) {

        jbNew.setActionCommand("NEW_PROJECT");
        jbNew.addActionListener(controllerClient);

        jbUser.setActionCommand("POPUP");
        jbUser.addActionListener(controllerClient);

        jbBackground.setActionCommand("BROWSE");
        jbBackground.addActionListener(controllerClient);

        jbCreate.setActionCommand("CREATE");
        jbCreate.addActionListener(controllerClient);

        jbCancel.setActionCommand("CANCEL");
        jbCancel.addActionListener(controllerClient);

        menuItem1.setActionCommand("HOME");
        menuItem1.addActionListener(controllerPopUp);

        menuItem2.setActionCommand("NEW_PROJECT");
        menuItem2.addActionListener(controllerPopUp);

        menuItem3.setActionCommand("LOGOUT");
        menuItem3.addActionListener(controllerPopUp);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void addProject(String project){
        dataUser.addElement(project);
    }

    public void addBackground(String path){
        finalPanel.removeAll();

        finalPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(new File(path)),
                            0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        finalPanel.add(jpLists, BorderLayout.CENTER);
        finalPanel.add(jpButtons, BorderLayout.NORTH);

        this.getContentPane().add(finalPanel);
        validate();
    }
}
