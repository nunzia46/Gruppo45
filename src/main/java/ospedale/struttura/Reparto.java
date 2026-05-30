package ospedale.struttura;

import ospedale.utenti.Medico;

import java.util.ArrayList;
import java.util.List;

public class Reparto {
    public String nome;
    private List<Stanza> stanze;
    private List<Medico> medici;

    //costruttore
    public Reparto(String nome){
        this.nome=nome;
        this.stanze=new ArrayList<>();
        this.medici=new ArrayList<>();
    }

    public void setNome(String nome){this.nome=nome;}
    public String getNome() {return nome;}

    public List<Stanza> getStanze(){return stanze;}
    public List<Medico> getMedici(){ return medici;}

    public void addStanza(Stanza s) {this.stanze.add(s);}
    public void addMedico(Medico m){this.medici.add(m);}


}
