package views;

import controlador.*;
import model.*;

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
import java.awt.event.WindowEvent;
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

    private ProjectManager projectManager;

    private ClientController clientController;

    private ProjectView projectView;

    public MainView() {

        initHome();
        this.setSize(600, 300);
        this.setTitle("ProjectManager");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public MainView(ProjectManager projectManager) {

        this.projectManager = projectManager;
        initHome();
        this.setSize(600, 300);
        this.setTitle("ProjectManager");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void initHome() {

        this.getContentPane().removeAll();
        this.setResizable(false);
        initComponents();
        initHomeView();
        initListeners();
        initDragDrop();
    }

    private void initComponents(){

        //Componentes Vista Home
        // Esta lista habra que cogerla de la base de datos directamente
        dataUser = new DefaultListModel<>();

        stringsUser = new JList<String>(dataUser);

        dataShared = new DefaultListModel<>();

        stringsShared = new JList<String>(dataShared);

        userProjects = new JList();     //Clase que contendra la info de la DB
        userProjects.setFixedCellHeight(25);
        userProjects.setOpaque(false);
        userProjects.setCellRenderer(new TransparentListCellRenderer());

        userProjects.setName("1");

        userProjects.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()) {
                    //System.out.println(userProjects.getSelectedValue());
                }
            }
        });

        sharedProjects = new JList();
        sharedProjects.setFixedCellHeight(25);
        sharedProjects.setOpaque(false);
        sharedProjects.setCellRenderer(new TransparentListCellRenderer());

        sharedProjects.setName("2");

        sharedProjects.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()) {
                    //System.out.println(sharedProjects.getModel().getElementAt(sharedProjects.getSelectedIndex()));
                }
            }
        });

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
        userProjects.setFixedCellHeight(35);
        //userProjects.setPreferredSize(new Dimension(30, 50));

        sharedProjects.setFixedCellHeight(35);
        //sharedProjects.setPreferredSize(new Dimension(70,50));

        jsc1 = new JScrollPane(userProjects);

        jsc1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsc1.getVerticalScrollBar().setVisible(false);
        jsc1.getVerticalScrollBar().setOpaque(false);
        jsc1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jsc1.setOpaque(false);
        jsc1.getViewport().setOpaque(false);


        //jsc1.setMinimumSize(new Dimension(250, 50));
        //jsc1.setMaximumSize(new Dimension(250, 50));

        jsc2 = new JScrollPane(sharedProjects);

        jsc2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsc2.getVerticalScrollBar().setVisible(false);
        jsc2.getVerticalScrollBar().setOpaque(false);
        jsc2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jsc2.setOpaque(false);
        jsc2.getViewport().setOpaque(false);

        //jsc2.setMinimumSize(new Dimension(250, 50));
        //jsc2.setMaximumSize(new Dimension(250, 50));

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

                if (jtfProjectName.getText().equals("Project Name")){

                    jtfProjectName.setText("");
                }
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
                        //System.out.println(selectedIndex);
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

    public void initHomeView(){

        getContentPane().removeAll();

        jpButtons = new JPanel(new BorderLayout());
        jpButtons.setOpaque(false);
        jpButtons.add(jbNew, BorderLayout.LINE_START);
        jpButtons.add(jbUser, BorderLayout.LINE_END);

        jpLists = new JPanel();
        jpLists.setLayout(new BoxLayout(jpLists, BoxLayout.X_AXIS));
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

    public void addProjects (){

        for (int i = 0; i < projectManager.getYourProjects().size(); i++){

            try{

                dataUser.addElement(projectManager.getYourProjects().get(i).getName());

            }catch(IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }

        for (int i = 0; i < projectManager.getSharedProjects().size(); i++){

            try{

                dataShared.addElement(projectManager.getSharedProjects().get(i).getName());

            }catch(IndexOutOfBoundsException e){

                e.printStackTrace();
            }
        }

        this.revalidate();
    }

    public void addContributors (ArrayList<String> contributors){

        for (int i = 0; i < contributors.size(); i++){

            dataContributors.addElement(contributors.get(i));
        }

        this.revalidate();
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

                for (int i = 0; i < dataUser.size(); i++){

                    try {
                        if (dataUser.get(i).equals
                                ((String)support.getTransferable().getTransferData(DataFlavor.stringFlavor))){
                        return true;
                        }
                    } catch (UnsupportedFlavorException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return false;
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

                for (int i = 0; i < dataShared.size(); i++){

                    try {
                        if (dataShared.get(i).equals
                                ((String)support.getTransferable().getTransferData(DataFlavor.stringFlavor))){
                            return true;
                        }
                    } catch (UnsupportedFlavorException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return false;
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

        //----

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

    public Project createProject() throws IOException {

        Project newProject = new Project();

        newProject.setName(jtfProjectName.getText());

        ArrayList<String> aux = new ArrayList<>();

        for (int i = 0; i < dataSelected.size(); i++){

            aux.add(dataSelected.get(i));
        }

        newProject.setMembres(aux);

        newProject.setColumnes(new ArrayList<Columna>());

        //Guarda la imagen seleccionada como background en la carpeta images y pone su path en
        //el atributo Background del proyecto
        File f = new File(jfChooser.getSelectedFile().getAbsolutePath());
        newProject.setBackground("images/" + f.getName());
        f.renameTo(new File("images/" + f.getName()));
        f.delete();

        ArrayList<Etiqueta> newEtiquetes = new ArrayList<>();

        Etiqueta auxEtiqueta = new Etiqueta();
        auxEtiqueta.setNom("Etiqueta 1");
        auxEtiqueta.setColor(Color.YELLOW);
        newEtiquetes.add(auxEtiqueta);

        auxEtiqueta = new Etiqueta();
        auxEtiqueta.setNom("Etiqueta 2");
        auxEtiqueta.setColor(Color.GREEN);
        newEtiquetes.add(auxEtiqueta);

        auxEtiqueta = new Etiqueta();
        auxEtiqueta.setNom("Etiqueta 3");
        auxEtiqueta.setColor(Color.ORANGE);
        newEtiquetes.add(auxEtiqueta);

        auxEtiqueta = new Etiqueta();
        auxEtiqueta.setNom("Etiqueta 4");
        auxEtiqueta.setColor(Color.BLUE);
        newEtiquetes.add(auxEtiqueta);

        auxEtiqueta = new Etiqueta();
        auxEtiqueta.setNom("Etiqueta 5");
        auxEtiqueta.setColor(Color.PINK);
        newEtiquetes.add(auxEtiqueta);

        newProject.setEtiquetes(newEtiquetes);

        newProject.setDate();

        return newProject;
    }

    private void jListUserValueChanged(javax.swing.event.ListSelectionEvent evt) {
        String s = (String) userProjects.getSelectedValue();

    }

    private void jListSharedValueChanged(javax.swing.event.ListSelectionEvent evt) {
        String s = (String) sharedProjects.getSelectedValue();

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

    public void loadProject(String columna, int fila){

        if (columna.equals("Your")){

            this.projectView = new ProjectView(projectManager.getYourProjects().get(fila), 1);

        }else{

            this.projectView = new ProjectView(projectManager.getSharedProjects().get(fila), 2);

        }

        CustomTransferHandler customTransferHandler = new CustomTransferHandler(projectView.getProjectColumns(),
                projectView.getDataUser(), projectView, clientController, this);
        projectView.setCustomTransferHandler(customTransferHandler);
        projectView.initDragDropProject();
        CustomMouseListenerProject mouseListener = new CustomMouseListenerProject(projectView);
        projectView.registerController(clientController, mouseListener);
        projectView.setVisible(true);
        this.setVisible(false);
    }

    public void addTask(String task, int column){

        int id = this.projectView.getProject().getIdProyecto();
        int list = this.projectView.getList();
        //this.projectView.addTask(task, column);
        Tasca newTask = new Tasca();
        newTask.setNom(task);
        newTask.setCompleta(0);

        if (this.projectView.getList() == 1){

            this.projectManager.getYourProjects().get(findByIdYours(
                    this.projectView.getProject())).getColumnes().get(column).getTasques().add(newTask);
            this.projectView.setProject(this.projectManager.getYourProjects().get(findByIdYours(
                    this.projectView.getProject())));

            this.projectView.getContentPane().removeAll();
            this.projectView.initComponentsProject();
            this.projectView.initVistaProject();
            CustomMouseListenerProject mouseListener = new CustomMouseListenerProject(projectView);
            projectView.registerController(clientController, mouseListener);
            this.projectView.revalidate();

        }else{

            this.projectManager.getSharedProjects().get(findByIdShared(
                    this.projectView.getProject())).getColumnes().get(column).getTasques().add(newTask);

            this.projectView.getContentPane().removeAll();
            this.projectView.initComponentsProject();
            this.projectView.initVistaProject();
            CustomMouseListenerProject mouseListener = new CustomMouseListenerProject(projectView);
            projectView.registerController(clientController, mouseListener);
            this.projectView.revalidate();
        }

    }

    public void addColumn(String columnName){

        int id = this.projectView.getProject().getIdProyecto();
        int list = this.projectView.getList();
        Columna newColumna = new Columna();
        newColumna.setNom(columnName);

        if (this.projectView.getList() == 1){

            this.projectManager.getYourProjects().get(findByIdYours(
                    this.projectView.getProject())).getColumnes().add(newColumna);

            this.projectView.getContentPane().removeAll();
            this.projectView.initComponentsProject();
            this.projectView.initVistaProject();
            CustomMouseListenerProject mouseListener = new CustomMouseListenerProject(projectView);
            projectView.registerController(clientController, mouseListener);
            this.projectView.revalidate();

        }else{

            this.projectManager.getSharedProjects().get(findByIdShared(
                    this.projectView.getProject())).getColumnes().add(newColumna);

            this.projectView.getContentPane().removeAll();
            this.projectView.initComponentsProject();
            this.projectView.initVistaProject();
            CustomTransferHandler customTransferHandler = new CustomTransferHandler(projectView.getProjectColumns(),
                    projectView.getDataUser(), projectView, clientController, this);
            projectView.setCustomTransferHandler(customTransferHandler);
            projectView.initDragDropProject();
            CustomMouseListenerProject mouseListener = new CustomMouseListenerProject(projectView);

            projectView.registerController(clientController, mouseListener);
            this.projectView.revalidate();
        }
    }

    public ArrayList<Project> getYourNewOrder(ArrayList<Project> projects){

        ArrayList<Project> aux = new ArrayList<>();

        for (int i = 0; i < dataUser.size(); i++){

            for (int j = 0; j < projects.size(); j++){

                if (projects.get(j).getName().equals(dataUser.get(i))){

                    aux.add(projects.get(j));
                }
            }
        }

        return aux;
    }

    public ArrayList<Project> getSharedNewOrder(ArrayList<Project> projects){

        ArrayList<Project> aux = new ArrayList<>();

        for (int i = 0; i < dataShared.size(); i++){

            for (int j = 0; j < projects.size(); j++){

                if (projects.get(j).getName().equals(dataShared.get(i))){

                    aux.add(projects.get(j));
                }
            }
        }

        return aux;
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void setChecked(int selected) {

        if (this.projectView.getList() == 1){

            projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                    getColumnes().get(this.projectView.getPopupTaskColumn()).
                    getTasques().get(this.projectView.getPopupTaskRow()).setCompleta(selected);

        } else{

            projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                    getColumnes().get(this.projectView.getPopupTaskColumn()).
                    getTasques().get(this.projectView.getPopupTaskRow()).setCompleta(selected);
        }

        this.projectView.getProject().getColumnes().get(this.projectView.getPopupTaskColumn()).
                getTasques().get(this.projectView.getPopupTaskRow()).setCompleta(selected);

        this.projectView.revalidate();

    }

    public void changeName(String text) {

        if (this.projectView.getList() == 1){

            projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                    getColumnes().get(this.projectView.getPopupTaskColumn()).
                    getTasques().get(this.projectView.getPopupTaskRow()).setNom(text);

        } else {

            projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                    getColumnes().get(this.projectView.getPopupTaskColumn()).
                    getTasques().get(this.projectView.getPopupTaskRow()).setNom(text);
        }

        //this.projectView.getProject().getColumnes().get(this.projectView.getPopupTaskColumn()).
                //getTasques().get(this.projectView.getPopupTaskRow()).setNom(text);

        refreshView();
    }

    public void deleteColumn(int column) {

        if (this.projectView.getList() == 1){

            projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                    getColumnes().remove(column);

        } else {

            projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                    getColumnes().remove(column);
        }

        refreshView();

    }

    public void deleteTask() {

        if (this.projectView.getList() == 1){

            projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                    getColumnes().get((projectView.getPopupTaskColumn())).
                    getTasques().remove(projectView.getPopupTaskRow());

        } else {

            projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                    getColumnes().get((projectView.getPopupTaskColumn())).
                    getTasques().remove(projectView.getPopupTaskRow());
        }

        refreshView();

    }

    public void closeProject(){

        this.projectView.dispatchEvent(new WindowEvent(projectView, WindowEvent.WINDOW_CLOSING));
    }

    public void moveRight(int column) {

        if (this.projectView.getList() == 1){

            if (column < projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                    getColumnes().size() - 1) {


                Columna movingColumn = new Columna();
                movingColumn = projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                        getColumnes().get(column);

                Columna nextColumn = new Columna();
                nextColumn = projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                        getColumnes().get(column + 1);

                projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).getColumnes().
                        set(column, nextColumn);

                projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).getColumnes().
                        set(column + 1, movingColumn);
            }

        } else {

            if (column < projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                    getColumnes().size() - 1) {

                Columna movingColumn = new Columna();
                movingColumn = projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                        getColumnes().get(column);

                Columna nextColumn = new Columna();
                projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                        getColumnes().get(column + 1);

                projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).getColumnes().
                        set(column, nextColumn);

                projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).getColumnes().
                        set(column + 1, movingColumn);
            }
        }

        refreshView();
    }

    public void moveLeft(int column) {

        System.out.println(column);
        if (this.projectView.getList() == 1){

            if (column > 0) {

                Columna movingColumn = new Columna();
                movingColumn = projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                        getColumnes().get(column);

                Columna previousColumn = new Columna();
                previousColumn = projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                        getColumnes().get(column - 1);

                projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).getColumnes().
                        set(column, previousColumn);

                projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).getColumnes().
                        set(column - 1, movingColumn);
            }

        } else {

            if (column > 0) {

                Columna movingColumn = new Columna();
                movingColumn = projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                        getColumnes().get(column);

                Columna previousColumn = new Columna();
                projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                        getColumnes().get(column - 1);

                projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).getColumnes().
                        set(column, previousColumn);

                projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).getColumnes().
                        set(column - 1, movingColumn);
            }
        }

        refreshView();

    }

    public void putEtiqueta (int color) {

        if (this.projectView.getList() == 1) {

            projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                    getColumnes().get((projectView.getPopupTaskColumn())).
                    getTasques().get(projectView.getPopupTaskRow()).setId_etiqueta(color);

        } else {

            projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                    getColumnes().get((projectView.getPopupTaskColumn())).
                    getTasques().get(projectView.getPopupTaskRow()).setId_etiqueta(color);
        }

        refreshView();

    }

    public void fixEtiqueta (int etiqueta, String nom){

        if (this.projectView.getList() == 1) {

            projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                    getEtiquetes().get(etiqueta).setNom(nom);

        } else {

            projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                    getEtiquetes().get(etiqueta).setNom(nom);
        }

        System.out.println(projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                getEtiquetes().get(etiqueta).getNom());
        refreshView();
    }

    public void titlePopup(int i) {

        projectView.titlePopup(i);
    }

    public void initPopupUser(){

        projectView.initPopupUser();
    }

    public void initPopupColors(){

        projectView.initPopupColors();
        projectView.initPopupColors();
    }

    public void newColumnTitle(String newName) {

        if (this.projectView.getList() == 1){

            projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                    getColumnes().get(projectView.getChangingTitle()).setNom(newName);

        } else {

            projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                    getColumnes().get(projectView.getChangingTitle()).setNom(newName);
        }

        refreshView();

    }

    public void closePopupTitle() {

        this.projectView.closePopupTitle();
    }

    public void closePopupTask() {

        this.projectView.closePopupTask();
    }

    public void deleteProject(){

        if (this.projectView.getList() == 1){

            projectManager.getYourProjects().remove(findByIdYours(this.projectView.getProject()));
        } else {

            projectManager.getSharedProjects().remove(findByIdShared(this.projectView.getProject()));
        }

        this.revalidate();
    }


    public void useNewPM(ProjectManager projectManager) {

        System.out.println("new");
        this.projectManager = projectManager;
        refreshView();

    }

    public void syncDescription(String description){

        if (this.projectView.getList() == 1) {

            projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                    getColumnes().get((projectView.getPopupTaskColumn())).
                    getTasques().get(projectView.getPopupTaskRow()).setDescripcio(description);

        } else {

            projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                    getColumnes().get((projectView.getPopupTaskColumn())).
                    getTasques().get(projectView.getPopupTaskRow()).setDescripcio(description);
        }

        refreshView();
    }

    public ProjectView getProjectView(){

        return this.projectView;
    }

    public int findByIdYours(Project project){

        for (int i = 0; i < this.projectManager.getYourProjects().size(); i++){

            if (this.projectManager.getYourProjects().get(i).getIdProyecto() == this.projectView.getProject().getIdProyecto()) {

                return i;
            }
        }

        return 0;
    }

    public void initFileChooser(){

        File newBg = projectView.initFileChooser();
        newBg.renameTo(new File("images/" + projectView.getList() +
                projectView.getProject().getIdProyecto() + "_bg.jpg"));
        newBg.delete();

        refreshView();
    }

    public int findByIdShared(Project project){

        for (int i = 0; i < this.projectManager.getSharedProjects().size(); i++){

            if (this.projectManager.getSharedProjects().get(i).getIdProyecto() == this.projectView.getProject().getIdProyecto()) {

                return i;
            }
        }

        return 0;
    }

    public void refreshView(){

        projectView.getContentPane().removeAll();
        projectView.initComponentsProject();
        projectView.initVistaProject();
        CustomTransferHandler customTransferHandler = new CustomTransferHandler(projectView.getProjectColumns(),
                projectView.getDataUser(), projectView, clientController, this);
        projectView.setCustomTransferHandler(customTransferHandler);
        projectView.initDragDropProject();
        CustomMouseListenerProject mouseListener = new CustomMouseListenerProject(projectView);
        projectView.registerController(clientController, mouseListener);
        projectView.setVisible(true);
        projectView.revalidate();
        this.setVisible(false);
        this.revalidate();
    }

    public void registerController(ClientController controllerClient, PopupController controllerPopUp,
                                   CustomMouseListenerMain customMouseListenerMain) {

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

        userProjects.addMouseListener(customMouseListenerMain);
        sharedProjects.addMouseListener(customMouseListenerMain);

    }

}
