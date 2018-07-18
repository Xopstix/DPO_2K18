package controlador;

import views.ProjectView;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseListener;
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
    private ProjectView vistaProject;
    private MouseListener mouseListener;
    private ClientController clientController;

    private JList<String> source;
    private JList<String> target;


    public CustomTransferHandler(ArrayList<JList<String>> userColumns, ArrayList<DefaultListModel<String>> dataUser,
                                 ProjectView vistaProject, CustomMouseListenerProject mouseListener, ClientController clientController){
        this.userColumns = userColumns;
        this.vistaProject = vistaProject;
        this.dataUser = dataUser;
        this.mouseListener = mouseListener;
        this.clientController = clientController;
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
                    System.out.println("3.1");
                    dataUser.get(listOrigin).remove(indexOrigin + 1);
                    //this.vistaProject.getProject().getColumnes().get(listOrigin).getTasques().remove(indexOrigin + 1);


                } else {
                    System.out.println("4.1");
                    dataUser.get(listOrigin).remove(indexOrigin);
                    //this.vistaProject.getProject().getColumnes().get(listOrigin).getTasques().remove(indexOrigin);
                }

                System.out.println(dataUser);

            }else{
                dataUser.get(listOrigin).remove(indexOrigin);
                //this.vistaProject.getProject().getColumnes().get(listOrigin).getTasques().remove(indexOrigin);
                System.out.println("diferente");
            }

        }

        /*vistaProject.getContentPane().removeAll();
        vistaProject.initComponentsProject();
        vistaProject.initVistaProject();
        CustomMouseListenerProject mouseListener = new CustomMouseListenerProject(vistaProject);
        vistaProject.registerController(clientController, mouseListener);*/
        vistaProject.revalidate();
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
            String data = (String) transferable.getTransferData(DataFlavor.stringFlavor);
            dataUser.get(listDrop).add(indexDrop, data);
            //this.vistaProject.getProject().getColumnes().get(listDrop).getTasques()
                    //.add(indexDrop, this.vistaProject.getProject().getColumnes().get(listOrigin).getTasques().get(indexOrigin));

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

    public MouseListener getMouseListener() {
        return mouseListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void actualizarInfo(){

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
