package controlador;

import model.Project;
import model.ProjectManager;
import model.Tasca;
import views.MainView;
import views.ProjectView;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xaviamorcastillo on 14/5/18.
 */
public class CustomTransferHandler extends TransferHandler {

    private ArrayList<DefaultListModel<String>> dataUser;
    private ArrayList<JList<String>> userColumns;


    private int indexOrigin;
    private int listOrigin;
    private int indexDrop;
    private int listDrop;
    private boolean beforeIndex = false;
    private ProjectView projectView;
    private ClientController clientController;
    private MainView mainView;
    private ProjectManager projectManager;

    private JList<String> source;
    private JList<String> target;


    public CustomTransferHandler(ArrayList<JList<String>> userColumns, ArrayList<DefaultListModel<String>> dataUser,
                                 ProjectView vistaProject, ClientController clientController, MainView mainView){
        this.userColumns = userColumns;
        this.projectView = vistaProject;
        this.dataUser = dataUser;
        this.clientController = clientController;
        this.mainView = mainView;
        this.projectManager = mainView.getProjectManager();
    }

    @Override
    public int getSourceActions(JComponent comp) {
        return COPY_OR_MOVE;
    }

    @Override
    public Transferable createTransferable(JComponent comp) {
        indexOrigin = userColumns.get(Integer.parseInt(comp.getName())).getSelectedIndex();
        listOrigin = Integer.parseInt(userColumns.get(Integer.parseInt(comp.getName())).getName());

        return new StringSelection((String) userColumns.get(Integer.parseInt(comp.getName())).getSelectedValue());
    }

    @Override
    public void exportDone(JComponent comp, Transferable trans, int action) {
        if (action == MOVE) {
            System.out.println("1.1");
            System.out.println("Index Drop: "+ indexDrop);
            System.out.println("List Drop: "+ listDrop);
            System.out.println("Index Origin: " + indexOrigin);
            System.out.println("List Origin: " + listOrigin);

            if (listDrop == listOrigin){
                System.out.println("2.1");

                if (beforeIndex) {
                    //System.out.println("3.1");
                    //dataUser.get(listOrigin).remove(indexOrigin + 1);

                    if (this.projectView.getList() == 1){

                        mainView.getProjectManager().getYourProjects().get(findByIdYours(this.projectView.getProject())).
                                getColumnes().get(listOrigin).getTasques().remove(indexOrigin + 1);

                    } else {

                        mainView.getProjectManager().getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                                getColumnes().get(listOrigin).getTasques().remove(indexOrigin + 1);
                    }


                } else {
                    System.out.println("4.1");
                    //dataUser.get(listOrigin).remove(indexOrigin);
                    //this.projectView.getProject().getColumnes().get(listOrigin).getTasques().remove(indexOrigin);

                    if (this.projectView.getList() == 1){

                        mainView.getProjectManager().getYourProjects().get(findByIdYours(this.projectView.getProject())).
                                getColumnes().get(listOrigin).getTasques().remove(indexOrigin);

                    } else {

                        mainView.getProjectManager().getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                                getColumnes().get(listOrigin).getTasques().remove(indexOrigin);
                    }
                }

                //System.out.println(dataUser);

            }else{
                //dataUser.get(listOrigin).remove(indexOrigin);
                //this.projectView.getProject().getColumnes().get(listOrigin).getTasques().remove(indexOrigin);

                if (this.projectView.getList() == 1){

                    mainView.getProjectManager().getYourProjects().get(findByIdYours(this.projectView.getProject())).
                            getColumnes().get(listOrigin).getTasques().remove(indexOrigin);

                } else {

                    mainView.getProjectManager().getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                            getColumnes().get(listOrigin).getTasques().remove(indexOrigin);
                }
            }

        }

        mainView.refreshView();
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        // Data =? String
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferSupport t) {

        //if (!canImport(comp, t.getTransferDataFlavors())) {
          //  System.out.println("Mal");
            //return false;
        //}

        JList.DropLocation dl = (JList.DropLocation) t.getDropLocation();

        indexDrop = dl.getIndex();
        listDrop = Integer.parseInt(userColumns.get(Integer.parseInt(t.getComponent().getName())).getName());
        //System.out.println("importData: List: " + listDrop+ " Index: " + indexDrop);

        Transferable transferable = t.getTransferable();
        try {

            Tasca data;
            if (this.projectView.getList() == 1){

                data = projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                        getColumnes().get(listOrigin).getTasques().get(indexOrigin);

            } else {

                data = projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                        getColumnes().get(listOrigin).getTasques().get(indexOrigin);
            }

            String notUsed = (String) transferable.getTransferData(DataFlavor.stringFlavor);
            //dataUser.get(listDrop).add(indexDrop, data);

            //this.projectView.getProject().getColumnes().get(listDrop).getTasques()
                    //.add(indexDrop, this.projectView.getProject().getColumnes().get(listOrigin).getTasques().get(indexOrigin));

            if (this.projectView.getList() == 1){

                projectManager.getYourProjects().get(findByIdYours(this.projectView.getProject())).
                        getColumnes().get(listDrop).getTasques().add(indexDrop, data);

            } else {

                projectManager.getSharedProjects().get(findByIdShared(this.projectView.getProject())).
                        getColumnes().get(listDrop).getTasques().add(indexDrop, data);
            }

        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(listDrop == listOrigin){

            beforeIndex = dl.getIndex() < indexOrigin ? true : false;
        }

        return true;
    }

    public int findByIdYours(Project project){

        for (int i = 0; i < this.projectManager.getYourProjects().size(); i++){

            if (this.projectManager.getYourProjects().get(i).getIdProyecto() == this.projectView.getProject().getIdProyecto()) {

                return i;
            }
        }

        return 0;
    }

    public int findByIdShared(Project project){

        for (int i = 0; i < this.projectManager.getSharedProjects().size(); i++){

            if (this.projectManager.getSharedProjects().get(i).getIdProyecto() == this.projectView.getProject().getIdProyecto()) {

                return i;
            }
        }

        return 0;
    }

    public int getIndexOrigin(){
        return indexOrigin;
    }
    public void setIndexOrigin(){
        this.indexOrigin = indexOrigin;
    }
    public int getListOrigin(){
        return listOrigin;
    }
    public void setListOrigin(){
        this.listOrigin = listOrigin;
    }
    public int getIndexDrop() {
        return indexDrop;
    }
    public void setIndexDrop(){
        this.indexDrop = indexDrop;
    }
    public int getListDrop() {
        return listDrop;
    }
    public void setListDrop(){
        this.listDrop = listDrop;
    }

}
