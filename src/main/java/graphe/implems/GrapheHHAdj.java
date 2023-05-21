package graphe.implems;

import java.util.*;

public class GrapheHHAdj extends Graphe{
    private Map<String, Map<String, Integer>> hhadj;

    public GrapheHHAdj(String description_graphe){
        this.hhadj = new HashMap<>();
        this.peupler(description_graphe);
    }

    public GrapheHHAdj(){
        this.hhadj = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        this.hhadj.putIfAbsent(noeud, new HashMap<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) throws IllegalArgumentException{
        if (contientArc(source, destination)) throw new IllegalArgumentException("deja present");
        if (valeur < 0) throw new IllegalArgumentException("valuation negative");
        if (!contientSommet(destination)) ajouterSommet(destination);
        if (!contientSommet(source)) ajouterSommet(source);
        this.hhadj.get(source).put(destination, valeur);
    }

    @Override
    public void oterSommet(String noeud){
        hhadj.remove(noeud);
    }

    @Override
    public void oterArc(String source, String destination) throws IllegalArgumentException {
        if(!contientArc(source, destination))
            throw new IllegalArgumentException("n'existe pas");
        else
            this.hhadj.get(source).remove(destination);
    }

    //a faire
    @Override
    public List<String> getSommets() {
        return new ArrayList<>(hhadj.keySet());
    }

    //a faire
    @Override
    public List<String> getSucc(String sommet) {
        return new ArrayList<>(this.hhadj.get(sommet).keySet());
    }

    @Override
    public int getValuation(String src, String dest) {
        return this.hhadj.get(src).get(dest);
    }

    @Override
    public boolean contientSommet(String sommet) {
        return this.hhadj.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (contientSommet(src))
            return this.hhadj.get(src).containsKey(dest);
        return false;
    }
}
