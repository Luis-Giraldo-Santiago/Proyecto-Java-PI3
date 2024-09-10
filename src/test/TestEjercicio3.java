package test;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.EdgeReversedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import ejercicios.Ejercicio3;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio3 {

	public static void main(String[] args) {
		testEjercicio3("PI3E3A_DatosEntrada");
		testEjercicio3("PI3E3B_DatosEntrada");

	}

	private static void testEjercicio3(String file) {
		Graph<String, DefaultEdge> g = Graphs2.simpleGraph(String::new, DefaultEdge::new, false);
		
		Files2.streamFromFile("ficheros/"+file+".txt").forEach(linea -> {
			String[] v = linea.split(":");
			String[] trozos = v[1].split(",");
			for(int i = 0; i<trozos.length; i++) {
				g.addVertex(trozos[i].trim());
			}
			for(String s :trozos ) {
				for(int i = 0; i<trozos.length; i++) {
					if(!trozos[i].trim().equals(s.trim())) {
						g.addEdge(s.trim(), trozos[i].trim());
					}
				}
			}
		});
		
		Ejercicio3.apartadosAyB(file, g);
	}
}












