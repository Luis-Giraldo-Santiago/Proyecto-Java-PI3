package datos;

public record Persona(Integer id, String nombre, Integer a�o, String ciudad) {
	
	public static Persona ofFormat(String[] formato) {
		Integer id = Integer.valueOf(formato[0]);
		String nombre = formato[1];
		Integer a�o = Integer.valueOf(formato[2]);
		String ciudad = formato[3];
		return new Persona(id,nombre,a�o,ciudad);
	}
	
	public static Persona of(Integer id, String nombre, Integer a�o, String ciudad) {
		return new Persona(id,nombre,a�o,ciudad);
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}





