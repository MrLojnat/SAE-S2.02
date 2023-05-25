package graphe.implems;

import graphe.core.Arc;
import java.io.FileNotFoundException;
import java.util.*;

public class GrapheLArcs extends Graphe{
    private ArrayList<Arc> LArcs;

    public GrapheLArcs(){
        LArcs = new  ArrayList<Arc>();
    }

    public GrapheLArcs(String description_graphe){
        this();
        this.peupler(description_graphe);
    }

    @Override
    public void ajouterSommet(String noeud){
        if(!this.contientSommet(noeud)){
            LArcs.add(new Arc(noeud,"",-1));
        }
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur)throws IllegalArgumentException{
        if (valeur < 0) throw new IllegalArgumentException("valuation négative");
        if (contientArc(source,destination)) throw new IllegalArgumentException("l'arc existe déjà");

        Arc arc = new Arc(source,destination,valeur);
        LArcs.add(arc);
    }

    @Override
    public void oterSommet(String noeud){
        if(this.contientSommet(noeud) ){
            for (int i = 0; i < LArcs.size(); i++) {
                if (LArcs.get(i).getSource().equals(noeud)) {
                    LArcs.add(i,new Arc("",LArcs.get(i).getDestination(),0));
                }
                if(LArcs.get(i).getDestination().equals(noeud)){
                    LArcs.add(i,new Arc("",LArcs.get(i).getSource(),0));
                }
            }
        }
    }

    @Override
    public void oterArc(String source, String destination)throws IllegalArgumentException{
        if(this.contientArc(source,destination)){
            for (int i = 0; i < LArcs.size(); i++) {
                if(LArcs.get(i).getSource().equals(source) && LArcs.get(i).getDestination().equals(destination)){
                    LArcs.remove(i);
                }
            }
        }
        else
            throw new IllegalArgumentException("n'existe pas");
    }

    @Override
    public List<String> getSommets(){
        Set<String> sommets = new HashSet<>();

        for (Arc a: LArcs) {
            sommets.add(a.getSource());
            sommets.add(a.getDestination());
        }

        sommets.remove("");
        return new ArrayList<>(sommets);
    }

    @Override
    public List<String> getSucc(String sommet){
        List<String> succ = new ArrayList<>();

        for (Arc a: LArcs) {
            if (a.getSource().equals(sommet) && !a.getDestination().equals("")) succ.add(a.getDestination());
        }

        return succ;
    }

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
        List<String> clone = this.getSommets();
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
