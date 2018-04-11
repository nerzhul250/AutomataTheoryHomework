package mundo;

import java.util.ArrayList;

public class Conexion implements Comparable<Conexion>{
	private String simboloSalida;//Por si es automata de mealy
	private String simboloEntrada;
	//Origen
	private Estado estadoOrg;
	//Destino
	private Estado estadoDes;
	
	private ArrayList<int[]> camino;
	
	public Conexion(String simboloSalida,String simboloEntrada,Estado estor,Estado des){
		camino=new ArrayList<int[]>();
		this.simboloEntrada=simboloEntrada;
		this.simboloSalida=simboloSalida;
		estadoOrg=estor;
		estadoDes=des;
	}
	public Conexion(String simboloEntrada,Estado estor,Estado des){
		camino=new ArrayList<int[]>();
		estadoOrg=estor;
		estadoDes=des;
		this.simboloEntrada=simboloEntrada;
	}
	public Estado getEstadoOrig() {
		return estadoOrg;
	}
	public String getSimboloSalida() {
		return simboloSalida;
	}
	public void setSimboloSalida(String simboloSalida) {
		this.simboloSalida = simboloSalida;
	}
	public String getSimboloEntrada() {
		return simboloEntrada;
	}
	public void setSimboloEntrada(String simboloEntrada) {
		this.simboloEntrada = simboloEntrada;
	}
	public ArrayList<int[]> getCamino() {
		return camino;
	}
	public void setCamino(ArrayList<int[]> camino) {
		this.camino = camino;
	}
	public Estado getEstadoDes() {
		return estadoDes;
	}
	@Override
	public int compareTo(Conexion o) {
		return simboloEntrada.compareTo(o.getSimboloEntrada());
	}
}
