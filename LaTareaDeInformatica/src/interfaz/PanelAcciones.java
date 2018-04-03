package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PanelAcciones extends JPanel implements ActionListener{
	
	public static final String ELCOMBO="ELCOMBO";
	
	private JComboBox<Integer> automatas;
	private JButton[] salida;
	private JButton[] entrada;
	
	private InterfazAutomata principal;
	public PanelAcciones(String[] simbolosSalida,InterfazAutomata ventana){
		setLayout(new GridLayout(1,1));
		principal=ventana;
		salida=new JButton[simbolosSalida.length];
		for (int i = 0; i < simbolosSalida.length; i++) {
			salida[i]=new JButton(simbolosSalida[i]);
			salida[i].addActionListener(this);
			salida[i].setActionCommand("S"+simbolosSalida[i]);
		}
		Integer[] vals={1,2};
		automatas=new JComboBox<>(vals);
		automatas.addActionListener(this);
		automatas.setActionCommand(ELCOMBO);
		add(automatas);
	}
	
	public void actualizarBotonesEntrada(String[] simbolosNoUsados){
		entrada=new JButton[simbolosNoUsados.length];
		for (int i = 0; i < entrada.length; i++) {
			entrada[i]=new JButton(simbolosNoUsados[i]);
			entrada[i].addActionListener(this);
			entrada[i].setActionCommand("E"+simbolosNoUsados[i]);
		}
		int max=(entrada.length>salida.length)?entrada.length:salida.length;
		removeAll();
		setLayout(new GridLayout(0,max+1));
		add(new JLabel("Alfabeto de entrada"));
		for (int i = 0; i < entrada.length; i++) {
			add(entrada[i]);
		}
		if(entrada.length!=max){for (int i = 0; i <max-entrada.length ; i++) {add(new JLabel());}}
		add(new JLabel("Alfabeto de salida"));
		for (int i = 0; i < salida.length; i++) {
			add(salida[i]);
		}
		if(salida.length!=max){for (int i = 0; i <max-salida.length ; i++) {add(new JLabel());}}
		add(new JLabel("Construyendo en el automata numero: "));
		add(automatas);
		repaint();
		revalidate();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String a=e.getActionCommand();
		if(a.equals(ELCOMBO)){
			principal.cambiarAutomata((Integer)automatas.getSelectedItem());
		}else{
			if(a.charAt(0)=='E'){
				principal.actualizarSimboloEntrada(a.substring(1));
			}else{
				principal.actualizarSimboloSalida(a.substring(1));
			}
		}
	}
}
