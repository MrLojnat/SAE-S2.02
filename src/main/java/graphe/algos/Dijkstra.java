package graphe.algos;

import graphe.core.*;

import java.util.Map.Entry;

import java.util.*;

public class Dijkstra {
    public static void dijkstra(IGrapheConst g, String source, Map<String, Integer> dist, Map<String, String> prev) {
        Set<String> noeudsDecouverts = new HashSet<>();
        PriorityQueue<Map.Entry<String,Integer>> pq = new PriorityQueue<>(new SommetComparator());

        dist.put(source, 0);
        pq.add(new AbstractMap.SimpleEntry<>(source, 0));

        while (noeudsDecouverts.size() != g.getSommets().size()) {
            if (pq.isEmpty()) return;
            Map.Entry<String, Integer> u = pq.remove();

            if (noeudsDecouverts.contains(u.getKey())) continue;
            noeudsDecouverts.add(u.getKey());

            for (String noeud : g.getSucc(u.getKey())) {
                if (!noeudsDecouverts.contains(noeud)) {
                    int distanceArc = g.getValuation(u.getKey(), noeud);
                    int distance = distanceArc + u.getValue();

                    if (!dist.containsKey(noeud) || distance < dist.get(noeud) ) {
                        dist.put(noeud, distance);
                        prev.put(noeud, u.getKey());
                    }
                    pq.add(new AbstractMap.SimpleEntry<>(noeud, distance));
                }
            }
        }
    }
}

class SommetComparator implements Comparator<Entry<String,Integer>> {
    public int compare(Entry<String, Integer> s1, Entry<String, Integer> s2) {
        if (s1.getValue() > s2.getValue()) return 1;
        else if (s1.getValue() < s2.getValue()) return -1;
        return 0;
    }
}
