import controlador.ClientController;
import model.ProjectManager;
import views.AuthenticationView;
import views.MainView;

import javax.swing.*;

/**
 * Classe principal del programa cliente (La parte del server no está acabada, por eso está comentada)
 * Created by Marc on 13/3/18
 */
public class Main {

    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                AuthenticationView authenticationView = new AuthenticationView();                  //views de autenticación
                MainView mainView = new MainView();
                ProjectManager projectManager = new ProjectManager();                              //modelo
                //ServerCommunication serverCommunication = new ServerCommunication(projectManager); //network
                //serverCommunication.startConnection();

                ClientController clientController= new ClientController(authenticationView, projectManager);    //controlador

                authenticationView.registerController(clientController);    //Relación controlador --> views

                authenticationView.setVisible(true);        //Se hace visible la views de autenticación
                mainView.setVisible(true);
            }
        });
    }
}
