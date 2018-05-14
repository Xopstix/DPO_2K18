package controlador;

import views.VistaTest;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by xaviamorcastillo on 14/5/18.
 */

public class CustomListSelectionListener implements ListSelectionListener {

    private VistaTest vistaTest;

    public CustomListSelectionListener(VistaTest vistaTest) {
        this.vistaTest = vistaTest;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        vistaTest.initPopupTasca(Integer.parseInt(((JList) e.getSource()).getName()), ((JList) e.getSource()).getSelectedIndex());
        System.out.println(((JList) e.getSource()).getName());
        System.out.println(((JList) e.getSource()).getSelectedIndex());
    }

    public VistaTest getVistaTest() {
        return vistaTest;
    }

    public void setVistaTest(VistaTest vistaTest) {
        this.vistaTest = vistaTest;
    }
}
