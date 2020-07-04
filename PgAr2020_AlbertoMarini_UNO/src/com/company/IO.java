package com.company;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;

public class IO {

    XMLInputFactory xmlif = null;
    XMLStreamReader xmlr = null;

    public void initReader(String nome_file) {

        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(new FileInputStream(nome_file));
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
        }
    }

    public void readXML() {
        try {
            String coloreattuale = null;

            while (xmlr.hasNext()) {

                if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) { // inizio di un elemento: stampa il nome del tag e i suoi attributi

                    for (int i = 0; i < xmlr.getAttributeCount(); i++) {

                        switch (xmlr.getAttributeLocalName(i)) {
                            case "colore":
                                coloreattuale = xmlr.getAttributeValue(i);
                                break;
                            case "valore":
                                vario(xmlr.getAttributeValue(i), coloreattuale);
                                break;
                        }

                    }
                }
                xmlr.next();
            }

        } catch (Exception e) {
            System.out.println("Errore nella lettura:");
        }

    }


    public void vario(String i, String colore) {

        switch (i) {
            case "PescaDue":
                //Andrebbe "allacciato"
                break;
            case "Stop":
                //Andrebbe "allacciato"
                break;
            case "CambioGiro":
                //Andrebbe "allacciato"
            default:
                //Andrebbe "allacciato"
                break;

        }
    }

}
