package com.company;


import java.io.Serializable;

public class Carta implements Serializable {

    int numero;
    boolean speciale;
    ValoreCarta effetto;
    Colori colore;

    public Carta(int numero, Colori colore) {
        this.numero = numero;
        this.colore = colore;
        this.speciale = false;
        this.effetto = ValoreCarta.valueOf("NULLO");
    }

    public void setColore(Colori colore) {
        this.colore = colore;
    }

    public Carta(int numero, Colori colore, boolean speciale, ValoreCarta effetto) {
        this.numero = numero;
        this.colore = colore;
        this.speciale = speciale;
        this.effetto = effetto;
    }

    @Override
    public String toString() {
        return "Carta " + numero + " - " + colore + " Speciale " + speciale + " - Effetto: " + effetto ;
    }

    /**
     * Controlla se la carta giocata è valida rispetto all'ultima sulla piula degli scarti
     * @param c
     * @return
     */
    public boolean giocataValida(Carta c){
        if (c.effetto == ValoreCarta.PESCA4 || c.effetto == ValoreCarta.CAMBIOCOLORE) return true;
        if (c.colore == this.colore || c.numero == this.numero) return true;
        return false;
    }
}
