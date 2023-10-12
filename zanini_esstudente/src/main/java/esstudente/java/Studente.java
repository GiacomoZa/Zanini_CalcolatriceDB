package esstudente.java;

import java.util.*;
import java.text.SimpleDateFormat;

public class Studente {

    private String nome;
    private String cognome;
    private String corsoDiStudi;
    private ArrayList<Float> voti = new ArrayList<Float>();
    private int matricola;
    private Date dataDiNascita;

    public Studente(String nome, String cognome, Date dataDiNascita, int matricola, String corsoDiStudi) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.matricola = matricola;
        this.corsoDiStudi = corsoDiStudi;
    }

    public String getNome(){
        return nome;
    }

    public String getCognome(){
        return cognome;
    }

    public String getcorsoDiStudi(){
        return corsoDiStudi;
    }

    public int getMatricola(){
        return matricola;
    }

    public void setNome(String n){
        nome=n;
    }

    public void setCognome(String c){
        cognome=c;
    }

    public void  setMatricola(int m){
        matricola=m;
    }

    public void setcorsoDiStudi(String c){
        corsoDiStudi=c;
    }

    public void aggiungiVoto(float voto) {
        try{
            if(voto <= 0 || voto > 30){
                throw new Exception("Inserire un nuovo voto", null);
            }else {
            voti.add(voto);
            }
        }catch(Exception e){
            System.out.println("Errore nell'inserimento dei voti");
        }
    }

    public void rimuoviVoti(int indice) {
        voti.remove(indice);
    }

    public float calcolaMediaVoti() {
        float somma = 0.0f;
        int conteggio = 0;
        for (int i = 0; i < 10; i++) {
            if (voti.get(i) > 0) {
                somma += voti.get(i);
                conteggio++;
            }
        }
        if (conteggio > 0) {
            return somma / conteggio;
        } else {
            return 0.0f;
        }
    }

    public int calcolaEta() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataDiNascita);
        Calendar oggi = Calendar.getInstance();
        int annoNascita = cal.get(Calendar.YEAR);
        int meseNascita = cal.get(Calendar.MONTH);
        int giornoNascita = cal.get(Calendar.DAY_OF_MONTH);

        int annoOggi = oggi.get(Calendar.YEAR);
        int meseOggi = oggi.get(Calendar.MONTH);
        int giornoOggi = oggi.get(Calendar.DAY_OF_MONTH);

        int eta = annoOggi - annoNascita;
        if (meseOggi < meseNascita || (meseOggi == meseNascita && giornoOggi < giornoNascita)) {
            eta--;
        }
        return eta;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Cognome: ").append(cognome).append("\n");
        sb.append("Data di nascita: ").append(sdf.format(dataDiNascita)).append("\n");
        sb.append("Matricola: ").append(matricola).append("\n");
        sb.append("Corso di studi: ").append(corsoDiStudi).append("\n");
        sb.append("Voti:\n");
        for (int i = 0; i < voti.size(); i++) {
            sb.append("Voto ").append(i + 1).append(": ");
        if (i < voti.size()) {  //errore out of bound
            sb.append(voti.get(i));
        }
        sb.append("\n");
    }
        return sb.toString();
    }
}
