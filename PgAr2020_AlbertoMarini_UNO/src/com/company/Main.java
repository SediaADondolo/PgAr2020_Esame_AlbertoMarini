package com.company;

import java.io.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("Controllo partite esistenti...");
        Gioco g;
        if (new File("partita.ser").isFile()){
            System.out.println("Sono presenti dei salvataggi\nPremere 1 per continuare\nPremere 2 per iniziare una nuova partite");
            Scanner sc = new Scanner(System.in);
            if (sc.nextInt() == 1 && false) { //purtroppo non sono riuscito a fare il caricamento
                 try {
                    g = leggiPartita();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                g = new Gioco();
            }
        } else {
            g = new Gioco();
        }
        if (g.gioca() == -1){
            try {
                salvaPartita(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * legge la partita
     * @return
     * @throws IOException
     */
    public static final Gioco leggiPartita() throws IOException {
        Gioco g = new Gioco();
        FileInputStream fileIn = new FileInputStream("partita.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        try {
            g = (Gioco) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        in.close();
        fileIn.close();
        System.out.printf("Partita letta!");
        return g;
    }

    /**
     * salva la partita
     * @param g
     * @throws IOException
     */
    public static final void salvaPartita(Gioco g) throws IOException {
        FileOutputStream fileOut = new FileOutputStream("partita.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(g);
        out.close();
        fileOut.close();
        System.out.printf("Partita salvata!");
    }
}
