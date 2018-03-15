package network;

import model.ProjectManager;

import java.io.*;
import java.net.Socket;

/**
 * S'encarrega de la connexió amb el servidor
 * Created by Marc on 14/3/18
 */
public class ServerCommunication extends Thread{

    // constants relacionades amb la comunicacio
    private static final String SERVER_IP = "localhost";        //Se tiene que hacer a partir del config.json
    private static final int SERVER_PORT = 12345;

    private boolean isOn;                   //booleano para saber si el thread está activo
    private Socket socketToServer;
    private DataOutputStream dataOut;
    private DataInputStream dataIn;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private ProjectManager projectManager;
    private String info;
    private Object infoObject;

    /**
     * Constructor donde se inician los atributos
     * @param projectManager modelo
     */
    public ServerCommunication(ProjectManager projectManager){

        this.projectManager = projectManager;
        this.isOn = false;
        info = "";
        try {
            this.socketToServer = new Socket(SERVER_IP, SERVER_PORT);
            this.dataIn = new DataInputStream(socketToServer.getInputStream());
            this.dataOut = new DataOutputStream(socketToServer.getOutputStream());
            this.objectIn = new ObjectInputStream(socketToServer.getInputStream());
            this.objectOut = new ObjectOutputStream(socketToServer.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("*** ESTA EL SERVIDOR EN EXECUCIO? ***");
        }
    }

    /**
     * Inicia la comunicación con el servidor
     */
    public void startServerComunication() {
        isOn = true;
        this.start();
    }

    /**
     * Atura la comunicació amb el servidor
     */
    public void stopServerComunication() {
        this.isOn = false;
        this.interrupt();
    }

    /**
     * Lo que lleva a cabo el thread
     */
    @Override
    public synchronized void run(){

        while (isOn){       //Mientras el thread esté activo

            try {
                objectOut.writeObject(projectManager.getUsuari());      //Va enviando el usuario al servidor
                info = dataIn.readUTF();                                //Va leyendo la información que le llega del servidor(no objetos)

                try {
                    infoObject = objectIn.readObject();                 //Va leyendo la información que le llega del servidor (objetos)
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
                stopServerComunication();
                System.out.println("*** ESTA EL SERVIDOR EN EXECUCIO? ***");
            }
        }
        stopServerComunication();
    }

}
