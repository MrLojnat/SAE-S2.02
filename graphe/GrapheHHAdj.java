package graphe;

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
        if(contientSommet(source)){
            if(contientArc(source, destination)) throw new IllegalArgumentException("deja present");
            if(valeur < 0) throw new IllegalArgumentException("valuation negative");
        }
        else{
            if (!contientSommet(destination)) ajouterSommet(destination);
            if (!contientSommet(source)) ajouterSommet(source);
            if (valeur < 0) {
                oterSommet(source);
                oterSommet(destination);
                throw new IllegalArgumentException("valuation negative");
            }
        }
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
        ArrayList <String> listSommets = new ArrayList<>();
        for(Map.Entry<String, Map<String, Integer>> map : hhadj.entrySet())
            listSommets.add(map.getKey());
        return listSommets;
    }

    //a faire
    @Override
    public List<String> getSucc(String sommet) {
        Map<String,Integer> inner= this.hhadj.get(sommet);
        return new ArrayList<>(inner.keySet());
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
        if(contientSommet(src))
            return this.hhadj.get(src).containsKey(dest);
        return false;
    }
}
