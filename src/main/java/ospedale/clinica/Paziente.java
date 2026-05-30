package ospedale.clinica;

public class Paziente {
    private String nome;
    private String cognome;
    private String codiceFiscale;

    public Paziente(String nome, String cognome, String codiceFiscale){
        this.nome=nome;
        this.cognome=cognome;
        this.codiceFiscale=codiceFiscale;
    }

    public Paziente(String mario, String rossi) {
    }

    public void setNome(String nome) {this.nome = nome;}
    public String getNome(){return nome;}

    public void setCognome(String cognome) { this.cognome = cognome;}
    public String getCognome(){return cognome;}

    public void setCodiceFiscale(String codiceFiscale) {this.codiceFiscale=codiceFiscale;}
    public String getCodiceFiscale() {return codiceFiscale;}
}
