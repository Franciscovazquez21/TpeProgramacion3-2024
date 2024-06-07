package TPE;

import java.util.List;

import TPE.taskprocessing.Solucion;
import TPE.taskprocessing.Tarea;

public class Main {

	public static void main(String args[]) {
		
		Servicios servicios = new Servicios("./TPE/datasets/Procesadores.csv", "./TPE/datasets/Tareas.csv");
		//---------------------servicio1---------------------------------
		System.out.println("\n-----------------------------Servicio 1----------------------------------------");
		
		String id="T1";
		Tarea t= servicios.servicio1(id);
		if(t!=null){
			System.out.println(t);
		}else{
			System.out.println("No existe tarea con id: "+id);
		}
		//--------------------servicio2---------------------------------
		System.out.println("\n-----------------------------Servicio 2----------------------------------------");
		boolean critica=true;
		List<Tarea>listaServicio2=servicios.servicio2(critica);
		if(!listaServicio2.isEmpty()){
			for (Tarea tarea : listaServicio2) {
				System.out.println(tarea);
			}
		}else{
			System.out.println("no existen tareas que cumplan con tipo tarea: " +critica);
		}
		//--------------------servicio3----------------------------------
		System.out.println("\n-----------------------------Servicio 3----------------------------------------");
		
		List<Tarea>listaServicio3=servicios.servicio3(22,70);
		if(listaServicio3!=null){
			for (Tarea tarea : listaServicio3) {
					System.out.println(tarea);
			}
			System.out.println("");
		}else{
			System.out.println("el rango elegido es incorrecto o inexistente");
		}
		
		//--------------------solucion backtraking---------------------
		System.out.println("\n------------------------Resultado Solucion Bactraking------------------------------");

		Solucion solucionBacktraking = servicios.backtraking(200);
		if(solucionBacktraking.getTiempo()!=(-1)){
			System.out.println(solucionBacktraking);
		}else{
			System.out.println("\nNo hay solucion");	
		}
		//--------------------solucion greedy------------------------
		Solucion solucionGreedy= servicios.greedy(200);
		
		System.out.println("\n------------------------Resultado Solucion Greedy----------------------------------");
		if(solucionGreedy!=null){
			System.out.println(solucionGreedy);
		}else{
			System.out.println("\nNo hay solucion");	
		}
		
	}
}
