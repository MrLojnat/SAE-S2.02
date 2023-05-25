package graphe.ihm;

import graphe.core.Arc;
import graphe.core.IGraphe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GraphDirectoryImporter implements Iterable<CheminATrouver>{
    private List<Path> fichiersGraphes;
    private List<Path> fichiersReponses;
    private boolean traceOn;
    private IGraphe g;

    public GraphDirectoryImporter(String graphesRep, String reponsesRep, boolean traceOn, IGraphe g) {
        try {
            fichiersGraphes = Files.list(Paths.get(graphesRep))
                    .filter(Files::isRegularFile).sorted()
                    .collect(Collectors.toList());
            fichiersReponses = Files.list(Paths.get(reponsesRep))
                    .filter(Files::isRegularFile).sorted()
                    .collect(Collectors.toList());
        }
        catch(IOException e) {
            throw new RuntimeException("Erreur I/O sur "+graphesRep + " ou "+ "reponsesRep");
        }
        this.traceOn = traceOn;
        this.g = g;
    }

    public Iterator<CheminATrouver> iterator() {
        return new CustomIterator(this, g);
    }

    public static class CustomIterator implements Iterator<CheminATrouver> {
        Iterator<Path> iteratorG;
        Iterator<Path> iteratorR;
        boolean traceOn;
        private IGraphe g;

        public CustomIterator(GraphDirectoryImporter importer, IGraphe g) {
            this.iteratorG = importer.fichiersGraphes.iterator();
            this.iteratorR = importer.fichiersReponses.iterator();
            this.traceOn = importer.traceOn;
            this.g = g;
        }

        @Override
        public boolean hasNext() {
            if (iteratorG.hasNext() && !iteratorR.hasNext())
                throw new RuntimeException("Le fichier de graphe "+iteratorG.next()
                        + " n'a pas de fichier de reponse associe");

            if (iteratorR.hasNext() && !iteratorG.hasNext())
                    throw new RuntimeException("Le fichier de reponse " + iteratorR.next()
                            + " n'a pas de fichier de graphe associe");
            return iteratorG.hasNext();
        }

        @Override
        public CheminATrouver next() {
            Path gPath = iteratorG.next();
            Path rPath = iteratorR.next();
            g = GraphImporter.spawn(g);
            Arc arc = GraphImporter.importer(gPath.toFile(), g);
            List<Integer> cheminPossible = new ArrayList<>();
            int distance_attendue = -1;
            try {
                distance_attendue = GraphImporter.importerReponse(rPath.toString(), cheminPossible);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de l'acces au fichier de reponse : " + rPath.toString());
            }
            if (traceOn) {
                System.out.println("\ngraphe: " + gPath.getFileName());
                System.out.println("On demande un chemin de " + arc.getSource() + " vers " + arc.getDestination());
                System.out.println("Graphe de  " + g.getSommets().size() + " sommets");

                System.out.println("reponse: " + rPath.getFileName());
                if (distance_attendue >= 0) {
                    System.out.println("distance attendue : " + distance_attendue);
                    System.out.println("chemin possible : " + cheminPossible);
                } else System.out.println("Aucun chemin attendu");
            }
            return new CheminATrouver(gPath.toString(), rPath.toString(), g,
                                        arc, distance_attendue, cheminPossible);
        }
    }
}
