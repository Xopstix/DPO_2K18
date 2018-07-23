package controlador;

import network.ServerCommunication;

import java.awt.event.WindowEvent;

/**
 * Quan un client surt de la seva interfície aquesta classe s'encarrega de tancar el servidor dedicat d'aquest client
 * Created by Marc on 4/4/18.
 */
public class WindowListener implements java.awt.event.WindowListener {

    private ServerCommunication s;


    public WindowListener(ServerCommunication s){
        this.s = s;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        s.endConnection();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
