package test;

import org.jgrapht.graph.SimpleWeightedGraph;

import datos.*;
import ejercicios.Ejercicio2;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio2 {

	public static void main(String[] args) {
		testEjercicio2("PI3E2_DatosEntrada");

	}

	private static void testEjercicio2(String file) {
		SimpleWeightedGraph<Ciudad,Trayecto> g1 = GraphsReader.newGraph(
				"ficheros/"+file+".txt", 
				Ciudad::ofFormat,
				Trayecto::ofFormat,
				Graphs2::simpleWeightedGraph,
				Trayecto::precio);
		SimpleWeightedGraph<Ciudad,Trayecto> g2 = GraphsReader.newGraph(
				"ficheros/"+file+".txt", 
				Ciudad::ofFormat,
				Trayecto::ofFormat,
				Graphs2::simpleWeightedGraph,
				Trayecto::duracion);
		GraphColors.toDot(g1,"resultados/ejercicio2/"+file+".gv",
				v -> v.nombre()+"\n"+v.puntuacion()+" puntos",
				e -> e.precio()+" euros\n"+e.duracion()+" minutos",
				v -> GraphColors.color(Color.black),
				e -> GraphColors.color(Color.black));
		
		System.out.println(file+".txt \n"+ "Datos entrada: "+g1);
		System.out.println("\nAparatdoA");
		System.out.println("Hay "+ Ejercicio2.apartadoA(file, g1, "AparatdoA").size()+" grupos de ciudades");
		System.out.println("Grupo numero 1: "+ Ejercicio2.apartadoA(file, g1, "AparatdoA").get(0));
		System.out.println("Grupo numero 2: "+ Ejercicio2.apartadoA(file, g1, "AparatdoA").get(1));
		System.out.println("\nAparatdoB");
		System.out.println("Grupo de ciudades que maximiza la suma de puntaciones: "+
							Ejercicio2.apartadoB(file, g1, "AparatdoB"));
		System.out.println("\nAparatdoC");
		Ejercicio2.apartadoC(file, g1, "AparatdoC");
		System.out.println("\nAparatdoD");
		Ejercicio2.apartadoD(file, g2, "AparatdoD");
	}

}
