package mundo;

import java.util.ArrayList;
import java.util.Comparator;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Automata {
	
	private int id;
	private ArrayList<Estado>estados;
	
	public Automata(int i){
		id=i;
		estados=new ArrayList<Estado>();
	}

	public int buscarEstadoEnRango(int x, int y) {
		for (int i = 0; i < estados.size(); i++) {
			int posx=estados.get(i).getPosj();
			int posy=estados.get(i).getPosi();
			int dif=(x-posx)*(x-posx)+(y-posy)*(y-posy);
			if(dif<=AutomataManager.estadoRadio*AutomataManager.estadoRadio){
				return i;
			}
		}
		return -1;
	}
	
	public ArrayList<Estado> getEstados() {
		return estados;
	}
	public void setEstados(ArrayList<Estado> estados) {
		this.estados = estados;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public boolean EliminarEstadosNoAlcan() {
		boolean elimino=false;
		if(estados.get(0)!=null){
			ArrayList<Integer> ids=estados.get(0).darEstadosAlcansados(new ArrayList<Integer>());
			ids.sort(new idsComparate());
			int i=1;
			while(i<estados.size())
			{
				if(!ids.contains(estados.get(i).getId())) {
					estados.get(i).EliminarAdyacencias();
					estados.remove(i);
					elimino=true;
					
				}else {
					i++;
				}	
			}
		}
		return elimino;
	}
}
