package TPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
			return null;//devolvemos null?????
		}
	}
		
    /*
     * Expresar la complejidad temporal del servicio 2.
	 * O(1)
     */
	public List<Tarea> servicio2(boolean esCritica) {
		if(esCritica){
			return new LinkedList<Tarea>(this.esCritica);
		}else{
			return new LinkedList<Tarea>(this.noEsCritica);
		}
	}
		
    /*
     * Expresar la complejidad temporal del servicio 3.
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

	private int buscarIndice(int valor, int inicio , int fin,boolean menor){
		
		if(inicio>fin){
			return -1;
		}

		int medio = (inicio+fin)/2;

		//control de Tareas con igual prioridad, se busca hasta la frontera.
		if(listaTareasOrdenada.get(medio).getPrioridad()==valor){
			if(menor){
				while(medio>0&&listaTareasOrdenada.get(medio-1).getPrioridad()==valor){
					medio--;
				}		
			}else{
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
	 * Metodo servicio 1: agrega todas las tareas a la estructura Hash por Id. O(n)
	 */
	private void addTareasHash(List<Tarea>listaTareas){
		for (Tarea t : listaTareas) {
			hashTarea.put(t.getId(), t);
		}
	}
	
	/*
	 ** Metodo servicio 2: agrega todas las tareas a las listas por critica(true/false). O(n)
	 */
	private void addCritica(List<Tarea> listaTareas){
		for (Tarea t : listaTareas) {
				if(t.isCritica()){
					esCritica.add(t);
				}else{
					noEsCritica.add(t);
				}
			}
		}
	

	public Solucion backtraking (int tiempoX){//tiempoX:valor que se establece para limite procesadores no refrigerados
		int index=0;
		Solucion solucionActual = new Solucion(procesadores,0);
		Solucion mejorSolucion = new Solucion(5000);
		List<Tarea>tareasXasignar=new ArrayList<>(listaTareasOrdenada);
		backtraking(tiempoX,index,solucionActual,mejorSolucion,tareasXasignar);
		
		return mejorSolucion;	
	}


	private void backtraking(int tiempoX, int index, Solucion estadoActual, Solucion mejorSolucion, List<Tarea>tareasXasignar){
		
		if(index==tareasXasignar.size()){//llegue al final de la lista tareasXasignar
			if(estadoActual.getTiempoMaxEjec()< mejorSolucion.getTiempo()){
				mejorSolucion.removeAll();
				mejorSolucion.addAll(estadoActual.getListProcesadores());
				mejorSolucion.setTiempo(estadoActual.getTiempoMaxEjec());
				mejorSolucion.setCantEstados(estadoActual.cantEstados());
				estadoActual.setTiempo(0);
			}
		}else{
			Tarea siguiente= tareasXasignar.get(index);

			Iterator<Procesador> procesadores = estadoActual.getProcesadores();

			while (procesadores.hasNext()) {

				Procesador p = procesadores.next();
				//controlo tiempo ejecucion si tarea va hacia procesador "no refrigerado"
				if(!p.exedeTiempoEjec(tiempoX, siguiente)){
					//poda (procesador que agrega tarea no debe superar el menor registro maximo de ejecucion)
					if(p.getTiempoTotal()+siguiente.getTiempo()<mejorSolucion.getTiempo()){
						//asigno tarea y actualizo cant estados
						p.asignarTarea(siguiente);
						estadoActual.addEstado();
						//actualizo a siguiente tarea
						backtraking(tiempoX, index+1, estadoActual, mejorSolucion, tareasXasignar);
						//deshago actualizaciones
						p.removerTarea(siguiente);
						
						
					}

				}
			}

		}estadoActual.setTiempo(0);


	}
	}



