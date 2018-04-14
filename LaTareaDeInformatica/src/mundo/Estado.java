package mundo;

import java.util.ArrayList;

public class Estado {
	
	private String simboloSalida;//Por si es automata de moore
	
	private int posi;
	private int posj;
	private int id;
	private int automata;
	
	private String[] simbolosEntradaUsados;
	private String[] simbolosEntradaNoUsados;
	
	private ArrayList<Conexion> estadosAdyacentes;
	
	public String[] getSimbolosEntradaUsados() {
		return simbolosEntradaUsados;
	}
	public void setSimbolosEntradaUsados(String[] simbolosEntradaUsados) {
		this.simbolosEntradaUsados = simbolosEntradaUsados;
	}
	public Estado(int posj,int posi,int id,int aut,String[] simbolosEntrada){
		automata=aut;
		this.posi=posi;
		this.posj=posj;
		
		this.id=id;
		simbolosEntradaNoUsados=simbolosEntrada;
		simbolosEntradaUsados=new String[simbolosEntrada.length];
		estadosAdyacentes=new ArrayList<Conexion>();
		simboloSalida="";
	}
	public void removerSimbolo(String simboloEntrada) {
		String[] naw1=new String[simbolosEntradaNoUsados.length-1];
		String[] naw2=new String[simbolosEntradaUsados.length+1];
		int k=0;
		for (int i = 0; i < simbolosEntradaNoUsados.length; i++) {
			if(!simbolosEntradaNoUsados[i].equals(simboloEntrada)){
				naw1[k]=simbolosEntradaNoUsados[i];
				k++;
			}
		}
		k=0;
		for (int i = 0; i < simbolosEntradaUsados.length; i++) {
			naw2[i]=simbolosEntradaUsados[i];
			k++;
		}
		naw2[k]=simboloEntrada;
		simbolosEntradaNoUsados=naw1;
		simbolosEntradaUsados=naw2;
	}
	public String getSimboloSalida() {
		return simboloSalida;
	}
	public void setSimboloSalida(String simboloSalida) {
		this.simboloSalida = simboloSalida;
	}
	public int getPosi() {
		return posi;
	}
	public void setPosi(int posi) {
		this.posi = posi;
	}
	public int getPosj() {
		return posj;
	}
	public void setPosj(int posj) {
		this.posj = posj;
	}
	public String[] getSimbolosEntradaNoUsados() {
		return simbolosEntradaNoUsados;
	}
	public void setSimbolosEntradaNoUsados(String[] simbolosEntradaNoUsados) {
		this.simbolosEntradaNoUsados = simbolosEntradaNoUsados;
	}
	public ArrayList<Conexion> getConexiones() {
		return estadosAdyacentes;
	}
	public int getAutomata() {
		return automata;
	}
	public void setAutomata(int automata) {
		this.automata = automata;
	}
	public int getId() {
		return id;
	}
	
	public void darEstadosAlcansados(ArrayList<Integer> sal) {
		sal.add(this.id);
		for (int i = 0; i < estadosAdyacentes.size(); i++) {
			if(!sal.contains(estadosAdyacentes.get(i).getEstadoDes().id)) {
				estadosAdyacentes.get(i).getEstadoDes().darEstadosAlcansados(sal);
			}
		}
	}

	protected Object clone() {
		Estado es=new Estado(posj, posi, id, automata,simbolosEntradaNoUsados);
		es.setSimbolosEntradaUsados(simbolosEntradaUsados);
		return es;
	}
//	@Override
//	public boolean equals(Object obj) {
//		Estado e=(Estado)obj;
//		return e.getId()*e.automata==getId()*automata;
//	}
	public void EliminarAdyacencias() {
		// TODO Auto-generated method stub
		for (int i = 0; i < estadosAdyacentes.size();) {
			
			estadosAdyacentes.remove(i);
			
			//Esto que esta comentado es para eliminar si las conexiones estaban duplicadas
//			int j=0;
//			boolean bandera=false;
//			while(j < estadosAdyacentes.get(i).getEstadoDes().estadosAdyacentes.size()&&!bandera)
//			{
//				if(estadosAdyacentes.get(i).getEstadoDes().estadosAdyacentes.get(j).getEstadoOrig().id==id) {
//					estadosAdyacentes.get(i).getEstadoDes().estadosAdyacentes.remove(j);
//					estadosAdyacentes.remove(i);
//					bandera=true;
//				}
//				j++;
//				
//			}
		}
	}
	
}
