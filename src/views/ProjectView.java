package views;

import controlador.ClientController;
import controlador.CustomMouseListenerProject;
import controlador.CustomTransferHandler;
import model.Project;
import model.Tasca;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xaviamorcastillo on 17/4/18.
 */
public class ProjectView extends JFrame{

    private ArrayList<DefaultListModel<String>> dataUser;

    private JButton jbProjects;
    private JButton jbUser;
    private ArrayList<JList<String>> stringsUser;
    private ArrayList<JList<String>> projectColumns;
    private ArrayList<JButton> rightButtons;
    private ArrayList<JButton> leftButtons;
    private ArrayList<JButton> titleButtons;
    private ArrayList<JButton> deleteButtons;
    private ArrayList<JTextField> textFields;
    private ArrayList<JTextField> etiquetasTextfields;
    private JTextField newColumnTextField;
    private CustomMouseListenerProject customMouseListener;
    private CustomTransferHandler customTransferHandler;
    private ClientController clientController;

    private JPopupMenu popupUser;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;

    private JPopupMenu popup;
    private int popupTaskColumn;
    private int popupTaskRow;
    private int changingTitle;
    private JLabel doneLabel;
    private JCheckBox doneCheckbox;
    private JMenuItem deleteButton;
    private JTextField nameTextField;
    private JTextField descriptionArea;
    private JPanel colorLabels;
    private JColorChooser colorChooser;
    private ArrayList<JButton> colorIcons;
    private JButton colorButton;
    private JPanel colorChooserPanel;
    private JPanel colorIconsPanel;
    private JPopupMenu popupTitle;
    private JTextField titleTextField;

    private JPopupMenu popupColors;

    private Project project;
    private int type;

