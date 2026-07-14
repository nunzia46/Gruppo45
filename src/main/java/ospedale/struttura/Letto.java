package ospedale.struttura;

/**
 * Rappresenta un letto all'interno della struttura ospedaliera.
 * Identificato da un codice univoco.
 */
public class Letto {
    private String codiceIdentificativo;

    /**
     * Costruisce un letto con il codice specificato.
     *
     * @param codiceIdentificativo il codice identificativo del letto
     */
    public Letto(String codiceIdentificativo) {
        this.codiceIdentificativo = codiceIdentificativo;
    }

    /** Imposta il codice identificativo del letto. */
    public void setCodiceIdentificativo(String codiceIdentificativo) {
        this.codiceIdentificativo = codiceIdentificativo;
    }

    /** Restituisce il codice identificativo del letto. */
    public String getCodiceIdentificativo() {
        return codiceIdentificativo;
    }
}