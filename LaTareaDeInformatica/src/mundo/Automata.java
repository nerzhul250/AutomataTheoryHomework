package mundo;

import java.util.ArrayList;
import java.util.Comparator;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Automata {
	
	private int id;
	private ArrayList<ArrayList<Estado>>estados;
	
	public Automata(int i){
		id=i;
		estados=new ArrayList<ArrayList<Estado>>();
		estados.add(new ArrayList<Estado>());
	}
	public int getSizeTime() {
		return estados.size();
	}
	public int buscarEstadoEnRango(int x, int y) {
		for (int i = 0; i < estados.get(0).size(); i++) {
			int posx=estados.get(0).get(i).getPosj();
			int posy=estados.get(0).get(i).getPosi();
			int dif=(x-posx)*(x-posx)+(y-posy)*(y-posy);
			if(dif<=AutomataManager.estadoRadio*AutomataManager.estadoRadio){
				return i;
			}
		}
		return -1;
	}
	public ArrayList<Estado> getEstados() {
		return estados.get(0);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean EliminarEstadosNoAlcan() {
		boolean elimino=false;
		if(estados.get(0).get(0)!=null){
			ArrayList<Integer> ids=new ArrayList<Integer>();
			estados.get(0).get(0).darEstadosAlcansados(ids);
			int i=1;
			while(i<estados.get(0).size()){
				if(!ids.contains(estados.get(0).get(i).getId())) {
					estados.get(0).remove(i);
					elimino=true;
				}else {
					i++;
				}	
			}
		}
		return elimino;
	}

	public void modificarEstado(Estado es, int iEs, Conexion con) {
		ArrayList<Estado>nuevos=new ArrayList<Estado>();
		for (int i = 0; i <estados.get(0).size(); i++) {
			nuevos.add((Estado)estados.get(0).get(i).clone());
			nuevos.get(i).setSimboloSalida(estados.get(0).get(i).getSimboloSalida());
		}
		for (int i = 0; i <estados.get(0).size(); i++) {
			for (int j = 0; j < estados.get(0).get(i).getConexiones().size(); j++) {
				Conexion c=estados.get(0).get(i).getConexiones().get(j);
				Conexion nueva=new Conexion(c.getSimboloSalida(),c.getSimboloEntrada(),nuevos.get(c.getEstadoOrig().getId()),nuevos.get(c.getEstadoDes().getId()));	
				nueva.setCamino(c.getCamino());
				nuevos.get(i).getConexiones().add(nueva);
			}
		}
		if(es==null) {
			estados.add(0,nuevos);
			con.setEstadoDes(nuevos.get(con.getEstadoDes().getId()));
			con.setEstadoOrg(nuevos.get(con.getEstadoOrg().getId()));
			estados.get(0).get(iEs).getConexiones().add(con);
		}else{
			nuevos.add(es);
			estados.add(0,nuevos);
		}
	}
	public void retroceder() {
		estados.remove(0);
	}
}