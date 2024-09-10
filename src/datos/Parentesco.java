package datos;

public record Parentesco(Integer id, Integer padre, Integer hijo, String nombre) {
	
	private static int num = 0;
	
	public static Parentesco ofFormat(String[] formato){
		Integer id = num;
		Integer padre = Integer.valueOf(formato[0]);
		Integer hijo = Integer.valueOf(formato[1]);
		String nombre = "";
		num++;
		return new Parentesco(id,padre,hijo,nombre);
	}
	
	public static Parentesco of(Integer padreDe, Integer hijoDe) {
		Integer id = num;
		Integer padre = padreDe;
		Integer hijo = hijoDe;
		String nombre = "";
		num++;
		return new Parentesco(id,padre,hijo,nombre);
	}

}




