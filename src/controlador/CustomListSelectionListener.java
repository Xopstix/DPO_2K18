package controlador;

import model.ProjectManager;
import views.MainView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by xaviamorcastillo on 21/5/18.
 * Encarregada de escoltar quina cela se clica en una llista de la vista principal
 */
public class CustomListSelectionListener implements ListSelectionListener {

    private MainView mainView;
    private ProjectManager projectManager;

    public CustomListSelectionListener(MainView mainView) {

        this.mainView = mainView;
    }

        @Override
        public void valueChanged(ListSelectionEvent e) {

        if (((JList) e.getSource()).getName().equals("UserProjects")){

            ((JList) e.getSource()).getName().equals("UserProjects");
            mainView.loadProject("Your", ((JList) e.getSource()).getSelectedIndex());

        }else{

            mainView.loadProject("Shared", ((JList) e.getSource()).getSelectedIndex());
        }

        //vistaTest.initPopupTasca(Integer.parseInt(((JList) e.getSource()).getName()), ((JList) e.getSource()).getSelectedIndex());
    }

}
