package com.company;

import java.util.*;

public class Gioco implements java.io.Serializable {

    static boolean direzione = true;
    private int numeroGiocatori;
    private int giocatoreTurno;

    Mazzo mazzo;
    Mazzo mazzoScarti;
    Carta ultimaCartaScartata;

    List<Giocatore> giocatori = new ArrayList<Giocatore>();
    transient Scanner sc = new Scanner(System.in);

    /**
     * parte iniziale del gioco
     */
    public Gioco() {
        System.out.println("Inserire numero Giocatori: ");
        this.numeroGiocatori = sc.nextInt();
        giocatoreTurno = 0;
        creaMazzo();
        aggiungiCarteSpeciali(120);
        creaGiocatori(numeroGiocatori);
        daiCarteGiocatori(7);
    }

    /**
     * crea il mazzo
     */
    private void creaMazzo() {
        mazzo = new Mazzo();
        mazzoScarti = new Mazzo();
        mazzo.riempiMazzo();
        mazzo.mescola();
        ultimaCartaScartata = mazzo.pesca(); //prima carta degli scarti
        //     mazzo.stampaMazzo();
    }

    /**
     * Ad ogni enum è associato un colore
     * @param i
     * @return
     */
    private Colori intColori(int i) {
        switch (i) {
            case 0:
                return Colori.GIALLO;
            case 1:
                return Colori.ROSSO;
            case 2:
                return Colori.BLU;
            case 3:
                return Colori.VERDE;
            default:
                return Colori.GIALLO;
        }
    }

    /**
     * ogni valore corrsisponde alla carta speciale
     * @param i
     * @return
     */
    private ValoreCarta intValoreCarta(int i) {
        switch (i) {
            case 0:
                return ValoreCarta.PESCA2;
            case 1:
                return ValoreCarta.PESCA4;
            case 2:
                return ValoreCarta.CAMBIOCOLORE;
            case 3:
                return ValoreCarta.SALTATURNO;
            case 4:
                return ValoreCarta.INVERTIGIRO;
            case 5:
                return ValoreCarta.SHIFTLEFTLOGIC;
            default:
                return ValoreCarta.PESCA2;
        }
    }

    /**
     * aggiunta carte speciali
     * @param numeroSpeciali
     */
    private void aggiungiCarteSpeciali(int numeroSpeciali) {
        Random rand = new Random();
        int vc;
        Colori c = intColori(rand.nextInt(4));
        for (int i = 0; i < numeroSpeciali; i++) {
            vc = rand.nextInt(6);
            mazzo.aggiungiCarta(new Carta(0, c, true, intValoreCarta(vc)));
        }
        mazzo.mescola();
        //  mazzo.stampaMazzo();
    }

    /**
     * aggiunge i giocatori
     * @param n numero di giocatori
     */
    private void creaGiocatori(int n) {
        for (int i = 0 ; i < n ; ++i){
            giocatori.add(new Giocatore(String.valueOf(i)));
        }
    }

    /**
     * i giocatori pescano le carte iniziali
     * @param n carte
     */
    public void daiCarteGiocatori(int n) {
        //    System.out.println("Prima: " + mazzo.numeroCarte());
        for (Giocatore g : giocatori) {
            for (int i = 0; i < n; i++) {
                g.daiCarta(mazzo.pesca());
            }
        }
        //     System.out.println(giocatori);
        //     System.out.println(mazzo.numeroCarte());
    }

