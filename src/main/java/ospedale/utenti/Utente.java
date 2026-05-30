package ospedale.utenti;

public abstract class Utente{
    private String login;
    private String password;

    //costruttore
    public Utente (String login, String password){
        this.login=login;
        this.password=password;
    }

    protected Utente() {
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return password;
    }
}

