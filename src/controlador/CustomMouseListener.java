package controlador;

import views.VistaProject;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by xaviamorcastillo on 22/5/18.
 */
public class CustomMouseListener implements MouseListener {

    private VistaProject vistaProject;

    private int column;
    private int row;

    public CustomMouseListener(VistaProject vistaProject) {
        this.vistaProject = vistaProject;
    }

    public void mousePressed(MouseEvent e) {
        column = Integer.parseInt(((JList) e.getSource()).getName());
        row = ((JList) e.getSource()).getSelectedIndex();
    }

    public void mouseReleased(MouseEvent e) {
        saySomething("Mouse released; # of clicks: "
                + e.getClickCount(), e);
    }

    public void mouseEntered(MouseEvent e) {
        saySomething("Mouse entered", e);
    }

    public void mouseExited(MouseEvent e) {
        saySomething("Mouse exited", e);
    }

    public void mouseClicked(MouseEvent e) {

        vistaProject.initPopupTasca(Integer.parseInt(((JList) e.getSource()).getName()), ((JList) e.getSource()).getSelectedIndex());
        System.out.println(((JList) e.getSource()).getName());
        System.out.println(((JList) e.getSource()).getSelectedIndex());
        column = Integer.parseInt(((JList) e.getSource()).getName());
        row = ((JList) e.getSource()).getSelectedIndex();
    }

    void saySomething(String eventDescription, MouseEvent e) {

    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