    /**
     * l'effettiva partita
     * @return mi serve per salvare la partita
     */
    public int gioca() {
        int numeroCarteGiocatore;
        int indiceCartaDaGiocare = 0;
        int giocatoreTurno = tiraDado(numeroGiocatori);
        int indiceCarta;
        List<Carta> carteGiocatore;
        Carta carta;
        boolean giocoContinua = true;
        while (giocoContinua) {
            for (Giocatore g : giocatori) {
                if (g.getCarte().size() == 0) {
                    giocoContinua = false;
                    System.out.println("Ha vinto il giocatore: " + g.getNome());
                    return 1;
                }
            }
            System.out.println("Giocatore: " + giocatori.get(giocatoreTurno).getNome());
            System.out.println("Ultima carta scartata: " + ultimaCartaScartata);

            carteGiocatore = giocatori.get(giocatoreTurno).getCarte();
            numeroCarteGiocatore = carteGiocatore.size();

            if (controlloPescataObbligatoria(giocatoreTurno, carteGiocatore)) {
                System.out.println("Nessuna carta giocabile, pescata obbligatoria!");
                if (controlloMazzoVuoto()) {
                    mazzo = mazzoScarti;
                    mazzo.mescola();
                    System.out.println("------------------------------------");
                    System.out.println("\n\nMazzo rimescolato\n\n");
                    System.out.println("------------------------------------");
                }
                System.out.println("La carta pescata è:");
                carta = mazzo.pesca();
                giocatori.get(giocatoreTurno).getCarte().add(carta);
                System.out.println(carta);
            }
            if (controlloPescataObbligatoria(giocatoreTurno, carteGiocatore)) {
                System.out.println("Nessuna carta giocabile, obbligo di passare il turno!");
                System.out.println("\nNuovo Turno!");
                giocatoreTurno = avanzaTurno(giocatoreTurno, numeroGiocatori);
                continue;
            }

            System.out.println("Elenco carte:");
            for (int i = 0; i < numeroCarteGiocatore; i++) {
                System.out.println(carteGiocatore.get(i) + " - Indice: " + i);
            }

            boolean valida = false;
            while (!valida) {
                System.out.println("Scegli la carta da giocare: ");
                indiceCartaDaGiocare = sc.nextInt();

                if (indiceCartaDaGiocare == -1) {
                    return -1;
                }

                if (indiceCartaDaGiocare < 0 || indiceCartaDaGiocare >= carteGiocatore.size()) continue;
                valida = ultimaCartaScartata.giocataValida(carteGiocatore.get(indiceCartaDaGiocare));
                if (!valida) System.out.println("Carta non valida, sceglierne un'altra!");
            }
            mazzoScarti.aggiungiCarta(ultimaCartaScartata);

            ultimaCartaScartata = giocatori.get(giocatoreTurno).giocaCarta(indiceCartaDaGiocare);
            if (ultimaCartaScartata.speciale) {
                switch (ultimaCartaScartata.effetto) {
                    case PESCA2:
                        carta = mazzo.pesca();
                        giocatoreTurno = avanzaTurno(giocatoreTurno, numeroGiocatori);
                        giocatori.get(giocatoreTurno).getCarte().add(carta);
                        carta = mazzo.pesca();
                        giocatori.get(giocatoreTurno).getCarte().add(carta);
                    case PESCA4:
                        carta = mazzo.pesca();
                        giocatoreTurno = avanzaTurno(giocatoreTurno, numeroGiocatori);
                        giocatori.get(giocatoreTurno).getCarte().add(carta);
                        carta = mazzo.pesca();
                        giocatori.get(giocatoreTurno).getCarte().add(carta);
                        carta = mazzo.pesca();
                        giocatori.get(giocatoreTurno).getCarte().add(carta);
                        carta = mazzo.pesca();
                        giocatori.get(giocatoreTurno).getCarte().add(carta);
                        System.out.println("Scegli un colore (0 GIALLO, 1 ROSSO, 2 BLU, 3 VERDE)");
                        int i = sc.nextInt();
                        switch (i) {
                            case 0:
                                ultimaCartaScartata.setColore(Colori.GIALLO);
                                break;
                            case 1:
                                ultimaCartaScartata.setColore(Colori.ROSSO);
                                break;
                            case 2:
                                ultimaCartaScartata.setColore(Colori.BLU);
                                break;
                            case 3:
                                ultimaCartaScartata.setColore(Colori.VERDE);
                                break;
                            default:
                                System.out.println("Non hai inserito un valore valido, per punizione perdi la possibilità di scegliere il colore!");
                                break;
                        }
                    case CAMBIOCOLORE:
                        System.out.println("Scegli un colore (0 GIALLO, 1 ROSSO, 2 BLU, 3 VERDE)");
                        int w = sc.nextInt();
                        switch (w) {
                            case 0:
                                ultimaCartaScartata.setColore(Colori.GIALLO);
                                break;
                            case 1:
                                ultimaCartaScartata.setColore(Colori.ROSSO);
                                break;
                            case 2:
                                ultimaCartaScartata.setColore(Colori.BLU);
                                break;
                            case 3:
                                ultimaCartaScartata.setColore(Colori.VERDE);
                                break;
                            default:
                                System.out.println("Non hai inserito un valore valido, per punizione perdi la possibilità di scegliere il colore!");
                                break;
                        }
                    case SALTATURNO:
                        giocatoreTurno = avanzaTurno(giocatoreTurno, numeroGiocatori);
                    case INVERTIGIRO:
                        direzione = !direzione;
                        giocatoreTurno = avanzaTurno(giocatoreTurno, numeroGiocatori);
                    case SHIFTLEFTLOGIC:
                        //Mancanza di tempo, viene considerato un turno normale
                        giocatoreTurno = avanzaTurno(giocatoreTurno, numeroGiocatori);
                        break;
                    default:
                        giocatoreTurno = avanzaTurno(giocatoreTurno, numeroGiocatori);
                        break;
                }
            }
            System.out.println("\nNuovo Turno!");
        }

        return 0;
    }

    /**
     * passa al turno successivo
     * @param turno
     * @param numeroGiocatori
     * @return
     */
    public int avanzaTurno(int turno, int numeroGiocatori) {
        if (direzione) {
            turno++;
        } else {
            turno--;
        }
        turno = turno % numeroGiocatori;
        if (turno <= 0) turno = numeroGiocatori-1;
        return turno;
    }

    /**
     * dado per scegliere la partenza
     * @param numeroGiocatori
     * @return
     */
    private int tiraDado(int numeroGiocatori) {
        int dado = 0;
        Giocatore migliore = new Giocatore("temp");
        migliore.setDado(0);
        List<Giocatore> giocatoriDado = new ArrayList<Giocatore>(giocatori);
        int giocatoreVincente = -1;
        int dadoGiocatoreVincente = 0;
        ListIterator<Giocatore> iter;
        Random rand = new Random();
        Giocatore g;
        while (true) {
            for (Giocatore gioc : giocatoriDado) {
                gioc.setDado(1 + rand.nextInt(6));
            }
            iter = giocatoriDado.listIterator();
            if (iter.hasNext()) migliore = iter.next();
            if (iter.hasNext()) {
                g = iter.next();
                if (g.getDado() > migliore.getDado()) {
                    giocatoriDado.remove(migliore);
                    migliore = g;
                    //System.out.println("Rimosso");
                }
            }
            if (giocatoriDado.size() == 1) break;
        }
        return giocatori.indexOf(migliore);
    }

    /**
     * controlla la capacità del mazzo
     * @return
     */
    public boolean controlloMazzoVuoto() {
        return mazzo.isVuoto();
    }

    /**
     * controlla se un giocatore deve pescare obbligatoriamente date le sue carte
     * @param indiceGiocatore
     * @param carte
     * @return
     */
    public boolean controlloPescataObbligatoria(int indiceGiocatore, List<Carta> carte) {
        for (Carta c : carte) {
            if (ultimaCartaScartata.giocataValida(c)) return false;
        }
        return true;
    }


}
















