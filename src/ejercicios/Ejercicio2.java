package ejercicios;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.SimpleWeightedGraph;

import datos.*;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.*;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.SubGraphView;
import us.lsi.path.GraphPath2;

public class Ejercicio2 {
	
	public static List<Set<Ciudad>> apartadoA(String file, SimpleWeightedGraph<Ciudad, Trayecto> gf, String nombreVista){
		
		var alg = new ConnectivityInspector<>(gf);
		
		List<Set<Ciudad>> ls = alg.connectedSets();
		
		GraphColors.toDot(gf, "resultados/ejercicio2/"+file+nombreVista+".gv",
				v -> v.nombre(),
				e -> "",
				v -> ls.get(0).contains(v)? GraphColors.color(Color.green): GraphColors.color(Color.yellow),
				e -> ls.get(0).contains(parseaCiudad(gf, e.origen())) || ls.get(0).contains(parseaCiudad(gf, e.destino()))?  
						GraphColors.color(Color.green): GraphColors.color(Color.yellow));
		
		System.out.println(file + nombreVista + ".gv generado en "+ "resultados/ejercicio2");
		
		return ls;
	}
	
	public static Set<Ciudad> apartadoB(String file, SimpleWeightedGraph<Ciudad, Trayecto> gf, String nombreVista) {
		var alg = new ConnectivityInspector<>(gf);
		List<Set<Ciudad>> ls = alg.connectedSets();
		Integer ac1 = 0;
		Integer ac2 = 0;
		
		for(int i = 0; i<ls.size();i++){
			for(Ciudad c: ls.get(i)) {
				if(i == 0) {
					ac1 = ac1 + c.puntuacion();
				}else {
					ac2 = ac2 + c.puntuacion();
				}
			}
		}
		if(ac1>ac2) {
			ls.remove(1);
		}else {
			ls.remove(0);
		}
		
		Set<Ciudad> conj =  ls.get(0);
		
		GraphColors.toDot(gf, "resultados/ejercicio2/"+file+nombreVista+".gv",
				v -> v.nombre()+"\n"+v.puntuacion()+" puntos",
				e -> "",
				v -> conj.contains(v)? GraphColors.color(Color.orange): GraphColors.color(Color.black),
				e -> conj.contains(parseaCiudad(gf, e.origen())) || ls.get(0).contains(parseaCiudad(gf, e.destino()))?  
						GraphColors.color(Color.orange): GraphColors.color(Color.black));
		
		
		System.out.println(file + nombreVista + ".gv generado en "+ "resultados/ejercicio2");
		return conj;
	}
	
	public static void  apartadoC(String file, SimpleWeightedGraph<Ciudad, Trayecto> gf, String nombreVista) {
		var alg = new ConnectivityInspector<>(gf);
		List<Set<Ciudad>> ls = alg.connectedSets();
		Set<Ciudad> grupo1 = ls.get(0);
		Set<Ciudad> grupo2 = ls.get(1);
		Graph<Ciudad, Trayecto> g1 = SubGraphView.of(gf, grupo1);
		Graph<Ciudad, Trayecto> g2 = SubGraphView.of(gf, grupo2);
		Double precioGrupo1 = new HeldKarpTSP<Ciudad, Trayecto>().getTour(g1).getWeight();
		Double precioGrupo2 = new HeldKarpTSP<Ciudad, Trayecto>().getTour(g2).getWeight();
		List<Trayecto> trayecto1 = new HeldKarpTSP<Ciudad, Trayecto>().getTour(g1).getEdgeList();
		List<Trayecto> trayecto2 = new HeldKarpTSP<Ciudad, Trayecto>().getTour(g2).getEdgeList();
		if(precioGrupo1.compareTo(precioGrupo2) != 1) {
			System.out.println();
			GraphColors.toDot(gf, "resultados/ejercicio2/"+file+nombreVista+".gv",
					v -> v.nombre(),
					e -> "",
					v -> grupo1.contains(v)? GraphColors.color(Color.blue): GraphColors.color(Color.black),
					e -> trayecto1.contains(e)?  
							GraphColors.color(Color.blue): GraphColors.color(Color.black));
			System.out.println("El grupo de ciudades a visitar que dan lugar al camino cerrado de menor precio: ");
			System.out.println(grupo1+" --> "+ precioGrupo1+" euros");
		}else {
			System.out.println();
			GraphColors.toDot(gf, "resultados/ejercicio2/"+file+nombreVista+".gv",
					v -> v.nombre(),
					e -> e.precio()+" euros",
					v -> grupo2.contains(v)? GraphColors.color(Color.blue): GraphColors.color(Color.black),
					e -> trayecto2.contains(e)?  
							GraphColors.color(Color.blue): GraphColors.color(Color.black));
			System.out.println("El grupo de ciudades a visitar que dan lugar al camino cerrado de menor precio: ");
			System.out.println(grupo2+" --> "+ precioGrupo2+" euros");
		}
		System.out.println(file + nombreVista + ".gv generado en "+ "resultados/ejercicio2");
	}
	
