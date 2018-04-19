package controlador;

import views.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xaviamorcastillo on 19/4/18.
 */
public class PopupController implements ActionListener{

    private MainView vista;

    public PopupController(){

    }

    public PopupController(MainView vista) {
        this.vista = vista;
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("HOME")) {

            vista.initHomeView();
        }

        if (e.getActionCommand().equals("NEW_PROJECT")){

            vista.initNewProjectView();
        }

        if (e.getActionCommand().equals("LOGOUT")){

            //Por hacer
        }
    }

}
