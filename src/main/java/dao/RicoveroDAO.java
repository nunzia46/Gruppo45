package dao;

import ospedale.clinica.Paziente;
import ospedale.clinica.Ricovero;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RicoveroDAO {
    void salva(Ricovero ricovero);
    void aggiorna(Ricovero ricovero);
    void elimina(Ricovero ricovero);
    List<Ricovero> trovaTutti();

    // Verifica l'indisponibilità di un posto letto per nuovi ricoveri in un dato intervallo.
    boolean isLettoOccupato(String codiceLetto, LocalDateTime inizio, LocalDateTime fine);

    // Fornisce l'elenco dei pazienti in scadenza di dimissione in una data specifica.
    List<Paziente> trovaPazientiInScadenza(LocalDate dataScadenza);
}
