package ospedale.utenti;

/**
 * Classe astratta che rappresenta un utente del sistema ospedaliero.
 * Funge da superclasse per tutti gli utenti che devono autenticarsi.
 */
public abstract class Utente {
    private String login;
    private String password;

    /**
     * Costruisce un utente con login e password specificati.
     *
     * @param login    il nome utente
     * @param password la password
     */
    public Utente(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /** Costruttore protetto per le sottoclassi. */
    protected Utente() {
    }

    /** Imposta il login dell'utente. */
    public void setLogin(String login) {
        this.login = login;
    }

    /** Restituisce il login dell'utente. */
    public String getLogin() {
        return login;
    }

    /** Imposta la password dell'utente. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Restituisce la password dell'utente. */
    public String getPassword() {
        return password;
    }
}