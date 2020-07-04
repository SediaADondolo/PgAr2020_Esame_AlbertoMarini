package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Giocatore implements Serializable {
    private String nome;
    int dado;
    private List<Carta> carte = new ArrayList<Carta>();

    public Giocatore(String nome){
        this.nome = nome;
        this.dado = 0;
    }

    public int getDado() {
        return dado;
    }

    public void setDado(int dado) {
        this.dado = dado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Carta> getCarte() {
        return carte;
    }

    public void daiCarta(Carta c){
        carte.add(c);
    }

    public Carta giocaCarta(int i){
        return carte.remove(i);
    }

    @Override
    public String toString() {
        return "Giocatore{" +
                "nome='" + nome + '\'' +
                ", carte=" + carte +
                '}';
    }
}
