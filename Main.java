package TPE;

public class Main {

	public static void main(String args[]) {
		
		Servicios servicios = new Servicios("./TPE/datasets/Procesadores.csv", "./TPE/datasets/Tareas.csv");
		/*Tarea t= servicios.servicio1("T1");
		System.out.println(t);
		List<Tarea>listaServicio2=servicios.servicio2(false);
		List<Tarea>listaServicio3=servicios.servicio3(29,50);
		for (Tarea tarea : listaServicio3) {
				System.out.println(tarea);
		}*/

		Solucion sol = servicios.backtraking(200);
			System.out.println(sol);
		
	}
}
