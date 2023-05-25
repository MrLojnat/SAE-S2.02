package graphe.core;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface IGrapheConst {
	List<String> getSommets(); // pas forcement triee
	List<String> getSucc(String sommet); // pas forcement triee
	int getValuation(String src, String dest); // les sommets doivent exister, -1 si pas d'arc
	boolean contientSommet(String sommet);
	boolean contientArc(String src, String dest);
	
	default String toAString() {
	    List<String> sommetsTries = new ArrayList<>(getSommets());
	    Collections.sort(sommetsTries);

	    List<String> descriptionsArcs = new ArrayList<>();

	    for (String sommet : sommetsTries) {
	        List<String> successeurs = getSucc(sommet);

	        if (successeurs.isEmpty()) {
	            descriptionsArcs.add(sommet + ":");
	        } else {
	            List<String> successeursTries = new ArrayList<>(successeurs);
	            Collections.sort(successeursTries);

	            for (String successeur : successeursTries) {
	                int poids = getValuation(sommet, successeur);
	                descriptionsArcs.add(sommet + "-" + successeur + "(" + poids + ")");
	            }
	        }
	    }

	    return String.join(", ", descriptionsArcs);
	}
}
