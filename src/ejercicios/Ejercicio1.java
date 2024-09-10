package ejercicios;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import datos.*;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.*;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;


public class Ejercicio1 {
	
	public static List<Persona> apartadoA(String file, Graph<Persona,Parentesco> gf, String nombreVista) {
		List<Persona> listap = new ArrayList<>();
		for(Persona p: gf.vertexSet()) {
			List<Persona> ls = Graphs.predecessorListOf(gf, p);
			if(!ls.isEmpty()) {
				if(comprobar(ls)==true) {
					listap.add(p);
				}
			}
			ls.clear();
		}
		
		GraphColors.toDot(gf, "resultados/ejercicio1/"+file+nombreVista+".gv",
				v -> v.nombre(),
				e -> e.nombre(),
				v -> listap.contains(v)? GraphColors.color(Color.blue): GraphColors.color(Color.black),
				e -> GraphColors.color(Color.black));
		
		System.out.println(file + nombreVista + ".gv generado en "+ "resultados/ejercicio1");
		return listap;
	}
	
	public static List<Persona> apartadoB(String file, SimpleDirectedGraph<Persona,Parentesco> gf, String nombreVista, String nombre){
		Persona p = parseaPersona(gf, nombre);
		List<Persona> ls = Graphs.predecessorListOf(gf, p);
		for(int i = 0; i< ls.size();i++) {
			ls.addAll(Graphs.predecessorListOf(gf, ls.get(i)));
		}

		GraphColors.toDot(gf, "resultados/ejercicio1/"+file+nombreVista+".gv",
				x -> x.nombre(),
				y -> y.nombre(),
				v -> v.nombre().equals(nombre) ? GraphColors.color(Color.red) :
				ls.contains(v) ? GraphColors.color(Color.blue): GraphColors.color(Color.black),
				e -> GraphColors.color(Color.black));
		
		System.out.println(file+nombreVista+".gv generado en " + "resultados/ejercicio1");
		return ls;
	}
	
	public static TipoParentesco apartadoC(SimpleDirectedGraph<Persona,Parentesco> gf, String nom1, String nom2){
		TipoParentesco res = null;
		
		
		
		Persona p1 = parseaPersona(gf, nom1);
		Persona p2 = parseaPersona(gf, nom2);
		List<Persona> lista1=  Graphs.predecessorListOf(gf,p1);
		List<Persona> lista2=  Graphs.predecessorListOf(gf,p2);
		
		for(int i = 0; i< lista1.size();i++) {
			lista1.addAll(Graphs.predecessorListOf(gf, lista1.get(i)));
		}
		for(int i = 0; i< lista2.size();i++) {
			lista2.addAll(Graphs.predecessorListOf(gf, lista2.get(i)));
		}
		
		if(lista1.get(0).equals(lista2.get(0)) || lista1.get(1).equals(lista2.get(1))) {
			res = TipoParentesco.Hermanos;
		}else if(comprobacion(lista1.subList(1, lista1.size()-1),lista2.subList(1, lista2.size()-1))==true) {
			res = TipoParentesco.Primos;
		}else {
			res = TipoParentesco.Otros;
		}
		
		return res;
	} 
	
	public static Set<Persona> apartadoD(String file, SimpleDirectedGraph<Persona,Parentesco> gf, String nombreVista){
		Set<Persona> conjunto = new HashSet<>();
		List<Persona> ls = new ArrayList<>();
		List<Persona> ls1 = new ArrayList<>();
		List<Persona> ls2 = new ArrayList<>();
		for(Persona p : gf.vertexSet()) {
			if(!Graphs.predecessorListOf(gf, p).isEmpty()) {
				ls.add(p);
			}
		}
		for(int i = 0; i < ls.size(); i++) {
			for(int j = 0; j < ls.size(); j++) {
				if(!ls.get(i).equals(ls.get(j))) {
					ls1 = Graphs.predecessorListOf(gf, ls.get(j));
					ls2 = Graphs.predecessorListOf(gf, ls.get(i));
					if (ls1.get(0).equals(ls2.get(0)) && !ls1.get(1).equals(ls2.get(1))) {
						conjunto.add(ls1.get(0));
					}else if (ls1.get(0).equals(ls2.get(1)) && !ls1.get(1).equals(ls2.get(0))) {
						conjunto.add(ls1.get(0));
					}else if (ls1.get(1).equals(ls2.get(0)) && !ls1.get(0).equals(ls2.get(1))) {
						conjunto.add(ls1.get(1));
					}else if (ls1.get(1).equals(ls2.get(1)) && !ls1.get(0).equals(ls2.get(0))) {
						conjunto.add(ls1.get(1));
					}
				}
			}
		}
		GraphColors.toDot(gf, "resultados/ejercicio1/"+file+nombreVista+".gv",
				x -> x.nombre(),
				y -> y.nombre(),
				v -> conjunto.contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
				e -> GraphColors.color(Color.black));
		System.out.println(file+nombreVista+".gv generado en " + "resultados/ejercicio1");
		return conjunto;
	}
	
	public static void apartadoE(String file, Graph<Persona,Parentesco> gf, String nombreVista){
		
		Graph<Persona,Parentesco> g = GraphsReader.newGraph(
				"ficheros/"+file+".txt", 
				Persona::ofFormat,
				Parentesco::ofFormat,
				Graphs2::simpleGraph);
		
		GreedyVCImpl<Persona,Parentesco> vCover = new GreedyVCImpl<>(g);
		Set<Persona> conectados = vCover.getVertexCover();
		
		
		GraphColors.toDot(gf, "resultados/ejercicio1/"+file+nombreVista+".gv",
				x -> x.nombre(),
				y -> y.nombre(),
				v -> conectados.contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
				e -> GraphColors.color(Color.black));
		
		System.out.println(file+nombreVista+".gv generado en " + "resultados/ejercicio1");
	}
	
	private static Boolean comprobar(List<Persona> ls) {
		Boolean res = false;
		if(ls.get(0).ciudad().equals(ls.get(1).ciudad()) && ls.get(0).año().equals(ls.get(1).año())) {
			res = true;
		}
		return res;
	}

	private static Boolean comprobacion(List<Persona> ls1, List<Persona> ls2) {
		Boolean res = false;
		for(Persona p1: ls1) {
			for(Persona p2: ls2) {
				if (p1.equals(p2)) {
					res = true;
					break;
				}
			}
		}
		return res;
	}
	
	private static Persona parseaPersona(Graph<Persona,Parentesco> g, String nombre) {
		return g.vertexSet().stream().filter(x -> x.nombre().equals(nombre)).findFirst().get();
	}

}
