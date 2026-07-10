package ospedale.struttura;

import java.util.ArrayList;
import java.util.List;

public class Stanza {
    private int numStanza;
    private List<Letto>letti;

    // costruttore
    public Stanza (int numStanza){
        this.numStanza=numStanza;
        this.letti= new ArrayList<>();
    }

    public void setNumStanza(int numStanza){this.numStanza=numStanza;}

    public int getNumStanza() {
        return numStanza;
    }

    public List<Letto> getLetti(){return letti;}
    public void addLetto(Letto l) {this.letti.add(l);}
}

