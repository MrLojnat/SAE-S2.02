package graphe;

import arc.Arc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheLAdj implements IGraphe{
    private Map<String, List<Arc>> ladj;

    public GrapheLAdj(){
        this.ladj = new HashMap<>();
    }
    public GrapheLAdj(String description_graphe) {
        this.ladj = new HashMap<>();
        this.peupler(description_graphe);
    }

    @Override
    public void ajouterSommet(String noeud) {
        this.ladj.putIfAbsent(noeud, new ArrayList<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) throws IllegalArgumentException{
        if(this.ladj.containsKey(source)){
            if(!this.ladj.get(source).contains(destination)){
                if(valeur < 0)
                    throw new IllegalArgumentException("valuation negative");
            }
            else
                throw new IllegalArgumentException("deja present");
        }
        else
            if(!contientSommet(destination))
                ajouterSommet(destination);
            ajouterSommet(source);
            if(!this.ladj.get(source).contains(destination)){
                if(valeur < 0){
                    oterSommet(source);
                    oterSommet(destination);
                    throw new IllegalArgumentException("valuation negative");
                }
            }
            Arc a = new Arc(source, destination, valeur);
            this.ladj.get(source).add(a);
    }

    @Override
    public void oterSommet(String noeud) {
        this.ladj.remove(noeud);
    }

    @Override
    public void oterArc(String source, String destination) {
        if(!contientArc(source, destination))
            throw new IllegalArgumentException("n'existe pas");
        else
                    this.ladj.get(source).remove(destination);
    }

    @Override
    public List<String> getSommets() {
        ArrayList <String> listSommets = new ArrayList<>();
        for(Map.Entry<String, List<Arc>> map : this.ladj.entrySet())
            listSommets.add(map.getKey());
        return listSommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        ArrayList<String> listSucc = new ArrayList<>();
        for(int i = 0; i < this.ladj.get(sommet).size(); ++i){
            listSucc.add(this.ladj.get(sommet).get(i).getDestination());
        }
        return listSucc;
    }

    @Override
    public int getValuation(String src, String dest) {
        if(contientSommet(src) && contientSommet(dest)){
            for (int i = 0; i < this.ladj.get(src).size(); ++i){
                if(this.ladj.get(src).get(i).getDestination().equals(dest))
                    return this.ladj.get(src).get(i).getValuation();
            }
        }
        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return this.ladj.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        String d = dest;
        if(contientSommet(src)) {
            for (int i = 0; i < this.ladj.get(src).size(); ++i) {
                return this.ladj.get(src).get(i).getDestination().equals(dest);
            }
        }
        return false;
    }
}
