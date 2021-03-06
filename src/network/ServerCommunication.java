package network;

import config.Config;
import config.ObjectFile;
import controlador.ClientController;
import controlador.CustomListSelectionListener;
import model.ProjectManager;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * S'encarrega de la connexió amb el servidor
 * Created by Marc on 14/3/18
 */
public class ServerCommunication extends Thread{

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean running;
    private ProjectManager projectManager;
    private String msg;
    private ClientController clientController;
    private CustomListSelectionListener customListSelectionListenerMain;
    private int mode;

    public ServerCommunication(){

    }

    public ServerCommunication(ProjectManager projectManager, ClientController clientController, int mode){

        running = false;
        this.projectManager = projectManager;
        msg = "";
        this.clientController = clientController;
        this.mode = mode;
    }

    /**
     * Es connecta al servidor
     */
    public void startConnection() throws FileNotFoundException {

        Config data;
        ObjectFile objData = new ObjectFile();


        data = objData.readData();

        running = true;
        try{
            System.out.println(data.getIp() + data.getPort());
            socket = new Socket(data.getIp(), data.getPort());
            this.start();

        }catch (IOException ioe){
            System.out.println("Could not connect, verify that the server is running!");
        }
    }

    /**
     * Escriu un objecte Usuari infinitament i mostra per pantalla el seu nom
     */
    public synchronized void run(){
        try{
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            while(running){

                if (mode == 1) {
                    projectManager.setMode(1);
                    oos.writeObject(projectManager);
                    this.projectManager = (ProjectManager) ois.readObject();
                    clientController.setProjectManager(projectManager);
                    msg = dis.readUTF();
                    autentica(msg);
                    endConnection();
                }

                if (mode == 2) {
                    projectManager.setMode(2);
                    //System.out.println("Imagen: " + projectManager.getProject().getBackground());
                    oos.writeObject(projectManager);
                    this.projectManager = (ProjectManager) ois.readObject();
                    System.out.println(projectManager.getYourProjects().get(projectManager.getYourProjects().size()-1).getIdProyecto());
                    this.clientController.setProjectManager(this.projectManager);
                    this.clientController.setMainPM(projectManager);
                    msg = dis.readUTF();
                    autentica(msg);
                    endConnection();
                }

                if (mode == 3) {
                    projectManager.setMode(3);
                    oos.writeObject(projectManager);
                    endConnection();
                }
                if (mode == 4) {
                    projectManager.setMode(4);
                    oos.writeObject(projectManager);
                    this.projectManager = (ProjectManager) ois.readObject();
                    msg = dis.readUTF();

                    //autentica(msg);
                    endConnection();

                    clientController.setProjectManager(this.projectManager);
                    clientController.useNewPM();

                }
            }

        }catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Acaba la connexió
     */
    public void endConnection(){
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        interrupt();
    }

    private void autentica(String msg){
        if (msg.equals("Logged")) {
            clientController.logInAccepted();
            System.out.println("LOGGED");
        }
        else{
            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public void showDialogMessage(String msg){

        JOptionPane.showMessageDialog(null, msg);
    }

    public ProjectManager getProjectManager(){

        return this.projectManager;
    }
}
