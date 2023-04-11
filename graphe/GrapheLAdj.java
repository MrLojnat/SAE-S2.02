package graphe;

import arc.Arc;

import java.util.*;

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
        if(contientSommet(source)){
            if(contientArc(source, destination)) throw new IllegalArgumentException("deja present");
            else if(valeur < 0) throw new IllegalArgumentException("valuation negative");
        }
        else {
            if (!contientSommet(destination)) ajouterSommet(destination);
            if (!contientSommet(source)) ajouterSommet(source);
            if (valeur < 0) {
                oterSommet(source);
                oterSommet(destination);
                throw new IllegalArgumentException("valuation negative");
            }
        }
        this.ladj.get(source).add(new Arc(source, destination, valeur));
    }

    @Override
    public void oterSommet(String noeud) {
        this.ladj.remove(noeud);
    }

    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination)) throw new IllegalArgumentException("n'existe pas");
        else {
            for (int i = 0; i < this.ladj.get(source).size(); ++i) {
                if (this.ladj.get(source).get(i).getDestination().equals(destination)) this.ladj.get(source).remove(i);
            }
        }
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
        if(contientSommet(src)) {
            for (int i = 0; i < this.ladj.get(src).size(); ++i) {
                return this.ladj.get(src).get(i).getDestination().equals(dest);
            }
        }
        return false;
    }

    public String toString(){
        Map<String, List<Arc>> mapTriee = new TreeMap<>(this.ladj);
        StringBuilder sb = new StringBuilder();
        int cmp = 0;
        for(Map.Entry<String, List<Arc>> mapTmp : mapTriee.entrySet()) {
            String source = mapTmp.getKey();
            ArrayList<Arc> tmpArc = new ArrayList<>(mapTriee.get(source));
            if(tmpArc.isEmpty()){
                sb.append(source + ":");
                if(cmp != this.ladj.size() - 1) sb.append(", ");
            }
            else{
                Collections.sort(tmpArc, new Comparator<Arc>() {
                    @Override
                    public int compare(Arc o1, Arc o2) {
                        int r = o1.getSource().compareTo(o2.getSource());
                        if(r == 0)
                            r = o1.getDestination().compareTo(o2.getDestination());
                        return r;
                    }
                });
                for(Arc arc : tmpArc) {
                    sb.append(arc.getSource() + "-" + arc.getDestination() + "(" + arc.getValuation() + ")");
                    if(cmp != this.ladj.size() - 1) sb.append(", ");
                }
            }
            cmp++;
        }
        return sb.toString();
    }
}
