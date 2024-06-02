package TPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import TPE.utils.CSVReader;
/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
	
	//estructura que abastece al servicio 1 (clave "ID", valor "Tarea")
	private Map<String,Tarea>hashTarea;
	//estructuras que abastecen al servicio 2, separadas por boolean "Critica(true, false)".
	private List<Tarea>esCritica;
	private List<Tarea>noEsCritica;
	//estructura que abastece al servicio 3, se asigna en constructor(se recibe ordenada en constructor)
	private List<Tarea>listaTareasOrdenada;
	//estructura procesadores
	private List<Procesador>procesadores;

	public List<Procesador> get(){
		return procesadores;
	}

	/*
     * Complejidad computacional constructor Servicios = O(n).
     */
	public Servicios(String pathProcesadores, String pathTareas){
		CSVReader reader = new CSVReader();
		procesadores=reader.readProcessors(pathProcesadores);
		listaTareasOrdenada=reader.readTasks(pathTareas);//devuelve array ordenado por prioridad
		this.hashTarea=new HashMap<String,Tarea>();
		this.esCritica=new LinkedList<>();
		this.noEsCritica=new LinkedList<>();
		this.addTareasHash(listaTareasOrdenada);
		this.addCritica(listaTareasOrdenada);//llama a funcion que ordena por atributo de clase Tarea(critica->true,false)
	}
	
	/*
     * Expresar la complejidad temporal del servicio 1.
	 * O(1)se utiliza HashMap, por lo que la clave esta asociada a un unico elemento.
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
     * Expresar la complejidad temporal del servicio 2.
	 * O(1)-->>preguntar si se toma correcto enviar sin copia a fines del enunciado
     */
	public List<Tarea> servicio2(boolean esCritica) {
		if(esCritica){
			return new LinkedList<Tarea>(this.esCritica);
		}else{
			return new LinkedList<Tarea>(this.noEsCritica);
		}
	}
		
    /*
     * La complejidad tenporal de servicio3 es O(log n) ya que se realiza una busqueda binaria
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		//control parametros incorrectos
		if(prioridadInferior>prioridadSuperior){
			return List.of();
		}

		int indiceMenor=buscarIndice(prioridadInferior,0,listaTareasOrdenada.size()-1,true);
		int indiceMayor=buscarIndice(prioridadSuperior,0,listaTareasOrdenada.size()-1,false);
		
		if (indiceMenor == -1 || indiceMayor == -1) {
            return List.of(); // Devuelve una lista vacía si no hay tareas en el rango
        }
		return listaTareasOrdenada.subList(indiceMenor, indiceMayor+1);
	}


	/*
     * La complejidad temporal de buscarIndice es O(log n)es una busqueda binaria
	 * como en el ejercicio se pueden encontrar prioridades repetidas, se implemento una variable 
	 * booleana "menor", que evalua si el indice es el minimo o el maximo.
     */
	private int buscarIndice(int valor, int inicio , int fin,boolean menor){
		if(inicio>fin){
			return -1;
		}
		int medio = (inicio+fin)/2;
		//control de Tareas con igual prioridad, se busca hasta la frontera.
		if(listaTareasOrdenada.get(medio).getPrioridad()==valor){
			if(menor){//busqueda prioridad indiceMayor
				while(medio>0&&listaTareasOrdenada.get(medio-1).getPrioridad()==valor){
					medio--;
				}		
			}else{//busqueda prioridad indiceMenor
				while(medio<listaTareasOrdenada.size()-1&&listaTareasOrdenada.get(medio+1).getPrioridad()==valor){
					medio++;
				}
			}
			return medio;
		}
		else if(listaTareasOrdenada.get(medio).getPrioridad()<valor){
			return buscarIndice(valor, medio+1, fin,menor);
		}
		else{
			return buscarIndice(valor, inicio, medio-1,menor);
		}
	}


	/*
	 * Metodo complementario servicio 1: agrega todas las tareas a la estructura Hash por Id. O(n)
	 */
	private void addTareasHash(List<Tarea>listaTareas){
		for (Tarea t : listaTareas) {
			hashTarea.put(t.getId(), t.getCopia());//consultar aca!!!! lo dejo con copia o no??? 
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
	
	/*
	 * 
	 */
	public Solucion backtraking (int limiteTprocNoRefrig){//limiteTprocNoRefrig:valor que se establece para limite procesadores no refrigerados
		Solucion solucionActual = new Solucion(procesadores);
		Solucion solucion = new Solucion(-1);
		List<Tarea>tareasXasignar=new ArrayList<>(listaTareasOrdenada);
		//backtraking(limiteTprocNoRefrig,solucionActual,solucion,tareasXasignar);
		Backtraking back = new Backtraking();
		return back.backtraking(limiteTprocNoRefrig,solucionActual,solucion,tareasXasignar);	
	}

	
	public Solucion greedy(int tiempoNoRefrig){
		Greedy greedy = new Greedy(procesadores);
		Solucion sol = greedy.greedy(listaTareasOrdenada, tiempoNoRefrig);
		return sol;
	}

	
	/*private void backtraking(int limiteTprocNoRefrig, Solucion estadoActual, Solucion solucion, List<Tarea>tareasXasignar){
		Solucion.incrementarEstado();
		if(tareasXasignar.isEmpty()){//no hay mas tareas por asignar
			if(estadoActual.esSolucion(solucion)){
			operarSolucion(estadoActual,solucion);
			}
		} else{
			Tarea siguiente= tareasXasignar.get(0);
			Iterator<Procesador> procesadores = estadoActual.getProcesadores();
			
			while (procesadores.hasNext()) {
				Procesador p = procesadores.next();
				if(cumpleRestriccion(p, limiteTprocNoRefrig, siguiente)){
					asignarTarea(p,siguiente,tareasXasignar);
					if(!poda(p, siguiente, solucion)){
						backtraking(limiteTprocNoRefrig, estadoActual, solucion, tareasXasignar);
					}
					desasignarTarea(p,siguiente,tareasXasignar);
				}
			}
		}
	}

	private void operarSolucion(Solucion nuevaSolucion, Solucion solucion){
		solucion.removeAll();
		solucion.addAll(nuevaSolucion.getCopiaProcesadores());
		solucion.setTiempo(nuevaSolucion.getTiempoMaxEjec());
		solucion.setTiempo(nuevaSolucion.getTiempoMaxEjec());
	}

	private boolean cumpleRestriccion(Procesador p, int limiteTprocNoRefrig, Tarea t){
		return (!p.exedeTiempoEjec(limiteTprocNoRefrig, t)&&p.getTareasCriticas()<2);
	}

	private boolean poda(Procesador p, Tarea t, Solucion s){
		if(s.getTiempo()==-1){
			return false;
		}
		return (p.getTiempoTotal()+t.getTiempo())>s.getTiempo();
	}

	private void asignarTarea(Procesador p, Tarea t,List<Tarea>tareas){
		p.asignarTarea(t);
		tareas.remove(t);
	}

	private void desasignarTarea(Procesador p, Tarea t,List<Tarea>tareas){
		p.removerTarea(t);
		tareas.add(t);
	}*/


}



