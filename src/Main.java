import config.Config;
import config.ObjectFile;
import controlador.ClientController;
import controlador.CustomMouseListenerMain;
import controlador.PopupController;
import model.ProjectManager;
import network.ServerCommunication;
import views.AuthenticationView;
import views.ColorChooserPanel;
import views.MainView;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * Classe principal del programa cliente (La parte del server no está acabada, por eso está comentada)
 * Created by Marc on 13/3/18
 */
public class Main {

    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                Config data;
                ObjectFile objData = new ObjectFile();

                try {
                    data = objData.readData();

                    AuthenticationView authenticationView = new AuthenticationView();                  //views de autenticación
                    MainView mainView = new MainView();
                    ProjectManager projectManager = new ProjectManager();                              //modelo
                    //ServerCommunication serverCommunication = new ServerCommunication(projectManager); //network
                    //serverCommunication.startConnection();

                    ClientController clientController = new ClientController(authenticationView, mainView, projectManager, data);    //controlador
                    ServerCommunication serverCommunication = clientController.getServerCommunication();
                    PopupController popupController = new PopupController(mainView, serverCommunication);
                    CustomMouseListenerMain mouseSelectionListenerMain = new CustomMouseListenerMain(mainView);

                    clientController.setMouseSelectionListenerMain(mouseSelectionListenerMain);
                    clientController.setPopupController(popupController);

                    authenticationView.registerController(clientController);    //Relación controlador --> views
                    mainView.registerController(clientController, popupController, mouseSelectionListenerMain);
                    mainView.setClientController(clientController);

                    authenticationView.setVisible(true);        //Se hace visible la views de autenticación

                    //Zona Test
                    //vistaProject.setVisible(false);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
