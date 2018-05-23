package controlador;

import views.ProjectView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by ManuSahun on 23/5/18.
 */
public class CustomKeyListener implements KeyListener {

    private ProjectView projectView;

    public CustomKeyListener (ProjectView projectView){

        this.projectView = projectView;
    }

    @Override
    public void keyPressed (KeyEvent k){
    }

    @Override
    public void keyReleased (KeyEvent k){
    }

    @Override
    public void keyTyped (KeyEvent k){
    }
}
