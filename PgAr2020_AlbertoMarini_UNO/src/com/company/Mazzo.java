package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Mazzo implements Serializable {
    private List<Carta> carte = new ArrayList<Carta>();
    private final int maxCarte = 10;

    public Mazzo() {
//        System.out.println("Creato mazzo vuoto");
        carte.clear();
    }

    public int numeroCarte(){
        return carte.size();
    }

    public boolean isVuoto(){
        if (carte.size() == 0) return true;
        else return false;
    }

    public void stampaMazzo(){
        for (Carta c : carte){
            System.out.println(c);
        }
    }
    public void mescola(){
       Collections.shuffle(carte);
    }

    public void riempiMazzo(){
        for (int i = 0; i < maxCarte; i++){
            aggiungiCarta(new Carta(i, Colori.GIALLO));
            aggiungiCarta(new Carta(i, Colori.GIALLO));
        }
        for (int i = 0; i < maxCarte; i++){
            aggiungiCarta(new Carta(i, Colori.ROSSO));
            aggiungiCarta(new Carta(i, Colori.ROSSO));
        }
        for (int i = 0; i < maxCarte; i++){
            aggiungiCarta(new Carta(i, Colori.BLU));
            aggiungiCarta(new Carta(i, Colori.BLU));
        }
        for (int i = 0; i < maxCarte; i++){
            aggiungiCarta(new Carta(i, Colori.VERDE));
            aggiungiCarta(new Carta(i, Colori.VERDE));
        }
    }

    public void aggiungiCarta(Carta carta){
        carte.add(carta);
    }

    public Carta pesca(){
        return carte.remove(0);
    }
}
