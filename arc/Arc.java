package arc;//

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
    public boolean equals(Arc a){
        return a.noeud_source==this.noeud_source && a.noeud_destination==this.noeud_destination;

    }
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(noeud_source);
        if(valeur==-1){
            s.append(":");
        }
        else
            s.append("-"+noeud_destination+"("+valeur+")");
        s.append(", ");
        return s.toString();
    }
}
