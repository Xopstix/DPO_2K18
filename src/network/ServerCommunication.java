package network;

import config.Config;
import config.ObjectFile;
import model.ProjectManager;
import model.Usuari;

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
    private boolean running;
    private ProjectManager projectManager;
    private String msg;

    public ServerCommunication(ProjectManager projectManager){

        running = false;
        this.projectManager = projectManager;
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
            //socket = new Socket("localhost", 12345);
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

            while(running){

                oos.writeObject(projectManager);
                msg = dis.readUTF();
                JOptionPane.showMessageDialog(null, msg);

                endConnection();
            }

        }catch (IOException ioe) {
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
