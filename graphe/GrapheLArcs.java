package graphe;
import arc.Arc;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class GrapheLArcs extends Graphe{
    private ArrayList<Arc> LArcs;
    public GrapheLArcs(){
        LArcs = new  ArrayList<Arc>();
    }
    public GrapheLArcs(String description_graphe){
        this();
        String[] s =  description_graphe.split(",");
        /*for (int i = 0; i < s.length; i++) {
            LArcs.add(new Arc(s[i].substring(0,0),s[i].substring(2,2),Integer.parseInt(s[i].substring(4,4))));
        }*/
        this.peupler(description_graphe);
    }
    @Override
    public void ajouterSommet(String noeud){
        if(!this.contientSommet(noeud)){
            LArcs.add(new Arc(noeud,"",-1));
        }
        //else
            //throw new IllegalArgumentException("le noeud existe deja");
    }
    @Override
    public void ajouterArc(String source, String destination, Integer valeur)throws IllegalArgumentException{
        if(valeur<0){
            throw new IllegalArgumentException("valuation négative");
        }
        Arc arc = new Arc(source,destination,valeur);
        if(!this.contientArc(source,destination)){
            LArcs.add(arc);
            for (int i = 0; i < LArcs.size(); i++) {
                if(LArcs.get(i).getSource().compareTo(source)==0 && LArcs.get(i).getValuation()==-1){
                    LArcs.remove(i);
                }
            }
        }
        else
            throw new IllegalArgumentException("déja présent");

    }
    @Override
    public void oterSommet(String noeud){
        if(this.contientSommet(noeud) ){
            for (int i = 0; i < LArcs.size(); i++) {
                if (LArcs.get(i).getSource() == noeud ) {
                    LArcs.add(i,new Arc("",LArcs.get(i).getDestination(),0));
                }
                if(LArcs.get(i).getDestination() == noeud){
                    LArcs.add(i,new Arc("",LArcs.get(i).getSource(),0));
                }
            }
        }
    }
    @Override
    public void oterArc(String source, String destination)throws IllegalArgumentException{
        if(this.contientArc(source,destination)){
            for (int i = 0; i < LArcs.size(); i++) {
                if(LArcs.get(i).getSource()==source && LArcs.get(i).getDestination()==destination){
                    LArcs.remove(i);
                }
            }
        }
        else
            throw new IllegalArgumentException("n'existe pas");
    }
    @Override
    public List<String> getSommets(){
        int cmp=0;
        ArrayList<String> som = new ArrayList<String>();
        for (int i = 0; i < LArcs.size(); i++) {
            for (int j = 0; j < som.size(); j++) {
                if(LArcs.get(i).getSource().compareTo(som.get(j))==0){
                    cmp=1;
                }
            }
            if(cmp==0)
                som.add(LArcs.get(i).getSource());
            cmp=0;
        }

        return som;
    } // pas forcement triee
    @Override
    public List<String> getSucc(String sommet){
        int cmp=0;
        ArrayList<String> succ = new ArrayList<String>();
            for (int i = 0; i < LArcs.size(); i++) {
                if(LArcs.get(i).getSource().compareTo(sommet)==0 ){
                    for (int j = 0; j < succ.size(); j++) {
                        if(succ.get(j).compareTo(LArcs.get(i).getDestination())==0){
                            cmp=1;
                        }
                    }
                    if(cmp==0 && LArcs.get(i).getDestination().compareTo("")!=0)
                        succ.add(LArcs.get(i).getDestination());
                    cmp=0;
                }
            }
        return succ;
    } // pas forcement triee
    @Override
    public int getValuation(String src, String dest){
        for (int i = 0; i < LArcs.size(); i++) {
            if (LArcs.get(i).getSource().compareTo(src)==0 && LArcs.get(i).getDestination().compareTo(dest) == 0) {
                return LArcs.get(i).getValuation();
            }
        }
        return -1;
    } // les sommets doivent exister, -1 si pas d'arc
    @Override
    public boolean contientSommet(String sommet){
        List<String> clone=this.getSommets();
        for (int i = 0; i <clone.size(); i++) {
            if(clone.get(i).compareTo(sommet)==0){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean contientArc(String src, String dest){
        for (int i = 0; i < LArcs.size(); i++) {
            if(LArcs.get(i).getSource().compareTo(src)==0 && LArcs.get(i).getDestination().compareTo(dest)==0){
                return true;
            }
        }
        return false;
    }
}
