package Djikstra;

import graphe.IGrapheConst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Djikstra {
    public void djikstra(IGrapheConst graphe, String source, String destination){
        String[][] tab = new String [graphe.getSommets().size()][1];

        for(int i = 0; i < tab.length; ++i){
            tab[i][0] = graphe.getSommets().get(i);
            if(tab[i][0] == source) tab[i][1] = "0";
        }

        String position = source;

        while (position != destination){
            List<String> succ = graphe.getSucc(position);

            if(position == source){
                for(int i = 0; i < succ.size(); ++i){
                    if(tab[i][0] == succ.get(i)){
                        tab[i][1] = String.valueOf(graphe.getValuation(position, succ.get(i)));
                    }
                }
                position = minValue(tab);
            }
            else{
                
            }

        }

    }

    public String minValue (String[][] tab){
        Arrays.stream(tab).sorted();
        return tab[0][0];
    }
}
