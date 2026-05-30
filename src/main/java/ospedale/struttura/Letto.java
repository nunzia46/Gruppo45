package ospedale.struttura;

public class Letto {
    private String codiceIdentificativo;

    public Letto(String codiceIdentificativo){
        this.codiceIdentificativo= codiceIdentificativo;
    }

    public void setCodiceIdentificativo(String codiceIdentificativo) {
        this.codiceIdentificativo = codiceIdentificativo;
    }

    public String getCodiceIdentificativo(){
        return codiceIdentificativo;
    }
}
