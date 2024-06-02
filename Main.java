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
		//--------------------solucion backtraking---------------------
		System.out.println("Resultado Solucion Backtraking");
		Solucion solucionBacktraking = servicios.backtraking(200);
		if(solucionBacktraking.getTiempo()!=-1){
			System.out.println(solucionBacktraking);
		}else{
			System.out.println("No hay solucion");	
		}
		//--------------------solucion greedy------------------------
		Solucion solucionGreedy= servicios.greedy(200);
		System.out.println("Resultado Solucion Greedy");
		if(solucionGreedy!=null){
			System.out.println(solucionGreedy);
		}else{
			System.out.println("No hay solucion");	
		}
		
	}
}
