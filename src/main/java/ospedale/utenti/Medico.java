package ospedale.utenti;

import ospedale.clinica.Prestazione;
import ospedale.clinica.Ricovero;
import ospedale.personale.Assenza;
import ospedale.personale.Turno;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Utente{

    private List<Turno>turni;
    private List<Prestazione> prestazioni;
    private List<Assenza> assenze;

    //costruttore
    public Medico(String login, String password) {
        super(login, password);
        //inizializziamo le liste
        this.turni=new ArrayList<>();
        this.prestazioni=new ArrayList<>();
        this.assenze=new ArrayList<>();
    }

    public void registraPrestazione (Ricovero ricovero, LocalDateTime inizio, LocalDateTime fine){
        System.out.println("Prestazione Registrata");
    }

    //get per ottenere le liste
    public List<Turno> getTurni(){
        return turni;
    }

    public List<Prestazione> getPrestazioni(){
        return prestazioni;
    }
    public List<Assenza> getAssenze(){
        return assenze;
    }

    public void addTurno(Turno t){this.turni.add(t);}

    public void addPrestazioni(Prestazione p){this.prestazioni.add(p);}

    public void addAssenza(Assenza a){this.assenze.add(a);}

}
