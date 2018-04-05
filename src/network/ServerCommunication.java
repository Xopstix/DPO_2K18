package network;

import model.ProjectManager;
import model.Usuari;

import java.io.*;
import java.net.Socket;

/**
 * S'encarrega de la connexió amb el servidor
 * Created by Marc on 14/3/18
 */
public class ServerCommunication extends Thread{

    private Socket socket;
    private ObjectOutputStream oos;
    private boolean running;
    private ProjectManager projectManager;

    public ServerCommunication(ProjectManager projectManager){

        running = false;
        this.projectManager = projectManager;
    }

    /**
     * Es connecta al servidor
     */
    public void startConnection(){
        running = true;
        try{
            socket = new Socket("localhost", 12345);
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

            while(running){

                oos.writeObject(projectManager);

                endConnection();
            }

        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Acaba la connexió
     */
    public void endConnection(){
        running = false;
        interrupt();
    }
}
