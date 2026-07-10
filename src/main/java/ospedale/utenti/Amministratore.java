package ospedale.utenti;

import ospedale.clinica.Paziente;
import ospedale.clinica.Ricovero;
import ospedale.personale.Assenza;
import ospedale.struttura.Letto;

import java.time.LocalDateTime;

public class Amministratore extends Utente{
    public void registraRicovero(Paziente paziente, Letto letto, LocalDateTime inizio, LocalDateTime fine){
        Ricovero ricovero = new Ricovero(inizio, paziente, letto);
        ricovero.setDimissionePrevista(fine);
        paziente.addRicovero(ricovero);
        System.out.println("Ricovero registrato per il paziente:"+paziente.getNome());
    }

    public void registraAssenza(Medico medico, LocalDateTime inizio, LocalDateTime fine){
        Assenza assenza = new Assenza(inizio, fine);
        medico.addAssenza(assenza);
        System.out.println("Assenza registrata per il medico.");
    }
}
