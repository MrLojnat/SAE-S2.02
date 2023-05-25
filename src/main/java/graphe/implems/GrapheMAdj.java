package graphe.implems;

import java.util.*;

public class GrapheMAdj extends Graphe{
    private int[][] matrice;
    private Map<String, Integer> indices;

    public GrapheMAdj(String descriptionGraphe){
        this();
        peupler(descriptionGraphe);
    }

    public GrapheMAdj() {
        matrice = new int[0][0];
        indices = new LinkedHashMap<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (indices.containsKey(noeud)) return;

        int[][] tmpMatrice = new int[matrice.length + 1][matrice.length + 1];

        for (int i = 0; i < matrice.length; ++i) {
            System.arraycopy(matrice[i], 0, tmpMatrice[i], 0, matrice.length);
            tmpMatrice[i][matrice.length] = -1;
        }
        Arrays.fill(tmpMatrice[matrice.length], -1);
        matrice = tmpMatrice;

        indices.put(noeud, matrice.length - 1);
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) throws IllegalArgumentException {
        if (valeur < 0) throw new IllegalArgumentException("Valuation négative");
        if (!indices.containsKey(source)) ajouterSommet(source);
        if (!indices.containsKey(destination)) ajouterSommet(destination);

        int ligne = indices.get(source);
        int colonne = indices.get(destination);

        if (matrice[ligne][colonne] != -1) throw new IllegalArgumentException("Déjà existant");
        matrice[ligne][colonne] = valeur;
    }

    @Override
    public void oterSommet(String noeud) {
        if (!indices.containsKey(noeud)) return;
        int[][] tmpMatrice = new int[matrice.length - 1][matrice.length - 1];

        for (int i = 0; i < matrice.length; ++i) {
            for (int j = 0; j < matrice.length; ++j) {
                if (i < indices.get(noeud) && j < indices.get(noeud)) tmpMatrice[i][j] = matrice[i][j];
                else if (i > indices.get(noeud) && j > indices.get(noeud)) tmpMatrice[i-1][j-1] = matrice[i][j];
                else if (i > indices.get(noeud) && j < indices.get(noeud)) tmpMatrice[i-1][j] = matrice[i][j];
                else if (i < indices.get(noeud) && j > indices.get(noeud)) tmpMatrice[i][j-1] = matrice[i][j];
            }
        }

        for (Map.Entry<String, Integer> sommet: indices.entrySet()) {
            int valuation = sommet.getValue();
            if (valuation > indices.get(noeud))
                sommet.setValue(valuation - 1);
        }

        indices.remove(noeud);
        matrice = tmpMatrice;
    }

    @Override
    public void oterArc(String source, String destination) throws IllegalArgumentException {
        if (!contientArc(source, destination)) throw new IllegalArgumentException("L'arc n'existe pas");

        int ligne = indices.get(source);
        int colonne = indices.get(destination);
        matrice[ligne][colonne] = -1;
    }

    @Override
    public List<String> getSommets() { return new ArrayList<>(indices.keySet()); }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<>();
        for (int i = 0; i < matrice.length; ++i) {
            if (matrice[indices.get(sommet)][i] != -1) {
               successeurs.add(indices.keySet().toArray()[i].toString());
            }
        }
        Collections.sort(successeurs);
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
}
