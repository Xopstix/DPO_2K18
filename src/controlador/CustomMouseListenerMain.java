package controlador;

import views.MainView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by xaviamorcastillo on 22/5/18.
 */
public class CustomMouseListenerMain implements MouseListener {

    private MainView mainView;

    private int column;
    private int row;

    public CustomMouseListenerMain(MainView mainView) {
        this.mainView = mainView;
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

        if (((JList) e.getSource()).getName().equals("UserProjects")) {

            mainView.loadProject("Your", ((JList) e.getSource()).getSelectedIndex());

        } else {

            mainView.loadProject("Shared", ((JList) e.getSource()).getSelectedIndex());
        }
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
