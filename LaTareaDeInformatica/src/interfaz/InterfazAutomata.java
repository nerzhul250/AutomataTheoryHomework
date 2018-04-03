package interfaz;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import mundo.AutomataManager;
import mundo.EstadoUsuario;

public class InterfazAutomata extends JFrame {

	private PanelDibAut pda;
	private PanelBotones pb;
	private PanelAcciones pa;
	
	private AutomataManager mundo;
	public InterfazAutomata(){
		setLayout(new BorderLayout());
		
		Object[] pe={"Mealy","Moore"};
		String k=(String)JOptionPane.showInputDialog(this,"Mealy o Moore?","Pregunta",JOptionPane.PLAIN_MESSAGE,null,pe,"Mealy");
		String[] alfentr=JOptionPane.showInputDialog(this,"Alfabeto de entrada?..Separado por comas",JOptionPane.QUESTION_MESSAGE).split(",");
		String[] alfsal=JOptionPane.showInputDialog(this,"Alfabeto de salida?...Separado por comas",JOptionPane.QUESTION_MESSAGE).split(",");
		if(alfsal.length==1 && alfsal[0].isEmpty()){alfsal[0]="*";}
		if(alfentr.length==1 && alfentr[0].isEmpty()){alfentr[0]="*";}
		mundo=new AutomataManager(k,alfentr,alfsal);
		
		setTitle("Equivalencia entre automatas de "+k+" "+mundo.getMealyMoore());
		
		pa=new PanelAcciones(alfsal,this);
		pda=new PanelDibAut(mundo,this);
		pb=new PanelBotones(this);
		
		add(pa,BorderLayout.SOUTH);
		add(pda,BorderLayout.CENTER);
		add(pb,BorderLayout.EAST);
		pack();
	}
	public static void main(String[] args) {
		InterfazAutomata ia=new InterfazAutomata();
		ia.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ia.setVisible(true);
	}
	public void cambiarEstadoUsuario(EstadoUsuario es) {
		mundo.cambiarEstadoUsuario(es);
		pda.repaint();
	}
	public void actualizarSimboloEntrada(String entr) {
		mundo.actSimbEntrada(entr);
		pda.repaint();
	}
	public void actualizarSimboloSalida(String sali) {
		mundo.actSimbSalida(sali);
		pda.repaint();
	}
	public void cambiarAutomata(Integer item) {
		mundo.cambiarAutomata(item);
		pda.repaint();
	}
	public void eventoUsuario(int x, int y) {
		try {
			mundo.eventoUsuario(x, y);	
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
		int estado=mundo.getEstadoSeleccionadoActual();
		if(estado!=-1){
			pa.actualizarBotonesEntrada(mundo.getAutomataActual().getEstados().get(estado).getSimbolosEntradaNoUsados());
		}
		pda.repaint();
	}
	public void detEquivalencia() {
		String A;
		try {
			A = mundo.sonEquivalentes();
			JOptionPane.showMessageDialog(this,A);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,"Te falta algo para completar alguno de los automatas");
		}
	}
}