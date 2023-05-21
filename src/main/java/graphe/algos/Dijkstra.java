package graphe.algos;

import graphe.core.*;

import java.util.Map.Entry;

import java.util.*;

public class Dijkstra {
    private static String trouve_mini(Map<String, Integer> dist) {
        Optional<Map.Entry<String, Integer>> minEntry = dist.entrySet().stream().min(Map.Entry.comparingByValue());
        return !minEntry.isPresent() ? null : minEntry.get().getKey();
    }

    public static void dijkstra(IGrapheConst g, String source, Map<String, Integer> dist, Map<String, String> prev) {

        ArrayList<String> noeudsDecouverts = new ArrayList<>();

        for (String noeud : g.getSommets()) dist.put(noeud, -1);
        dist.replace(source, 0);

        PriorityQueue<Map.Entry<String,Integer>> pq = new PriorityQueue<>(new SommetComparator());
        pq.add(new AbstractMap.SimpleEntry<>(source, 0));

        while (noeudsDecouverts.size() != g.getSommets().size()) {
            if (pq.isEmpty()) return;
            Map.Entry<String, Integer> u = pq.remove();

            if (noeudsDecouverts.contains(u.getKey())) continue;
            noeudsDecouverts.add(u.getKey());

            for (String noeud : g.getSucc(u.getKey())) {
                if (!noeudsDecouverts.contains(noeud)) {
                    int distanceArc = g.getValuation(u.getKey(), noeud);
                    int distance = distanceArc + dist.get(u.getKey());

                    if (dist.get(noeud) == -1 || distance < dist.get(noeud) ) {
                        dist.replace(noeud, distance);
                        prev.put(noeud, u.getKey());


                    }

                    pq.add(new AbstractMap.SimpleEntry<>(noeud, distance));
                }
            }
        }




        /*ArrayList<String> noeudsDecouverts = new ArrayList<>(g.getSommets());
        ArrayList<Long> temps = new ArrayList<>();
        Map<String, Integer> distNonDecouverts = new HashMap<>();


        for (String noeud : g.getSommets()) dist.put(noeud, -1);

        distNonDecouverts.put(source, 0);
        while (!noeudsDecouverts.isEmpty()) {
            String actuel = trouve_mini(distNonDecouverts);
            if (actuel == null) break;

            noeudsDecouverts.remove(actuel);
            distNonDecouverts.remove(actuel);

            long debut = System.nanoTime();

            for (String noeud : g.getSucc(actuel)) {
                if (noeudsDecouverts.contains(noeud) && (dist.get(noeud) == -1 || dist.get(noeud) > g.getValuation(actuel, noeud) + dist.get(actuel))) {
                    dist.put(noeud, g.getValuation(actuel, noeud) + dist.get(actuel));
                    distNonDecouverts.put(noeud, g.getValuation(actuel, noeud) + dist.get(actuel));
                    prev.put(noeud, actuel);
                }
            }

            long fin = System.nanoTime();
            temps.add(fin - debut);
        }
        // for (String noeud: g.getSommets()) dist.putIfAbsent(noeud, -1);
        long l = 0;
        for (long l1 : temps) {
            l += l1;
        }
        System.out.println(l/1000000);*/
    }
}

class SommetComparator implements Comparator<Entry<String,Integer>> {
    public int compare(Entry<String, Integer> s1, Entry<String, Integer> s2) {
        if (s1.getValue() > s2.getValue())
            return 1;
        else if (s1.getValue() < s2.getValue())
            return -1;
        return 0;
    }
}
