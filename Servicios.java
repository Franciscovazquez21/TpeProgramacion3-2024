package TPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import TPE.algorithms.Backtraking;
import TPE.algorithms.Greedy;
import TPE.taskprocessing.Procesador;
import TPE.taskprocessing.Solucion;
import TPE.taskprocessing.Tarea;
import TPE.utils.CSVReader;
/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
	
	private Map<String,Tarea>hashTarea;//utilizada para servicio 1 (clave "ID", valor "Tarea")
	private List<Tarea>esCritica;//utilizada para servicio 2
	private List<Tarea>noEsCritica;
	private ArrayList<Tarea>tareas;//utilizada en servicio 3,(se recibe ordenada por prioridad en constructor desde CSV)
	private List<Procesador>procesadores;//se reciben en constructor

	/*
     * Complejidad computacional constructor Servicios = O(n).
     */
	public Servicios(String pathProcesadores, String pathTareas){
		CSVReader reader = new CSVReader();
		procesadores=reader.readProcessors(pathProcesadores);
		tareas=reader.readTasks(pathTareas);//devuelve array ordenado por prioridad
		this.hashTarea=new HashMap<String,Tarea>();
		this.esCritica=new LinkedList<>();
		this.noEsCritica=new LinkedList<>();
		this.addTareasHash(tareas);//se asignan las tareas a estructura hash
		this.addCritica(tareas);//funcion que ordena por atributo de clase Tarea(critica->true,false)
	}
	
	/*
	 *Complejidad computacional: O(1) la clave esta asociada a un unico elemento.
     */
	public Tarea servicio1(String ID) {
		if(hashTarea.containsKey(ID)){
			Tarea t= hashTarea.get(ID);
			return new Tarea(t.getId(),t.getNombre(),t.getTiempo(),t.isCritica(),t.getPrioridad());//devuelvo copia 
		}else{
			return null;
		}
	}
		
    /*
	 *Complejidad computacional: O(1) listas ya ordenadas, de rapido acceso.
     */
	public List<Tarea> servicio2(boolean esCritica) {
		if(esCritica){
			return this.esCritica;
		}else{
			return this.noEsCritica;
		}
	}
		
    /*
     * La complejidad temporal de servicio3 es O(n)
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		List<Tarea>result=new LinkedList<>();
		for(Tarea t : tareas){
			if(t.getPrioridad()>=prioridadInferior && t.getPrioridad()<=prioridadSuperior){
				result.add(t);
			}
		}
		return result;
	}

	/*
	 * Para este servicio se exploran todas las posibles asignaciones 
 	 * de tareas a procesadores de manera recursiva. En cada paso, se asigna una tarea a un 
     * procesador y se verifica si la asignación cumple con las restricciones establecidas 
     * (por ejemplo, el límite de procesadores no refrigerados). Si se encuentra una solución 
     * válida, se guarda y se continúa explorando otras posibles soluciones hasta que se 
     * hayan explorado todas las opciones o se haya encontrado una solución óptima.
	 * Para los casos que no exista solucion solucion.getTiempoMax = (-1).
	 */
	public Solucion backtraking (int limiteTprocNoRefrig){//parametro limite procesadores no refrigerados
		Solucion solucionActual = new Solucion(procesadores);
		Solucion solucion = new Solucion(-1);//valor defecto solucion hasta encontrar un estado final/solucion
		List<Tarea>tareasXasignar=new LinkedList<>(tareas);
		Backtraking back = new Backtraking();
		return back.backtraking(limiteTprocNoRefrig,solucionActual,solucion,tareasXasignar);	
	}

	/*Para este servicio, se implento una estrategia que consta de ordenas las tarea de mayor a menor tiempo de ejecucion
	  por lo que, a medida que se asignan tareas, los procesadores a los cuales se pueden asignar deber cumplir con las 
	  restricciones y a su vez deben ser los que menos carga tengan. 
	  Por lo que, a medida que se ingresa una tarea, la distribucion de carga de los procedadores va creciendo de manera 
	  eficiente hasta asignar todas, si alguna tarea no encuentra procesador que pueda asignarla, no existe solucion.
	  Para los casos que no exista solucion, solucion es "null".
	 */
	public Solucion greedy(int tiempoNoRefrig){
		Greedy greedy = new Greedy(procesadores);
		Solucion sol = greedy.greedy(tareas, tiempoNoRefrig);
		return sol;
	}

	/*
	 * Metodo complementario servicio 1: agrega todas las tareas a la estructura Hash por Id. O(n)
	 */
	private void addTareasHash(List<Tarea>listaTareas){
		for (Tarea t : listaTareas) {
			hashTarea.put(t.getId(), t.getCopia()); 
		}
	}
	
	/*
	 ** Metodo servicio 2: agrega todas las tareas a las listas por critica(true/false). O(n)
	 */
	private void addCritica(List<Tarea> listaTareas){
		for (Tarea t : listaTareas) {
			if(t.isCritica()){
				esCritica.add(t.getCopia());
			}else{
				noEsCritica.add(t.getCopia());
			}
		}
	}
	
	
	
}