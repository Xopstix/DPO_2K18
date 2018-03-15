package model;

import controlador.ClientController;
import network.ServerCommunication;
import vista.AuthenticationView;

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

                AuthenticationView authenticationView = new AuthenticationView();                  //vista de autenticación
                ProjectManager projectManager = new ProjectManager();                              //modelo
                //ServerCommunication serverCommunication = new ServerCommunication(projectManager); //network

                ClientController clientController= new ClientController(authenticationView, projectManager);    //controlador

                authenticationView.registerController(clientController);    //Relación controlador --> vista

                authenticationView.setVisible(true);        //Se hace visible la vista de autenticación
            }
        });
    }
}
