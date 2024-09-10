package datos;

public record Trayecto(Integer id, String origen, String destino, Double precio, Double duracion) {
	private static int num = 0;
	public static Trayecto ofFormat(String[] formato) {
		Integer id = num;
		String origen = formato[0];
		String destino = formato[1];
		Double precio = parseaPrecio(formato[2]);
		Double duracion = parseaDuracion(formato[3]);
		num++;
		return new Trayecto(id,origen,destino,precio,duracion);
	}
	
	private static Double parseaPrecio(String precio) {
		String p = precio.replace("euros", "");
		return Double.valueOf(p);
	}
	
	private static Double parseaDuracion(String duracion) {
		String d = duracion.replace("min", "");
		return Double.valueOf(d);
	}

	public static Trayecto of(Integer id, String origen, String destino, Double precio, Double duracion) {
		return new Trayecto(id,origen,destino,precio,duracion);
	}
	
	@Override
	public String toString() {
		return precio + " euros ";
	}

}





