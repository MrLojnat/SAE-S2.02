package graphe;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class GraphImporter {
	public static int importerReponse(String filePath, List<Integer> chemin) throws FileNotFoundException {
		File file = new File(filePath);
		try (Scanner sc = new Scanner(file)) {
			sc.nextLine(); // nom de l'algo recommandé
			// distance attendue
			int distance = Integer.parseInt(sc.nextLine().trim());
			// chemin
			for (String s : sc.nextLine().split(" "))
				chemin.add(Integer.parseInt(s) - 1);
			return distance;
		}
	}

	public static Arc importer(String filepath, IGraphe g)  {
		File file = new File(filepath);
		return importer(file, g);
	}

	private static Arc importer(File file, IGraphe g)  {
		try (Scanner sc = new Scanner(file)) {
			String line;
			if (!sc.hasNextLine()) {
				throw new IllegalArgumentException("Pas de graphe dans " + file);
			}
			line = sc.nextLine(); // non utilisé
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
