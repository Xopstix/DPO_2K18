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

        if (e.getActionCommand().equals("NEW_PROJECT")){

            mainView.initNewProjectView();
        }

        if (e.getActionCommand().equals("POPUP")){

            mainView.showPopupMenu();
        }

        if (e.getActionCommand().equals("BROWSE")){

            mainView.showBrowseMenu();
        }

        if (e.getActionCommand().equals("CREATE")){


        }

        if (e.getActionCommand().equals("CANCEL")){

            mainView.initHomeView();
        }
    }

    public MainView getMainView() {
        return mainView;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
}
