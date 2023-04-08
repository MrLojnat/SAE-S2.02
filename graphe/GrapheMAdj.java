package graphe;
import java.util.*;

public class GrapheMAdj implements IGraphe{
    private int[][] matrice;
    private Map<String, Integer> indices;

    public GrapheMAdj(String description_graphe){
        matrice = new int[0][0];
        indices = new HashMap<>();
        this.peupler(description_graphe);
    }

    @Override
    public void ajouterSommet(String noeud) {
        int[][] tmpMatrice = new int[matrice.length + 1][matrice.length + 1];

        for (int i = 0; i < matrice.length; ++i) {
            System.arraycopy(matrice[i], 0, tmpMatrice[i], 0, matrice.length);
            tmpMatrice[i][matrice.length] = -1;
        }
        Arrays.fill(tmpMatrice[matrice.length], -1);
        matrice = tmpMatrice;

        indices.put(noeud, matrice.length);
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) throws IllegalArgumentException{
        int ligne = indices.get(source);
        int colonne = indices.get(destination);

        if (matrice[ligne][colonne] == -1) throw new IllegalArgumentException("Déjà existant");
        if (valeur < 0) throw new IllegalArgumentException("Valuation négative");

        matrice[ligne][colonne] = valeur;
    }

    // à faire
    @Override
    public void oterSommet(String noeud) {

    }

    @Override
    public void oterArc(String source, String destination) throws IllegalArgumentException {
        if (!contientArc(source, destination)) throw new IllegalArgumentException("L'arc n'existe pas");

        int ligne = indices.get(source);
        int colonne = indices.get(destination);
        matrice[ligne][colonne] = -1;
    }

    @Override
    public List<String> getSommets() {
        return new ArrayList<>(indices.keySet());
    }

    @Override
    public List<String> getSucc(String sommet) {
        ArrayList<String> successeurs = new ArrayList<>();
        for (int i = 0; i < matrice.length; ++i) {
            if (matrice[indices.get(sommet)][i] != -1) {
                // successeurs.add()
            }
        }

        return successeurs;
    }

    @Override
    public int getValuation(String src, String dest) {
        int ligne = indices.get(src);
        int colonne = indices.get(dest);
        return matrice[ligne][colonne];
    }

    @Override
    public boolean contientSommet(String sommet) {
        return indices.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (contientSommet(src) && contientSommet(dest)) {
            int ligne = indices.get(src);
            int colonne = indices.get(dest);
            return matrice[ligne][colonne] != -1;
        }
        return false;
    }

    // à faire
    public String toString() {
        return null;
    }
}
