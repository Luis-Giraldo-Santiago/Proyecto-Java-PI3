package test;

import java.util.function.Predicate;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedGraph;

import datos.*;
import ejercicios.Ejercicio1;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.*;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio1 {
	
	public static void main(String[] args) {
		System.out.println("Soluciones para los datos de PI3E1A_DatosEntrada.txt");
		testsEjercicio1A("PI3E1A_DatosEntrada");
		System.out.println("\nSoluciones para los datos de PI3E1B_DatosEntrada.txt");
		testsEjercicio1B("PI3E1B_DatosEntrada");

	}

	private static void testsEjercicio1A(String file) {
		SimpleDirectedGraph<Persona,Parentesco> g = GraphsReader.newGraph(
				"ficheros/"+file+".txt", 
				Persona::ofFormat,
				Parentesco::ofFormat,
				Graphs2::simpleDirectedGraph);
		
		GraphColors.toDot(g,"resultados/ejercicio1/"+file+".gv",
				v -> v.nombre(),
				e -> e.nombre(),
				v -> GraphColors.color(Color.black),
				e -> GraphColors.color(Color.black));
		
		System.out.println(file+".txt \n"+ "Datos entrada: "+g);
		
		System.out.println("\n ApartadoA");
		System.out.println("Personas cuyos padres han nacido en la misma ciudad y en el mismo año: "+Ejercicio1.apartadoA(file, g, "AparatdoA"));
		System.out.println("\n ApartadoB");
		System.out.println("Ancestros de Maria: "+Ejercicio1.apartadoB(file, g, "AparatdoB", "Maria"));
		System.out.println("\n ApartadoC");
		System.out.println("Rafael y Maria son "+Ejercicio1.apartadoC(g, "Rafael", "Sara"));
		System.out.println("Maria y Patricia son "+Ejercicio1.apartadoC(g, "Maria", "Patricia"));
		System.out.println("Carmen y Rafael son "+Ejercicio1.apartadoC(g, "Carmen", "Rafael"));
		System.out.println("\n ApartadoD");
		System.out.println("Personas que tienen hijos/as con distintas personas: "+Ejercicio1.apartadoD(file, g, "AparatdoD"));
		System.out.println("\n ApartadoE");
		Ejercicio1.apartadoE(file, g, "AparatdoE");
	}
	
	private static void testsEjercicio1B(String file) {
		SimpleDirectedGraph<Persona,Parentesco> g = GraphsReader.newGraph(
				"ficheros/"+file+".txt", 
				Persona::ofFormat,
				Parentesco::ofFormat,
				Graphs2::simpleDirectedGraph);
		
		GraphColors.toDot(g,"resultados/ejercicio1/"+file+".gv",
				v -> v.nombre(),
				e -> e.nombre(),
				v -> GraphColors.color(Color.black),
				e -> GraphColors.color(Color.black));
		
		System.out.println(file+".txt \n"+ "Datos entrada: "+g);
		
		System.out.println("\n ApartadoA");
		System.out.println("Personas cuyos padres han nacido en la misma ciudad y en el mismo año: "+Ejercicio1.apartadoA(file, g, "AparatdoA"));
		System.out.println("\n ApartadoB");
		System.out.println("Ancestros de Raquel: "+Ejercicio1.apartadoB(file, g, "AparatdoB", "Raquel"));
		System.out.println("\n ApartadoC");
		System.out.println("Julia y Angela son "+Ejercicio1.apartadoC(g, "Julia", "Angela"));
		System.out.println("Alvaro y Raquel son "+Ejercicio1.apartadoC(g, "Alvaro", "Raquel"));
		System.out.println("Laura y Raquel son "+Ejercicio1.apartadoC(g, "Laura", "Raquel"));
		System.out.println("\n ApartadoD");
		System.out.println("Personas que tienen hijos/as con distintas personas: "+Ejercicio1.apartadoD(file, g, "AparatdoD"));
		System.out.println("\n ApartadoE");
		Ejercicio1.apartadoE(file, g, "AparatdoE");
		
		
	}

}
