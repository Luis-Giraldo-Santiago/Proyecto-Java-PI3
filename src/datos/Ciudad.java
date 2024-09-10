package datos;

public record Ciudad(String nombre, Integer puntuacion){

	public static Ciudad ofFormat(String[] formato) {
		String nombre = formato[0];
		Integer puntuacion = parseaPuntos(formato[1]);
		return new Ciudad(nombre,puntuacion);
	}
	
	private static Integer parseaPuntos(String punto) {
		String puntos = punto.replace("p", "");
		return Integer.valueOf(puntos);
	}

	public static Ciudad of(String nombre, Integer puntuacion) {
		return new Ciudad(nombre,puntuacion);
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}

}



