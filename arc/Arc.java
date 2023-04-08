package arc;

public class Arc {

    private String noeud_source;
    private String noeud_destination;
    private int valeur;

    public Arc(String s, String d, int v){
        this.noeud_source = s;
        this.noeud_destination = d;
        this.valeur = v;
    }

    public String getSource() {
        return noeud_source;
    }

    public String getDestination() {
        return noeud_destination;
    }

    public int getValuation() {
        return valeur;
    }

}
