package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Columna
 * Created by Marc on 13/3/18.
 */
public class Columna implements Serializable{

    private String nom;
    private int ordre;
    private ArrayList<Tasca> tasques;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public ArrayList<Tasca> getTasques() {
        return tasques;
    }

    public void setTasques(ArrayList<Tasca> tasques) {
        this.tasques = tasques;
    }
}
