package esstudente.java;
import java.util.*;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        ArrayList<Studente> Studenti = new ArrayList<Studente>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNascita1, dataNascita2, dataNascita3;
        try {
            dataNascita1 = sdf.parse("15/03/2005");
            dataNascita2 = sdf.parse("10/06/2004");
            dataNascita3 = sdf.parse("22/10/2006");
        } catch (Exception e) {
            dataNascita1 = new Date();
            dataNascita2 = new Date();
            dataNascita3 = new Date();
        }

        String nome="", cognome="", corsoDiStudi = "";
        int matricola=0;
        try (Scanner myObj = new Scanner(System.in)) {
            for(int i=0; i<3; i++){
                try {
                    System.out.println("Inserire nome per studente " + i);
                    nome = myObj.nextLine();

                    System.out.println("Inserire cognome per studente " + i);
                    cognome = myObj.nextLine();

                    while (true) {
                        try {
                            System.out.println("Inserire matricola per studente " + i);
                            matricola = myObj.nextInt();
                            myObj.nextLine(); 
                            break; 
                        } catch (InputMismatchException e) {
                            System.out.println("Inserisci una matricola valida.");
                            myObj.nextLine(); 
                        }
                    }

                    System.out.println("Inserire corso di studi per studente " + i);
                    corsoDiStudi = myObj.nextLine();

                    Studente studente = new Studente(nome, cognome, dataNascita1, matricola, corsoDiStudi);
                    Studenti.add(studente);
                } catch (NoSuchElementException e) {
                    System.out.println("Errore di input. Assicurati di inserire tutti i dati richiesti.");
                    break;
                }
            }
        }

        Studente studente1, studente2, studente3;

        studente1=Studenti.get(0);
        studente2=Studenti.get(1);
        studente3=Studenti.get(2);


        studente1.aggiungiVoto(28.0f);
        studente1.aggiungiVoto(30.0f);
        studente1.aggiungiVoto(25.0f);
        studente1.aggiungiVoto(28.0f);
        studente1.aggiungiVoto(30.0f);
        studente1.aggiungiVoto(25.0f);

        studente2.aggiungiVoto(27.0f);
        studente2.aggiungiVoto(29.0f);
        studente2.aggiungiVoto(26.0f);

        studente3.aggiungiVoto(28.0f);
        studente3.aggiungiVoto(30.0f);
        studente3.aggiungiVoto(26.0f);

        System.out.println("Informazioni sullo Studente 1: ");
        System.out.println(studente1.toString());  //errore quando si stampano i voti, out of bound, ho provato tutto ciò che mi è venuto in mente ma non riesco a risolverlo
        System.out.println("Media dei voti dello studente 1: " + studente1.calcolaMediaVoti());
        System.out.println("Età dello studente 1: " + studente1.calcolaEta() + " anni");

        System.out.println("\nInformazioni sullo studente 2: ");
        System.out.println(studente2.toString());
        System.out.println("Media dei voti dello studente 2: " + studente2.calcolaMediaVoti());
        System.out.println("Età dello studente 2: " + studente2.calcolaEta() + " anni");

        System.out.println("\nInformazioni sullo studente 3: ");
        System.out.println(studente3.toString());
        System.out.println("Media dei voti dello studente 3: " + studente2.calcolaMediaVoti());
        System.out.println("Età dello studente 3: " + studente2.calcolaEta() + " anni");
    }
}