    public ProjectView(Project project, int type){

        this.project = project;
        this.type = type;
        initComponentsProject();
        initVistaProject();
        this.setSize(1200, 600);
        this.setTitle("ProjectManager");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void initComponentsProject() {

        popupUser = new JPopupMenu();

        System.out.println("creado item");

        menuItem1 = new JMenuItem("Home", new ImageIcon(((new ImageIcon("icons/home_icon.png"))
                .getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        menuItem1.setMnemonic(KeyEvent.VK_P);
        menuItem1.getAccessibleContext().setAccessibleDescription(
                "Go to the Home screen");

        menuItem2 = new JMenuItem("Assign markers", new ImageIcon(((new ImageIcon("icons/paperclip_icon.png"))
                .getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        menuItem2.setMnemonic(KeyEvent.VK_P);
        menuItem2.getAccessibleContext().setAccessibleDescription(
                "Sets name for each marker");

        menuItem3 = new JMenuItem("Delete Project", new ImageIcon(((new ImageIcon("icons/delete_icon.png"))
                .getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        menuItem3.setMnemonic(KeyEvent.VK_P);
        menuItem3.getAccessibleContext().setAccessibleDescription(
                "Deletes the Project");

        menuItem4 = new JMenuItem("Logout", new ImageIcon(((new ImageIcon("icons/logout_icon.png"))
                .getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        menuItem4.setMnemonic(KeyEvent.VK_P);

        menuItem5 = new JMenuItem("Change background", new ImageIcon(((new ImageIcon("icons/logout_icon.png"))
                .getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        menuItem5.setMnemonic(KeyEvent.VK_P);

        stringsUser = new ArrayList<>();
        dataUser = new ArrayList<>();

        rightButtons = new ArrayList<>();
        leftButtons = new ArrayList<>();

        titleButtons = new ArrayList<>();
        deleteButtons = new ArrayList<>();

        colorIcons = new ArrayList<>();
        etiquetasTextfields = new ArrayList<>();

        textFields = new ArrayList<>();

        try{
            for (int i = 0; i < project.getColumnes().size(); i++) {

                dataUser.add(new DefaultListModel<>());

                for (int j = 0; j < project.getColumnes().get(i).getTasques().size(); j++) {

                    dataUser.get(i).addElement(project.getColumnes().get(i).getTasques().get(j).getNom());
                }

                stringsUser.add(new JList<>(dataUser.get(i)));
            }
        }catch(Exception e){

            e.printStackTrace();
        }

        jbProjects = new JButton("Projects");
        jbUser = new JButton("User");

        popup = new JPopupMenu();

        doneLabel = new JLabel("Task completed");
        doneCheckbox = new JCheckBox();

        deleteButton = new JMenuItem("Delete this task", new ImageIcon(((new ImageIcon("icons/delete_icon.png"))
                .getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        deleteButton.setMnemonic(KeyEvent.VK_P);
        deleteButton.getAccessibleContext().setAccessibleDescription("Delete task");

        deleteButton.setMinimumSize(new Dimension(200,40));
        deleteButton.setMaximumSize(new Dimension(200,40));

        nameTextField = new JTextField("Nou nom");

        nameTextField.setMinimumSize(new Dimension(200,30));
        nameTextField.setMaximumSize(new Dimension(200,30));

        descriptionArea = new JTextField("Add a description..");
        descriptionArea.setBorder(new TitledBorder("Description"));

        colorLabels = new JPanel();
        colorLabels.setLayout(new BoxLayout(colorLabels, BoxLayout.Y_AXIS));

        colorIconsPanel = new JPanel();
        colorIconsPanel.setLayout(new BoxLayout(colorIconsPanel, BoxLayout.Y_AXIS));

        colorButton = new JButton();
        colorButton.setName("1");
        colorButton.setBorderPainted(false);

        try {
            Image img = ImageIO.read(new File("icons/greensticker.png")).
                    getScaledInstance(33, 33,  java.awt.Image.SCALE_SMOOTH ) ;
            colorButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        colorIcons.add(colorButton);
        colorIconsPanel.add(colorButton);

        colorButton = new JButton();
        colorButton.setName("2");
        colorButton.setBorderPainted(false);

        try {
            Image img = ImageIO.read(new File("icons/redsticker.png")).
                    getScaledInstance(33, 33,  java.awt.Image.SCALE_SMOOTH ) ;
            colorButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        colorIconsPanel.add(colorButton);
        colorIcons.add(colorButton);

        colorButton = new JButton();
        colorButton.setName("3");
        colorButton.setBorderPainted(false);

        try {
            Image img = ImageIO.read(new File("icons/yellowsticker.png")).
                    getScaledInstance(33, 33,  java.awt.Image.SCALE_SMOOTH ) ;
            colorButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        colorIconsPanel.add(colorButton);
        colorIcons.add(colorButton);

        colorButton = new JButton();
        colorButton.setName("4");
        colorButton.setBorderPainted(false);

        try {
            Image img = ImageIO.read(new File("icons/bluesticker.png")).
                    getScaledInstance(33, 33,  java.awt.Image.SCALE_SMOOTH ) ;
            colorButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        colorIconsPanel.add(colorButton);
        colorIcons.add(colorButton);

        colorButton = new JButton();
        colorButton.setName("5");
        colorButton.setBorderPainted(false);

        try {
            Image img = ImageIO.read(new File("icons/purplesticker.png")).
                    getScaledInstance(33, 33,  java.awt.Image.SCALE_SMOOTH ) ;
            colorButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        colorIconsPanel.add(colorButton);
        colorIcons.add(colorButton);

        colorChooserPanel = new JPanel(new BorderLayout());

        if (project.getEtiquetes() != null){

            for (int i = 0; i < 5; i++){
                JTextField auxColorTextField = new JTextField();
                //if (project.getEtiquetes().get(i).getNom().equals("")){

                    auxColorTextField.setText("New task name");
                //}else{

                    //auxColorTextField.setText(project.getEtiquetes().get(i).getNom());
                //}
                auxColorTextField.setName(i+"");
                auxColorTextField.setColumns(13);

                auxColorTextField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {

                        if (auxColorTextField.getText().equals("New task name")){

                            auxColorTextField.setText("");
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        String text = auxColorTextField.getText();
                        if (text == "") {
                            auxColorTextField.setText("New Task name");
                        }
                    }
                });

                JPanel auxiliar = new JPanel();
                auxiliar.add(auxColorTextField);
                //auxiliar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                etiquetasTextfields.add(auxColorTextField);
                colorLabels.add(auxiliar);
            }

            colorChooserPanel.add(colorIconsPanel, BorderLayout.LINE_START);
            colorChooserPanel.add(colorLabels, BorderLayout.CENTER);

        }

        titleTextField = new JTextField("Nou nom");

    }

    public void initVistaProject() {

        this.setResizable(false);

        JPanel totalPanel;

        File image2 = new File("images/dgreen.jpg");

        totalPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(image2), 0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        File existe = new File("images/" + this.type + this.project.getIdProyecto() + "_bg.jpg");
        if(existe.exists()) {
            File image = new File("images/" + this.type + this.project.getIdProyecto() + "_bg.jpg");

            totalPanel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        g.drawImage(ImageIO.read(image), 0, 0, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

        }

        JPanel boxPanel = new JPanel(new BorderLayout());

        JPanel jpButtons = new JPanel(new BorderLayout());
        jpButtons.setOpaque(false);
        jpButtons.add(jbProjects, BorderLayout.LINE_START);
        jpButtons.add(jbUser, BorderLayout.LINE_END);

        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.X_AXIS));

        projectColumns = new ArrayList<>();

        for (int i = 0; i < dataUser.size(); i++){

            JPanel auxPanel = new JPanel();
            auxPanel.setLayout(new BoxLayout(auxPanel, BoxLayout.Y_AXIS));

            JButton rightButton = new JButton();
            rightButton.setName((i+""));
            rightButton.setBorderPainted(false);

            JButton leftButton = new JButton();
            leftButton.setName((i+""));
            leftButton.setBorderPainted(false);

            try {
                Image img = ImageIO.read(new File("icons/right.png")).
                        getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                rightButton.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println(ex);
            }

            try {
                Image img = ImageIO.read(new File("icons/left.png")).
                        getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                leftButton.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println(ex);
            }

            rightButtons.add(rightButton);
            leftButtons.add(leftButton);

            JPanel titlePanel = new JPanel(new FlowLayout());
            titlePanel.setMaximumSize(new Dimension(350,50));

            JButton nameButton = new JButton(project.getColumnes().get(i).getNom());
            nameButton.setName(i+"");
            nameButton.setForeground(Color.WHITE);
            nameButton.setBorderPainted(false);
            JButton deleteButton = new JButton();
            deleteButton.setName((i+""));
            deleteButton.setBorderPainted(false);

            titleButtons.add(nameButton);
            deleteButtons.add(deleteButton);

            try {
                Image img = ImageIO.read(new File("icons/delete_icon.png")).
                        getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH ) ;
                deleteButton.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println(ex);
            }

            titlePanel.setOpaque(false);
            titlePanel.add(leftButton);
            titlePanel.add(nameButton);
            titlePanel.add(deleteButton);
            titlePanel.add(rightButton);

            auxPanel.setOpaque(false);
            auxPanel.add(titlePanel);

            //JPanel scrollable = new JPanel();
            //scrollable.setLayout(new BoxLayout(scrollable, BoxLayout.Y_AXIS));
            auxPanel.setMaximumSize(new Dimension(250, 500));
            auxPanel.setMinimumSize(new Dimension(250,400));

            JList<String> projectColumn = new JList<>(dataUser.get(i));     //Clase que contendra la info de la DB
            projectColumn.setFixedCellHeight(35);
            projectColumn.setOpaque(false);
            projectColumn.setName(i+"");
            projectColumn.setCellRenderer(new CustomListCellRenderer(this, projectColumn));

            projectColumn.setModel(new AbstractListModel() {

                @Override
                public int getSize() {
                    return stringsUser.get(Integer.parseInt(projectColumn.getName())).getModel().getSize();
                }

                @Override
                public Object getElementAt(int i) {
                    return stringsUser.get(Integer.parseInt(projectColumn.getName())).getModel().getElementAt(i);
                }
            });

            projectColumns.add(projectColumn);

            JScrollPane jScrollPane = new JScrollPane(projectColumn);
            //jScrollPane.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.RIGHT, TitledBorder.TOP, new Font("Arial",Font.PLAIN,12), Color.WHITE));
            jScrollPane.getVerticalScrollBar().setOpaque(false);
            jScrollPane.setOpaque(false);
            jScrollPane.getViewport().setOpaque(false);
            jScrollPane.setMaximumSize(new Dimension(250, dataUser.get(i).size() * 36 + 5));
            jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.getVerticalScrollBar().setVisible(false);
            jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            auxPanel.add(jScrollPane);
            //initDragDropProject(/*projectColumn*/);
            //initListeners(projectColumn);

            JTextField auxTextField = new JTextField("Afegeix tasca...");
            auxTextField.setName("" + i);
            auxTextField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {

                    if (auxTextField.getText().equals("Afegeix tasca...")){

                        auxTextField.setText("");
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    String text = auxTextField.getText();
                    if (text == "") {
                        auxTextField.setText("Afegeix tasca...");
                    }
                }
            });

            auxTextField.setPreferredSize(new Dimension(250,25));
            auxTextField.setMaximumSize(new Dimension(250,25));

            textFields.add(auxTextField);

            auxPanel.add(auxTextField);

            auxPanel.setPreferredSize(new Dimension(250, 530));
            auxPanel.setMaximumSize(new Dimension(250, 530));
            auxPanel.setBorder((BorderFactory.createEmptyBorder(0,20,0,0)));
            //auxPanel.setAlignmentY(boxPanel.TOP_ALIGNMENT);

            auxPanel.setBorder(BorderFactory.createMatteBorder(0,2,0,2,Color.WHITE));

            if (i == dataUser.size() - 1){

                auxPanel.setBorder(BorderFactory.createMatteBorder(0,2,0,4,Color.WHITE));
            }

            boxPanel.add(auxPanel);
            boxPanel.setOpaque(false);
        }

        for (int i = 0; i < textFields.size(); i++){

            textFields.get(i).updateUI();
        }

        JPanel newProjectPanel = new JPanel(new FlowLayout());
        //newProjectPanel.setBorder(BorderFactory.createMatteBorder(10,10,10,10, Color.WHITE));
        Border limits = BorderFactory.createMatteBorder(0,0,0,2,Color.WHITE);
        Border margin = BorderFactory.createEmptyBorder(60,0,0,0);
        Border compound = BorderFactory.createCompoundBorder(margin, limits);
        newProjectPanel.setBorder(compound);
        //newProjectPanel.setBorder((BorderFactory.createEmptyBorder(60,20,0,0)));
        newProjectPanel.setMinimumSize(new Dimension(300,250));
        newProjectPanel.setMaximumSize(new Dimension(300,250));
        newProjectPanel.setOpaque(false);

        newColumnTextField = new JTextField("Afegeix columna...");
        newColumnTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                if (newColumnTextField.getText().equals("Afegeix columna...")){

                    newColumnTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = newColumnTextField.getText();
                if (text == "") {
                    newColumnTextField.setText("Afegeix tasca...");
                }
            }
        });

        newProjectPanel.add(newColumnTextField);

        boxPanel.add(newProjectPanel);
        newProjectPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        boxPanel.setOpaque(false);

        JScrollPane jScrollPane2 = new JScrollPane(boxPanel);
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setOpaque(false);

        totalPanel.add(jpButtons, BorderLayout.NORTH);
        totalPanel.add(jScrollPane2, BorderLayout.CENTER);

        getContentPane().add(totalPanel);
        revalidate();

    }

    public void initDragDropProject() {

        for (int i = 0; i < dataUser.size(); i++) {

            JList<String> column = projectColumns.get(i);

            column.setDragEnabled(true);
            column.setDropMode(DropMode.INSERT);
            column.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            if (i == 0){

                System.out.println(column);
            }

            column.setTransferHandler(customTransferHandler);
        }
    }

    public void initPopupTasca(int columna, int fila){

        popup = new JPopupMenu();

        nameTextField.setText("Nou nom");
        popup.add(nameTextField);

        System.out.println(project.getColumnes());

        if (project.getColumnes().get(popupTaskColumn).getTasques().get(popupTaskRow).isCompleta() == 1){

            doneCheckbox.setSelected(true);

        } else{

            doneCheckbox.setSelected(false);
        }

        JPanel doneCheckboxPanel = new JPanel(new FlowLayout());
        doneCheckboxPanel.add(doneLabel);
        doneCheckboxPanel.add(doneCheckbox);
        popup.add(doneCheckboxPanel);
        popup.add(colorChooserPanel);

        descriptionArea.setText(project.getColumnes().get(popupTaskColumn).getTasques().get(popupTaskRow).getDescripcio());
        popup.add(descriptionArea);

        popup.add(deleteButton);

        popup.setPopupSize(new Dimension(225,370));

        popup.show(this, (int) projectColumns.get(columna).getLocationOnScreen().getX() + 150,
                (int) projectColumns.get(columna).getLocationOnScreen().getY() + fila * 35 - 100);
    }

    public void closePopupTask(){

        System.out.println("yes");
        this.popup.setVisible(false);
    }

    public void titlePopup(int column){

        popupTitle = new JPopupMenu();

        changingTitle = column;

        JLabel titleSelect = new JLabel("Escolleix un nou nom:");
        popupTitle.add(titleSelect);

        popupTitle.add(titleTextField);

        popupTitle.setPopupSize(new Dimension(250, 50));
        popupTitle.setLocation((int) projectColumns.get(column).getLocationOnScreen().getY(),
                (int) projectColumns.get(column).getLocationOnScreen().getX());

        popupTitle.show(this, (int) leftButtons.get(column).getLocationOnScreen().getX() + 30,
                (int) leftButtons.get(column).getLocationOnScreen().getY() -50);

    }

    public void initPopupUser(){

        popupUser = new JPopupMenu();

        popupUser.add(menuItem1);
        popupUser.add(menuItem2);
        popupUser.add(menuItem5);
        popupUser.add(menuItem3);
        popupUser.add(menuItem4);

        popupUser.show(jbUser, -95, jbUser.getBounds().y + jbUser.getBounds().height);
    }

    public File initFileChooser(){

        JFileChooser jfChooser = new JFileChooser();
        jfChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        jfChooser.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        int result = jfChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            return selectedFile;
        }

        return null;
    }

    public void closePopupTitle() {

        this.popupTitle.setVisible(false);
    }

    public void initPopupColors(){

        popup = new JPopupMenu();

        popup.add(colorChooserPanel);

        popup.setPopupSize(new Dimension(225,230));

        popup.show(this, 960, 95);
    }

    public void addTask(String task, int column) {

        this.jbUser.grabFocus();
        //dataUser.get(column).addElement(task);
        Tasca newTask = new Tasca();
        newTask.setNom(task);
        project.getColumnes().get(column).getTasques().add(newTask);
        initComponentsProject();
        initVistaProject();
        revalidate();
    }

    public void putEtiqueta (Color color){

        if (color == Color.green){

        } else if (color == Color.red){

        } else if (color == Color.yellow){

        } else if (color == Color.blue){

        } else if (color == Color.pink){

        }
    }

    public Project getProject(){

        return this.project;
    }

    public void setProject(Project project){

        this.project = project;
    }

    public void setPopupTaskColumn (int column){

        this.popupTaskColumn = column;
    }

    public int getPopupTaskColumn(){

        return this.popupTaskColumn;
    }

    public void setPopupTaskRow (int row){

        this.popupTaskRow = row;
    }

    public int getPopupTaskRow(){

        return this.popupTaskRow;
    }

    public int getList() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChangingTitle() {
        return changingTitle;
    }

    public void setChangingTitle(int changingTitle) {
        this.changingTitle = changingTitle;
    }

    public CustomTransferHandler getCustomTransferHandler() {
        return customTransferHandler;
    }

    public void setCustomTransferHandler(CustomTransferHandler customTransferHandler) {
        this.customTransferHandler = customTransferHandler;
    }

    public ArrayList getProjectColumns(){

        return projectColumns;
    }

    public ArrayList getDataUser(){

        return dataUser;
    }

    public void registerController(ClientController controllerClient, CustomMouseListenerProject customMouseListener) {

        this.clientController = controllerClient;

        jbUser.setActionCommand("POPUPUSER");
        jbUser.addActionListener(controllerClient);

        jbProjects.setActionCommand("PROJECTS");
        jbProjects.addActionListener(controllerClient);

        this.customMouseListener = customMouseListener;

        for (int i = 0; i < projectColumns.size(); i++){
            projectColumns.get(i).addMouseListener(customMouseListener);
        }

        for (int i = 0; i < titleButtons.size(); i++){

            titleButtons.get(i).addActionListener(controllerClient);
            titleButtons.get(i).setActionCommand("TITLEPOPUP");
        }

        for (int i = 0; i < deleteButtons.size(); i++){

            deleteButtons.get(i).addActionListener(controllerClient);
            deleteButtons.get(i).setActionCommand("DELETECOLUMN");
        }

        for (int i = 0; i < textFields.size(); i++){
            textFields.get(i).addActionListener(controllerClient);
            textFields.get(i).setActionCommand("TEXTFIELDNEWTASK");
            //textFields.get(i).addKeyListener(keyListener);
        }

        newColumnTextField.addActionListener(controllerClient);
        newColumnTextField.setActionCommand("TEXTFIELDNEWCOLUMN");

        doneCheckbox.addActionListener(controllerClient);
        doneCheckbox.setActionCommand("DONECHECKBOX");

        nameTextField.addActionListener(controllerClient);
        nameTextField.setActionCommand("NEWTASKNAME");

        deleteButton.addActionListener(controllerClient);
        deleteButton.setActionCommand("DELETETASK");

        for (int i = 0; i < rightButtons.size(); i++){

            rightButtons.get(i).addActionListener(controllerClient);
            rightButtons.get(i).setActionCommand("MOVERIGHT");

            leftButtons.get(i).addActionListener(controllerClient);
            leftButtons.get(i).setActionCommand("MOVELEFT");
        }

        titleTextField.addActionListener(controllerClient);
        titleTextField.setActionCommand("NEWTITLE");

        menuItem1.setActionCommand("PROJECTS");
        menuItem1.addActionListener(clientController);

        menuItem2.setActionCommand("POPUPCOLORS");
        menuItem2.addActionListener(clientController);

        menuItem3.setActionCommand("DELETEPROJECT");
        menuItem3.addActionListener(clientController);

        menuItem4.setActionCommand("LOGOUT");
        menuItem4.addActionListener(clientController);

        menuItem5. setActionCommand("BACKGROUND");
        menuItem5.addActionListener(clientController);
        for (int i = 0; i < colorIcons.size(); i++){

            colorIcons.get(i).setActionCommand("COLORCHOSEN");
            colorIcons.get(i).addActionListener(clientController);
        }

        for (int i = 0; i < etiquetasTextfields.size(); i++){

            etiquetasTextfields.get(i).setActionCommand("ETIQUETA");
            etiquetasTextfields.get(i).addActionListener(clientController);
        }

        descriptionArea.setActionCommand("DESCRIPTION");
        descriptionArea.addActionListener(clientController);

    }
}
