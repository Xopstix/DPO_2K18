package controlador;

import views.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xaviamorcastillo on 17/4/18.
 */
public class MainViewController implements ActionListener {

    private MainView mainView;

    public MainViewController(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("New_PROJECT")){

        }

    }

    public MainView getMainView() {
        return mainView;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
}
