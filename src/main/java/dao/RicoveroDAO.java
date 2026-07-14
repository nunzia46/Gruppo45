package dao;

import ospedale.clinica.Paziente;
import ospedale.clinica.Ricovero;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaccia DAO per la gestione dei Ricoveri e le logiche di sovrapposizione.
 */
public interface RicoveroDAO {
    void salva(Ricovero ricovero);
    void aggiorna(Ricovero ricovero);
    void elimina(Ricovero ricovero);
    List<Ricovero> trovaTutti();

    /**
     * Verifica l'indisponibilità di un posto letto per nuovi ricoveri in un dato intervallo.
     * @param codiceLetto Il codice identificativo del letto.
     * @param inizio Data e ora di inizio ricovero.
     * @param fine Data e ora di dimissione prevista.
     * @return true se il letto è già occupato, false se è libero.
     */
    boolean isLettoOccupato(String codiceLetto, LocalDateTime inizio, LocalDateTime fine);

    /**
     * Fornisce l'elenco dei pazienti in scadenza di dimissione in una data specifica.
     * @param dataScadenza La data in cui verificare le dimissioni.
     * @return Lista di pazienti in scadenza.
     */
    List<Paziente> trovaPazientiInScadenza(LocalDate dataScadenza);
}