	public static void apartadoD(String file, SimpleWeightedGraph<Ciudad, Trayecto> gf, String nombreVista) {
		var alg = new ConnectivityInspector<>(gf);
		List<Set<Ciudad>> ls = alg.connectedSets();
		Set<Ciudad> grupo1 = ls.get(0);
		Set<Ciudad> grupo2 = ls.get(1);
		Graph<Ciudad, Trayecto> g1 = SubGraphView.of(gf, grupo1);
		Graph<Ciudad, Trayecto> g2 = SubGraphView.of(gf, grupo2);
		List<Ciudad> ls1 = ls.get(0).stream().toList();
		List<Ciudad> ls2 = ls.get(1).stream().toList();
		Map<List<Ciudad>,Double> dic1 = new HashMap<>();
		Map<List<Ciudad>,Double> dic2 = new HashMap<>();
		var alg1 = new DijkstraShortestPath<>(g1);
		var alg2 = new DijkstraShortestPath<>(g2);
		
		for(Ciudad c: grupo1) {
			List<Ciudad> lss = Graphs.successorListOf(g1, c);
			List<Ciudad> lsp = Graphs.predecessorListOf(g1, c);
			for(int i = 0; i< grupo1.size();i++) {
				if(!lss.contains(ls1.get(i)) && !lsp.contains(ls1.get(i)) && !ls1.get(i).equals(c)) {
					List<Ciudad> lsc = new ArrayList<>();
					Double d = alg1.getPathWeight(c, ls1.get(i));
					lsc.add(c);
					lsc.add(ls1.get(i));
					if(!dic1.containsKey(lsc)) {
						dic1.put(lsc, d);
					}
				}
			}
		}
		
		for(Ciudad c: grupo2) {
			List<Ciudad> lss = Graphs.successorListOf(g2, c);
			List<Ciudad> lsp = Graphs.predecessorListOf(g2, c);
			for(int i = 0; i< grupo2.size();i++) {
				if(!lss.contains(ls2.get(i)) && !lsp.contains(ls2.get(i)) && !ls2.get(i).equals(c)) {
					List<Ciudad> lsc = new ArrayList<>();
					Double d = alg2.getPathWeight(c, ls2.get(i));
					lsc.add(c);
					lsc.add(ls2.get(i));
					if(!dic2.containsKey(lsc)) {
						dic2.put(lsc, d);
					}
				}
			}
		}
		List<Ciudad> listamenor1 = dic1.entrySet().stream().min(Comparator.comparing(par -> par.getValue())).orElse(null).getKey();
		Double tiempo1 = dic1.entrySet().stream().map(x -> x.getValue()).min(Comparator.comparingDouble(x -> x)).orElse(null);
		System.out.println("Para el grupo "+grupo1+", las ciudades no conectadas directamente entre las que se puede viajar en menor tiempo son: ");
		System.out.println("Origen: "+listamenor1.get(0)+" y Destino: "+listamenor1.get(1)+"--> Tiempo: "+tiempo1+" minutos");
		List<Trayecto> trayecto1 = new DijkstraShortestPath<Ciudad, Trayecto>(g1).getPath(listamenor1.get(0), listamenor1.get(1)).getEdgeList();
		List<Ciudad> listamenor2 = dic2.entrySet().stream().min(Comparator.comparing(par -> par.getValue())).orElse(null).getKey();
		Double tiempo2 = dic2.entrySet().stream().map(x -> x.getValue()).min(Comparator.comparingDouble(x -> x)).orElse(null);
		System.out.println("Para el grupo "+grupo2+", las ciudades no conectadas directamente entre las que se puede viajar en menor tiempo son: ");
		System.out.println("Origen: "+listamenor2.get(0)+" y Destino: "+listamenor2.get(1)+"--> Tiempo: "+tiempo2+" minutos");
		List<Trayecto> trayecto2 = new DijkstraShortestPath<Ciudad, Trayecto>(g2).getPath(listamenor2.get(0), listamenor2.get(1)).getEdgeList();
		
		GraphColors.toDot(gf, "resultados/ejercicio2/"+file+nombreVista+".gv",
				v -> v.nombre(),
				e -> e.duracion()+" minutos",
				v -> listamenor1.contains(v)? GraphColors.color(Color.green): listamenor2.contains(v)? 
						GraphColors.color(Color.yellow) : GraphColors.color(Color.black),
				e -> trayecto1.contains(e)?  GraphColors.color(Color.green): trayecto2.contains(e)?
						GraphColors.color(Color.yellow): GraphColors.color(Color.black));
		System.out.println(file + nombreVista + ".gv generado en "+ "resultados/ejercicio2");
	}

	private static Object parseaCiudad(SimpleWeightedGraph<Ciudad, Trayecto> gf, String nombre) {
		return gf.vertexSet().stream().filter(x -> x.nombre().equals(nombre)).findFirst().get();
	}
	

}
