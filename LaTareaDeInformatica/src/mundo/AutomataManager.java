package mundo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import sun.misc.Queue;

class Pair{
	public int x;
	public int y;
	public Pair ancestor;
	public Pair(int x, int y){
		this.x=x;
		this.y=y;
	}
}
public class AutomataManager {
	public final static double estadoRadio=30;
	public final static int MEALY=0;
	public final static int MOORE=1;
	public final static int ANCHOPAN=1000;
	public final static int ALTOPAN=600;
	
	private int mealyMoore;
	private EstadoUsuario eu;
	private int estadoSeleccionadoActual;
	private String simboloEntradaActual;
	private String simboloSalidaActual;
	
	private String[] conjuntoSimbolosEntrada;
	private String[] conjuntoSimbolosSalida;
	
	private Automata aut1;
	private Automata aut2;

	private Automata automataActual;
	
	public AutomataManager(String mealmoore,String[] conjuntoSimbolosEntrada,String[] conjuntoSimbolosSalida){
		eu=EstadoUsuario.SELECCIONANDO;
		this.conjuntoSimbolosEntrada=conjuntoSimbolosEntrada;
		this.conjuntoSimbolosSalida=conjuntoSimbolosSalida;
		aut1=new Automata(1);
		aut2=new Automata(2);
		automataActual=aut1;
		if(mealmoore.equals("Moore")){
			mealyMoore=MOORE;
		}else{
			mealyMoore=MEALY;
			simboloSalidaActual=conjuntoSimbolosSalida[0];
		}
	}
	/**
	 * Actualiza el simbolo de entrada actual
	 * @param entr
	 */
	public void actSimbEntrada(String entr){
		eu=EstadoUsuario.CONECTANDO;
		simboloEntradaActual=entr;
	}
	/**
	 * Actualiza el simbolo de salida actual
	 * @param sali
	 */
	public void actSimbSalida(String sali) {
		if(eu.equals(EstadoUsuario.SELECCIONANDO) && estadoSeleccionadoActual!=-1 && mealyMoore==MOORE){
			automataActual.getEstados().get(estadoSeleccionadoActual).setSimboloSalida(sali);
		}else{
			if(mealyMoore==MEALY){
				simboloSalidaActual=sali;
			}
		}	
	}
	public void cambiarEstadoUsuario(EstadoUsuario es) {
		eu=es;
	}
	/**
	 * Cambia el automata mostrado por pantalla
	 * @param item
	 */
	public void cambiarAutomata(Integer item) {
		estadoSeleccionadoActual=-1;
		if(item==1){
			automataActual=aut1;
		}else{
			automataActual=aut2;
		}
	}
	/**
	 * Metodo que maneja los eventos de usuario de acuerdo
	 * al estado actual de este.
	 * @param x
	 * @param y
	 * @throws Exception
	 */
	public void eventoUsuario(int x, int y) throws Exception {
		if(eu.equals(EstadoUsuario.CREANDOESTADOS)){
			if(automataActual.buscarEstadoEnRango(x, y)!=-1)throw new Exception("Posicionamiento invalido");
			int q=automataActual.getEstados().size();
			automataActual.modificarEstado(new Estado(x, y, q,automataActual.getId(), conjuntoSimbolosEntrada),-1,null);
		}else if(eu.equals(EstadoUsuario.SELECCIONANDO)){
			int estadoSel=automataActual.buscarEstadoEnRango(x,y);
			if(estadoSel!=-1) {
				estadoSeleccionadoActual=estadoSel;				
			}
		}else if(eu.equals(EstadoUsuario.CONECTANDO)){
			int estadoSel=automataActual.buscarEstadoEnRango(x,y);
			if(simboloEntradaActual!=null && estadoSel!=-1 && estadoSeleccionadoActual!=-1 &&
					automataActual.getEstados().get(estadoSeleccionadoActual).getSimbolosEntradaNoUsados().length!=0){
				Conexion con=null;
				if(mealyMoore==MEALY){
					con=new Conexion(simboloSalidaActual,simboloEntradaActual,
							automataActual.getEstados().get(estadoSeleccionadoActual),
							automataActual.getEstados().get(estadoSel));					
				}else{
					con=new Conexion(simboloEntradaActual,
							automataActual.getEstados().get(estadoSeleccionadoActual),
							automataActual.getEstados().get(estadoSel));
				}
				ArrayList<int[]>cam=encontrarCaminoEntreEstados(estadoSeleccionadoActual,estadoSel);
				if(cam.size()==0){throw new Exception("Retroceda el programa");}
				con.setCamino(cam);
				automataActual.modificarEstado(null,estadoSeleccionadoActual,con);
				automataActual.getEstados().get(estadoSeleccionadoActual).removerSimbolo(simboloEntradaActual);
				simboloEntradaActual=null;
			}
		}
	}
	/////////////////////////////////////////////
	//CAMINOS ENTRE AUTOMATAS EN EL DIBUJO
	////////////////////////////////////////////
	/**
	 * Retorna la serie de casillas que se deben de recorrer en la matriz de dibujo para ir del 
	 * estado origen al estado destino
	 * @param org 
	 * @param des
	 * @return
	 * @throws InterruptedException
	 */
	private ArrayList<int[]> encontrarCaminoEntreEstados(int org, int des) throws InterruptedException {
		int[][] matrix=new int[ALTOPAN][ANCHOPAN];
		/////////////////////////////////////////////
		//Caso cuando el destino es igual al estado origen
		if(org==des){
			int posj=automataActual.getEstados().get(org).getPosj();
			int posi=automataActual.getEstados().get(org).getPosi();
			for (int j = (int) (posj-(estadoRadio)); j <= posj+estadoRadio; j++) {
				int j2=(int) (Math.sqrt(Math.pow(estadoRadio,2)-Math.pow(j-posj,2))+posi);
				for (int k = j-5; k < j+5; k++) {
					for (int k2 = j2-5; k2 < j2+5; k2++) {
						matrix[k2][k]=2;							
					}
				}
			}
			for (int j = (int) (posj-(estadoRadio+5)); j <= posj+5+estadoRadio; j++) {
				int j2=(int) (-Math.sqrt(Math.pow(estadoRadio+5,2)-Math.pow(j-posj,2))+posi);
				for (int k = j-5; k < j+5; k++) {
					for (int k2 = j2-5; k2 < j2+5; k2++) {
						matrix[k2][k]=1;							
					}
				}
			}
			Estado q=automataActual.getEstados().get(org);
			for (int j = 0; j < q.getConexiones().size(); j++) {
				int[] p=q.getConexiones().get(j).getCamino().get(0);
				for (int k = p[0]-10; k < p[0]+10; k++) {
					for (int k2 =p[1]-10; k2 < p[1]+10; k2++) {
						matrix[k2][k]=1;							
					}
				}
			}
			for (int i = (int) (posi-(estadoRadio+5)); i >=posi-(estadoRadio+20) ; i--) {
				matrix[i][posj]=0;
			}
			return bfs(matrix,posj,(int)(posi-(estadoRadio+5)));
		}
		/////////////////////////////////////////////
		//des!=org
		for (int i = 0; i < automataActual.getEstados().size(); i++) {
			if(i!=org && i!=des){
				int posj=automataActual.getEstados().get(i).getPosj();
				int posi=automataActual.getEstados().get(i).getPosi();
				for (int j = (int) (posj-(estadoRadio+10)); j <= posj+10+estadoRadio; j++) {
					int j2=(int) (Math.sqrt(Math.pow(estadoRadio+10,2)-Math.pow(j-posj,2))+posi);
					for (int k = j-5; k < j+5; k++) {
						for (int k2 = j2-5; k2 < j2+5; k2++) {
							matrix[k2][k]=1;							
						}
					}
					j2=(int) (-Math.sqrt(Math.pow(estadoRadio+10,2)-Math.pow(j-posj,2))+posi);
					for (int k = j-5; k < j+5; k++) {
						for (int k2 = j2-5; k2 < j2+5; k2++) {
							matrix[k2][k]=1;							
						}
					}
				}
			}else if(i==des){
				int posj=automataActual.getEstados().get(i).getPosj();
				int posi=automataActual.getEstados().get(i).getPosi();
				for (int j = (int) (posj-(estadoRadio+10)); j <= posj+10+estadoRadio; j++) {
					int j2=(int) (Math.sqrt(Math.pow(estadoRadio+10,2)-Math.pow(j-posj,2))+posi);
					for (int k = j-5; k < j+5; k++) {
						for (int k2 = j2-5; k2 < j2+5; k2++) {
							matrix[k2][k]=2;							
						}
					}
					j2=(int) (-Math.sqrt(Math.pow(estadoRadio+10,2)-Math.pow(j-posj,2))+posi);
					for (int k = j-5; k < j+5; k++) {
						for (int k2 = j2-5; k2 < j2+5; k2++) {
							matrix[k2][k]=2;							
						}
					}
				}
			}
			Estado q=automataActual.getEstados().get(i);
			for (int j = 0; j < q.getConexiones().size(); j++) {
				int[] p=q.getConexiones().get(j).getCamino().get(0);
				for (int k = p[0]-10; k < p[0]+10; k++) {
					for (int k2 =p[1]-10; k2 < p[1]+10; k2++) {
						matrix[k2][k]=1;							
					}
				}
			}
		}
		int x1=automataActual.getEstados().get(org).getPosj();
		int y1=automataActual.getEstados().get(org).getPosi();
		int j=(int) ((int) (x1-(estadoRadio)) + Math.random()*(2*(estadoRadio)));
		int j2=(int) (Math.sqrt(Math.pow(estadoRadio,2)-Math.pow(j-x1,2))+y1);
		return bfs(matrix,j,j2);
	}
	/**
	 * @param matrix la matriz de dibujo
	 * @param j componente x de la posicion donde inicia el bfs
	 * @param j2 componente y de la posicion donde inicia el bfs
	 * @return
	 * @throws InterruptedException
	 */
	private ArrayList<int[]> bfs(int[][] matrix, int j,int j2) throws InterruptedException {
		Queue<Pair> cola=new Queue<Pair>();
		cola.enqueue(new Pair(j,j2));
		matrix[j2][j]=1;
		int[][] posibles={{1,0},{0,-1},{-1,0},{0,1},{1,1},{-1,-1},{1,-1},{-1,1}};
		Pair theNode=null;
		while(!cola.isEmpty() && theNode==null){
			Pair node=cola.dequeue();
			for (int i = 0; i < posibles.length; i++) {
				int x=node.x+posibles[i][0];
				int y=node.y+posibles[i][1];
				if(x>=0 && y>=0 && x<1000 && y<600 && matrix[y][x]!=1){
					if(matrix[y][x]==2){
						Pair naw=new Pair(x,y);
						naw.ancestor=node;
						theNode=naw;
						break;
					}else{
						Pair naw=new Pair(x,y);
						naw.ancestor=node;
						cola.enqueue(naw);
						matrix[y][x]=1;
					}
				}
			}
		}
		ArrayList<int[]>ans=new ArrayList<int[]>();
		while(theNode!=null){
			int[] ke={theNode.x,theNode.y};
			ans.add(ke);
			theNode=theNode.ancestor;
		}
		return ans;
	}
	/////////////////////////////////////////////
	//EQUIVALENCIA ENTRE AUTOMATAS
	/////////////////////////////////////////////
	/**
	 * Retorna un string con valor:
	 * Son equivalentes! si los automatas son equivalentes
	 * No Son equivalentes! si los automatas no son equivalentes
	 * @return
	 * @throws Exception 
	 */
	public String sonEquivalentes() throws Exception {
		if(!verificarAutomatas()){throw new Exception("NO");}
		/////////////
		//QUITAR ESTADOS NO ALCANZABLES
		aut1.EliminarEstadosNoAlcan();
		aut2.EliminarEstadosNoAlcan();
		/////////////
		//INICIALIZANDO CONJUNTOS...
		HashMap<Estado,Integer> P=new HashMap<Estado,Integer>();
		ArrayList<ArrayList<Estado>> M=new ArrayList<ArrayList<Estado>>();
		ArrayList<Estado>suma=new ArrayList<Estado>();
		/////////////////////
		//SUMA DIRECTA
		for (int i = 0; i < aut1.getEstados().size(); i++) {
			suma.add(aut1.getEstados().get(i));
		}
		for (int i = 0; i < aut2.getEstados().size(); i++) {
			suma.add(aut2.getEstados().get(i));
		}
		///////////////
		///////////////
		//Se ordenan las conexiones para facilitar la generacion de las particiones
		for (int i = 0; i < suma.size(); i++) {
			Collections.sort(suma.get(i).getConexiones());
		}
		///////////////
		//PARTICION INICIAL
		//SE GENERA LA PARTICION INICIAL
		while(suma.size()!=0){
			ArrayList<Estado>s1=new ArrayList<Estado>();
			s1.add(suma.get(0));
			M.add(s1);
			P.put(suma.get(0),M.size()-1);
			for (int i = 1; i < suma.size(); i++) {
				if(mealyMoore==MEALY){
					if(estadosEquivalentesMealy(suma.get(0),suma.get(i))){
						s1.add(suma.get(i));
						P.put(suma.get(i),M.size()-1);
						suma.remove(i);
						i--;
					}
				}else if(mealyMoore==MOORE){
					if(estadosEquivalentesMoore(suma.get(0),suma.get(i))){
						s1.add(suma.get(i));
						P.put(suma.get(i),M.size()-1);
						suma.remove(i);
						i--;
					}
				}
			}
			suma.remove(0);
		}
		//////////////////
		//OBTENIENDO P_K+1 DE P_K
		HashMap<Estado,Integer> P1=new HashMap<Estado,Integer>();
		ArrayList<ArrayList<Estado>> M1=new ArrayList<ArrayList<Estado>>();
		do{
			P1=P;
			M1=M;
			P=new HashMap<Estado,Integer>();
			M=new ArrayList<ArrayList<Estado>>();
			for (int i = 0; i < M1.size(); i++) {
				particionar(M1.get(i),M,P,P1);
			}
		}while(M1.size()!=M.size());
		//HASTA QUE PK+1=PK
		//SE DECIDE SI SON EQUIVALENTES SI HAY ESTADOS DE AUT1 Y AUT2 EN CADA UNO DE LOS SUBCONJUNTOS PK
		for (int i = 0; i < M.size(); i++) {
			boolean aut1=false;
			boolean aut2=false;
			for (int j = 0; j < M.get(i).size() && !(aut1 && aut2); j++) {
				if(M.get(i).get(j).getAutomata()==1){
					aut1=true;
				}else{
					aut2=true;
				}
			}
			if(!(aut1 && aut2)){
				return "No son equivalentes!";
			}
		}
		return "Son equivalentes!";
	}
	/**
	 * Subrutina de la determinacion de equivalencia, se encarga de partir
	 * subconjuntos de P_K
	 * @param partir
	 * @param m
	 * @param p
	 * @param p1
	 */
	private void particionar(ArrayList<Estado> partir,
			ArrayList<ArrayList<Estado>> m, HashMap<Estado, Integer> p,
			HashMap<Estado, Integer> p1) {
		while(partir.size()!=0){
			ArrayList<Estado>s1=new ArrayList<Estado>();
			s1.add(partir.get(0));
			m.add(s1);
			p.put(partir.get(0),m.size()-1);
			for (int i = 1; i < partir.size(); i++) {
				boolean safe=true;
				for (int j = 0; j < partir.get(0).getConexiones().size() && safe; j++) {
					Estado q1=partir.get(0).getConexiones().get(j).getEstadoDes();
					Estado q2=partir.get(i).getConexiones().get(j).getEstadoDes();
					if(p1.get(q1)!=p1.get(q2)){
						safe=false;
					}
				}
				if(safe){
					s1.add(partir.get(i));
					p.put(partir.get(i),m.size()-1);
					partir.remove(i);
					i--;
				}
			}
			partir.remove(0);
		}
	}
	/**
	 * Metodo para verificar que los dos automatas esten completos
	 */
	private boolean verificarAutomatas() {
		if(aut1.getEstados().size()==0 || aut2.getEstados().size()==0){return false;}
		if(mealyMoore==MOORE){
			for (int i = 0; i < aut1.getEstados().size(); i++) {
				if(aut1.getEstados().get(i).getSimboloSalida().isEmpty()){
					return false;
				}
			}
			for (int i = 0; i < aut2.getEstados().size(); i++) {
				if(aut2.getEstados().get(i).getSimboloSalida().isEmpty()){
					return false;
				}
			}
		}
		for (int i = 0; i < aut1.getEstados().size(); i++) {
			if(aut1.getEstados().get(i).getConexiones().size()!=conjuntoSimbolosEntrada.length){
				return false;
			}
		}
		for (int i = 0; i < aut2.getEstados().size(); i++) {
			if(aut2.getEstados().get(i).getConexiones().size()!=conjuntoSimbolosEntrada.length){
				return false;
			}
		}
		return true;
	}
	private boolean estadosEquivalentesMealy(Estado q1, Estado q2) {
		boolean safe=true;
		for (int j = 0; j < q1.getConexiones().size() && safe; j++) {
			String s1=q1.getConexiones().get(j).getSimboloSalida();
			String s2=q2.getConexiones().get(j).getSimboloSalida();
			if(!s1.equals(s2)){
				safe=false;
			}
		}
		return safe;
	}
	private boolean estadosEquivalentesMoore(Estado q1, Estado q2) {
		return q1.getSimboloSalida().equals(q2.getSimboloSalida());
	}
	///////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////
	public EstadoUsuario getEu() {
		return eu;
	}
	public Automata getAutomataActual() {
		return automataActual;
	}
	public int getEstadoSeleccionadoActual() {
		return estadoSeleccionadoActual;
	}
	public String getSimboloEntradaActual() {
		return simboloEntradaActual;
	}
	public String getSimboloSalidaActual() {
		return simboloSalidaActual;
	}
	public int getMealyMoore() {
		return mealyMoore;
	}
	/**
	 * Metodo que elimina estados no alcanzables
	 * @return
	 * @throws Exception
	 */
	public String DeleteEstadosNoAlcanzables() throws Exception {
		// TODO Auto-generated method stub
		if(automataActual!=null&&automataActual.getEstados()!=null&&automataActual.getEstados().size()!=0) {
			boolean resultado=automataActual.EliminarEstadosNoAlcan();
			if(resultado)
				return ("Se Han Eliminado Los Estados No Alcanzables");
			else
				return ("No Se Ha podido Eliminar Ningun Estado");
		}else {
			throw new Exception();
		}
		
	}
	public void retroceso() {
		if(automataActual.getSizeTime()>1) {
			estadoSeleccionadoActual=-1;
			automataActual.retroceder();
		}
	}
}