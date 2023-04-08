package graphe;

import java.util.*;

public class GrapheHHAdj implements IGraphe{
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
        if(this.hhadj.containsKey(source)){
            if (!this.hhadj.get(source).containsKey(destination)){
                if (valeur < 0)
                    throw new IllegalArgumentException("valuation negative");
            }
            else
                throw new IllegalArgumentException("deja present");
        }
        else {
            if(!contientSommet(destination))
                ajouterSommet(destination);
            ajouterSommet(source);
            if (!this.hhadj.get(source).containsKey(destination)){
                if (valeur < 0) {
                    oterSommet(source);
                    oterSommet(destination);
                    throw new IllegalArgumentException("valuation negative");
                }
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
        ArrayList<String> listSucc = new ArrayList<>(inner.keySet());
        return listSucc;
    }

    @Override
    public int getValuation(String src, String dest) {
        int value = this.hhadj.get(src).get(dest);
        return value;
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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        int cmp = 0;
        Map<String,Map<String, Integer>> Map = new TreeMap<>(this.hhadj);
        for(Map.Entry<String, Map<String, Integer>> map : Map.entrySet()){
            String source = map.getKey();
            Map<String, Integer> inner = new TreeMap<>(Map.get(source));
            if(inner.isEmpty()){
                sb.append(source +":");
                if(cmp != this.hhadj.size() - 1)
                    sb.append(", ");
            }
            else {
                for (Map.Entry<String, Integer> mapInner : inner.entrySet()) {
                    sb.append(source + "-" + mapInner.getKey() + "(" + mapInner.getValue() + ")");
                    if(cmp != this.hhadj.size() - 1)
                        sb.append(", ");

                }
            }
            cmp++;
        }
        return sb.toString();
    }
}
