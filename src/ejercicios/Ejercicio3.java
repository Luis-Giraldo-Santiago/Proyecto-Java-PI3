package ejercicios;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Style;

public class Ejercicio3 {
	
	public static void apartadosAyB(String file, Graph<String,DefaultEdge> gf) {
		
		var alg = new GreedyColoring<>(gf);
		
		GraphColors.toDot(gf, "resultados/ejercicio3/"+file+".gv",
				v -> v.toString(),
				e -> "",
				v -> GraphColors.style(Style.solid),
				e -> GraphColors.style(Style.solid));
		
		Coloring<String> coloring = alg.getColoring();
		var composicion = coloring.getColorClasses();
		
		System.out.println("\nAparatdoA");
		System.out.println("Hacen falta "+coloring.getNumberColors()+" franjas horarias.");
		System.out.println("Actividades para impartirse en paralelo por franja horaria: ");
		for(int i = 0; i < composicion.size(); i++) {
			System.out.println("Franja numero "+(i+1)+": "+ composicion.get(i));
		}
		
		Map<String, Integer> map = coloring.getColors();
		
		GraphColors.toDot(gf, "resultados/ejercicio3/"+file+"B.gv",
				v -> v.toString(),
				e -> "",
				v -> GraphColors.color(map.get(v)),
				e -> GraphColors.style(Style.solid));
		
		System.out.println(file+"C.gv generado en resultados/ejercicio3");
	}

}
