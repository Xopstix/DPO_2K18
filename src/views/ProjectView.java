package views;

import controlador.ClientController;
import controlador.CustomMouseListenerProject;
import controlador.CustomTransferHandler;
import model.Project;
import model.Tasca;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.colorchooser.AbstractColorChooserPanel;
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

    private JButton jbNew;
    private JButton jbUser;
    private ArrayList<JList<String>> stringsUser;
    private ArrayList<JList<String>> projectColumns;
    private ArrayList<JButton> titleButtons;
    private ArrayList<JButton> deleteButtons;
    private ArrayList<JTextField> textFields;
    private JTextField newColumnTextField;
    private CustomMouseListenerProject customMouseListener;
    private ClientController clientController;

    private JPopupMenu popup;
    private int popupTaskColumn;
    private int popupTaskRow;
    private JPopupMenu popupColumn;
    private JLabel doneLabel;
    private JCheckBox doneCheckbox;
    private JMenuItem deleteButton;
    private JTextField nameTextField;
    private JPanel colorLabels;
    private JColorChooser colorChooser;
    private JPanel colorChooserPanel;

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
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void initComponentsProject() {

        stringsUser = new ArrayList<>();
        dataUser = new ArrayList<>();

        titleButtons = new ArrayList<>();
        deleteButtons = new ArrayList<>();

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

        jbNew = new JButton("New Project");
        jbUser = new JButton("User");

        popup = new JPopupMenu();

        doneLabel = new JLabel("Task completed");
        doneCheckbox = new JCheckBox();

        deleteButton = new JMenuItem("Delete", new ImageIcon(((new ImageIcon("icons/delete_icon.png"))
                .getImage()).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        deleteButton.setMnemonic(KeyEvent.VK_P);
        deleteButton.getAccessibleContext().setAccessibleDescription("Delete task");

        deleteButton.setMinimumSize(new Dimension(200,40));
        deleteButton.setMaximumSize(new Dimension(200,40));

        nameTextField = new JTextField("Nou nom");

        nameTextField.setMinimumSize(new Dimension(200,30));
        nameTextField.setMaximumSize(new Dimension(200,30));

        colorChooser = new JColorChooser(Color.WHITE);

        colorChooser.setPreviewPanel(new JPanel());

        AbstractColorChooserPanel panels[] = { new ColorChooserPanel() };
        colorChooser.setChooserPanels(panels);

        colorChooser.setMaximumSize(new Dimension(50, 50));
        colorChooser.setMinimumSize(new Dimension(50, 50));

        colorChooserPanel = new JPanel(new BorderLayout());

        colorLabels = new JPanel();

        colorLabels.setLayout(new BoxLayout(colorLabels, BoxLayout.Y_AXIS));

        colorLabels.setSize(new Dimension(150,50));

        if (project.getEtiquetes() != null){

            for (int i = 0; i < project.getEtiquetes().size(); i++){
                JLabel auxLabelColor = new JLabel(project.getEtiquetes().get(i).getNom());
                auxLabelColor.setBorder(BorderFactory.createEmptyBorder(5,0,5,10));
                colorLabels.add(auxLabelColor);
            }

            colorChooserPanel.add(colorChooser, BorderLayout.WEST);
            colorChooserPanel.add(colorLabels,BorderLayout.CENTER);

        }else {

            for (int i = 0; i < 5; i++) {
                JTextField auxColorTextField = new JTextField("Name your task!");
                auxColorTextField.setMinimumSize(new Dimension(140, 40));
                auxColorTextField.setMaximumSize(new Dimension(140, 40));
                auxColorTextField.setBorder(BorderFactory.createMatteBorder(10, 5, 10, 10, Color.LIGHT_GRAY));
                auxColorTextField.setOpaque(false);
                colorLabels.add(auxColorTextField);
            }

            colorChooserPanel.add(colorChooser, BorderLayout.WEST);
            colorChooserPanel.add(colorLabels, BorderLayout.CENTER);
        }
    }

    public void initVistaProject() {

        this.setResizable(false);

        JPanel totalPanel;

        File image = new File("images/dgreen.jpg");

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

        projectColumns = new ArrayList<>();

        for (int i = 0; i < dataUser.size(); i++){

            JPanel auxPanel = new JPanel();
            auxPanel.setLayout(new BoxLayout(auxPanel, BoxLayout.Y_AXIS));

            JPanel titlePanel = new JPanel(new FlowLayout());
            titlePanel.setMaximumSize(new Dimension(300,50));

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
            titlePanel.add(nameButton);
            titlePanel.add(deleteButton);

            auxPanel.setOpaque(false);
            auxPanel.add(titlePanel, BorderLayout.NORTH);

            //JPanel scrollable = new JPanel();
            //scrollable.setLayout(new BoxLayout(scrollable, BoxLayout.Y_AXIS));
            auxPanel.setMaximumSize(new Dimension(200, 500));
            auxPanel.setMinimumSize(new Dimension(200,400));

            JList<String> projectColumn = new JList<>(dataUser.get(i));     //Clase que contendra la info de la DB
            projectColumn.setFixedCellHeight(35);
            projectColumn.setOpaque(false);
            projectColumn.setCellRenderer(new TransparentListCellRenderer());

            projectColumn.setName(i+"");

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
            jScrollPane.setMaximumSize(new Dimension(200, dataUser.get(i).size() * 36));
            jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.getVerticalScrollBar().setVisible(false);
            jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            auxPanel.add(jScrollPane);
            initDragDropProject(projectColumn, jScrollPane, clientController);
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

            auxTextField.setPreferredSize(new Dimension(200,25));
            auxTextField.setMaximumSize(new Dimension(200,25));

            textFields.add(auxTextField);

            auxPanel.add(auxTextField);

            auxPanel.setPreferredSize(new Dimension(200, 530));
            auxPanel.setMaximumSize(new Dimension(200, 530));
            auxPanel.setBorder((BorderFactory.createEmptyBorder(0,20,0,0)));
            auxPanel.setAlignmentY(boxPanel.TOP_ALIGNMENT);

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
        newProjectPanel.setMaximumSize(new Dimension(200,250));
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

    private void initDragDropProject(JList<String> column, JScrollPane jScrollPane, ClientController clientController) {

        column.setDragEnabled(true);
        column.setDropMode(DropMode.INSERT);
        column.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        CustomTransferHandler customTransferHandler = new CustomTransferHandler(projectColumns, dataUser, this, customMouseListener, clientController);
        column.setTransferHandler(customTransferHandler);
    }

    /*private void jListUserValueChanged(ListSelectionEvent evt, JList<String> column) {
        //set text on right here
        String s = (String) column.getSelectedValue();
    }

    private void initListeners(JList<String> column) {

        //Action Listeners de las dos listas, User y Shared -- Más abajo están los procedimientos a seguir
        column.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                jListUserValueChanged(evt, column);
            }
        });
    }*/

    public void initPopupTasca(int columna, int fila){

        nameTextField.setText("Nou nom");
        popup.add(nameTextField);

        if (project.getColumnes().get(popupTaskColumn).getTasques().get(popupTaskColumn).getCompleta() == 1){

            doneCheckbox.setSelected(true);

        } else{

            doneCheckbox.setSelected(false);
        }

        JPanel doneCheckboxPanel = new JPanel(new FlowLayout());
        doneCheckboxPanel.add(doneLabel);
        doneCheckboxPanel.add(doneCheckbox);
        popup.add(doneCheckboxPanel);
        popup.add(colorChooserPanel);
        popup.add(deleteButton);

        popup.setPopupSize(new Dimension(200,300));
        //popup.show(this, columna * 200 + 23, fila * 35 + 120);
        popup.setLocation((int) projectColumns.get(columna).getLocationOnScreen().getY(),
                (int) projectColumns.get(columna).getLocationOnScreen().getX());

        popup.show(this, (int) projectColumns.get(columna).getLocationOnScreen().getX() + 75,
                (int) projectColumns.get(columna).getLocationOnScreen().getY() + fila * 35 - 100);
        //popup.show(this, projectColumns.get(columna).getY(), projectColumns.get(fila).getX());
    }

    public void initPopUpColumn(){

        popupColumn.add(new TextField(10));
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

    public void registerController(ClientController controllerClient, CustomMouseListenerProject customMouseListener) {

        this.clientController = controllerClient;

        jbUser.setActionCommand("POPUP_PANEL");
        jbUser.addActionListener(controllerClient);

        jbNew.setActionCommand("NEW_PROJECT");
        jbNew.addActionListener(controllerClient);

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
}
