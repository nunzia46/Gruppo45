package ospedale.utenti;

import ospedale.clinica.Paziente;
import ospedale.struttura.Letto;

import java.time.LocalDateTime;

public class Amministratore extends Utente{
    public void registraRicovero(Paziente paziente, Letto letto, LocalDateTime inizio, LocalDateTime fine){
        System.out.println("Ricovero registrato per il paziente:"+paziente.getNome());
    }

    public void registraAssenza(Medico medico, LocalDateTime inizio, LocalDateTime fine){
        System.out.println("Assenza registrata per il medico.");
    }
}
