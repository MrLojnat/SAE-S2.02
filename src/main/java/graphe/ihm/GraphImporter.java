package graphe.ihm;


import graphe.core.Arc;
import graphe.core.IGraphe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GraphImporter {
	
	public static int importerReponse(String nomFichier, List<Integer> listeEntiers) throws IOException {
        int distance = -1;

        try (Scanner scanner = new Scanner(new File(nomFichier))) {
            if (!scanner.hasNextLine()) {
                throw new IOException("Le fichier est vide.");
            }
            String premiereLigne = scanner.nextLine();

            if (premiereLigne.startsWith("pas de chemin entre")) {
                String[] ligneDecoupee = premiereLigne.split(" ");
                if (ligneDecoupee.length != 7) {
                    throw new IOException("Format incorrect pour la premiere ligne.");
                }
                listeEntiers.add(Integer.parseInt(ligneDecoupee[4]));
                listeEntiers.add(Integer.parseInt(ligneDecoupee[6]));
            } else {
                if (!scanner.hasNextLine()) {
                    throw new IOException("Deuxieme ligne non trouvee.");
                }
                distance = scanner.nextInt();
                scanner.nextLine(); 
                if (!scanner.hasNextLine()) {
                    throw new IOException("Troisieme ligne non trouvee.");
                }
                String troisiemeLigne = scanner.nextLine();
                List<Integer> tempList = Arrays.stream(troisiemeLigne.split(" "))
                                               .map(Integer::parseInt)
                                               .collect(Collectors.toList());
                listeEntiers.addAll(tempList);
            }
        } catch (NumberFormatException e) {
            throw new IOException("Le fichier contient des données mal formatees. "+e.toString(), e);
        }

        return distance;
    }
	
	public static Arc importer(String filePath, IGraphe g)  {
		File file = new File(filePath);
		return importer(file, g);
	}

	// retourne une instance vide de la classe de g
	// utile quand on importe plusieurs fichiers
	// pour repartir d'un graphe vide pour chaque fichier
	public static IGraphe spawn(IGraphe g) {
		try {
			return g.getClass().getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Impossible de spawn un graphe de type "+ g.getClass().getSimpleName());
		}
	}

	public static Arc importer(File file, IGraphe g)  {
		try (Scanner sc = new Scanner(file)) {
			String line;
			if (!sc.hasNextLine()) {
				throw new IllegalArgumentException("Pas de graphe dans " + file);
			}
			line = sc.nextLine(); // nom d'algorithme non utilise
			Arc a = null;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				a = parse(line);
				if (sc.hasNextLine())
					g.ajouterArc(a.getSource(), a.getDestination(), a.getValuation());
			}
			return a;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Pas de graphe dans " + file);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("valuation incorrecte dans " + file);
		}
	}

	public static Arc parse(String string) {
		String[] parts = string.split(" ");
		String source;
		int valuation;
		String destination;
		if (parts.length == 2) { // derniere ligne 
			String[] newParts = new String[3];
			newParts[0] = parts[0];
			newParts[1] = "00";
			newParts[2] = parts[1];
			parts = newParts;
		}
			
		try {
			source = parts[0];
			valuation = Integer.valueOf(parts[1]);
			destination = parts[2];
		} catch (Exception e) {
			throw new IllegalArgumentException(string + " n'est pas un arc");
		}
		return new Arc(source, destination, valuation);
	}
}
