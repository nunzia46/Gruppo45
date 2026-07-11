package ospedale.clinica;

import java.util.ArrayList;
import java.util.List;

public class Paziente {
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private List<Ricovero> ricoveri;

    public Paziente(String nome, String cognome, String codiceFiscale){
        this.nome=nome;
        this.cognome=cognome;
        this.codiceFiscale=codiceFiscale;
        this.ricoveri= new ArrayList<>();
    }

    public void setNome(String nome) {this.nome = nome;}
    public String getNome(){return nome;}

    public void setCognome(String cognome) { this.cognome = cognome;}
    public String getCognome(){return cognome;}

    public void setCodiceFiscale(String codiceFiscale) {this.codiceFiscale=codiceFiscale;}
    public String getCodiceFiscale() {return codiceFiscale;}

    public void addRicovero(Ricovero r) {this.ricoveri.add(r);}
}
