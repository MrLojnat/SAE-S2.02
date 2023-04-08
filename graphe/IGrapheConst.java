package graphe;


import java.util.List;

public interface IGrapheConst {
	List<String> getSommets(); // pas forcement triee
	List<String> getSucc(String sommet); // pas forcement triee
	int getValuation(String src, String dest); // les sommets doivent exister, -1 si pas d'arc
	boolean contientSommet(String sommet);
	boolean contientArc(String src, String dest);
}
