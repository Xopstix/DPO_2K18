package controlador;

import views.ProjectView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by xaviamorcastillo on 22/5/18.
 * Escolta quina cela se clica de les llistes de la vista project
 */
public class CustomMouseListenerProject implements MouseListener {

    private ProjectView vistaProject;

    private int column;
    private int row;

    public CustomMouseListenerProject(ProjectView vistaProject) {
        this.vistaProject = vistaProject;
    }

    public void mousePressed(MouseEvent e) {
        column = Integer.parseInt(((JList) e.getSource()).getName());
        row = ((JList) e.getSource()).getSelectedIndex();
    }

    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {

        vistaProject.setPopupTaskColumn(Integer.parseInt(((JList) e.getSource()).getName()));
        vistaProject.setPopupTaskRow(((JList) e.getSource()).getSelectedIndex());

        vistaProject.initPopupTasca(Integer.parseInt(((JList) e.getSource()).getName()), ((JList) e.getSource()).getSelectedIndex());

        column = Integer.parseInt(((JList) e.getSource()).getName());
        row = ((JList) e.getSource()).getSelectedIndex();
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
