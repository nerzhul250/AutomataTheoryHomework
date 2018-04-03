package mundo;

import java.util.ArrayList;

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
}
