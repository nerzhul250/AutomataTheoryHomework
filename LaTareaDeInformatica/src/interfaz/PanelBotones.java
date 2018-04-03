package interfaz;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import mundo.EstadoUsuario;

public class PanelBotones extends JPanel implements ActionListener{
	public final static String SELECCION="SELECCION";
	public final static String ESTADONORMAL="ESTADONORMAL";
	public final static String EQUIVALENCIA="EQUIV";
	
	private JButton butSeleccion;
	private JButton butEstadoNor;
	private JButton butDetermEquiv;
	
	private InterfazAutomata principal;
	
	public PanelBotones(InterfazAutomata v){
		principal=v;
		
		setLayout(new GridLayout(3,1));
		
		butSeleccion=new JButton("Accion: Seleccionando");
		butSeleccion.addActionListener(this);
		butSeleccion.setActionCommand(SELECCION);
		
		butEstadoNor=new JButton();
		butEstadoNor.addActionListener(this);
		butEstadoNor.setActionCommand(ESTADONORMAL);
		butEstadoNor.setIcon(new ImageIcon("./icons/estadoNormal.png"));
		
		butDetermEquiv=new JButton("¿Son los dos automatas equivalentes?");
		butDetermEquiv.addActionListener(this);
		butDetermEquiv.setActionCommand(EQUIVALENCIA);
		
		
		add(butSeleccion);
		add(butEstadoNor);
		add(butDetermEquiv);
	}
	public void actionPerformed(ActionEvent e) {
		String command=e.getActionCommand();
		if(command.equals(SELECCION)){
			principal.cambiarEstadoUsuario(EstadoUsuario.SELECCIONANDO);
		}else if(command.equals(ESTADONORMAL)){
			principal.cambiarEstadoUsuario(EstadoUsuario.CREANDOESTADOS);
		}else if(command.equals(EQUIVALENCIA)){
			principal.detEquivalencia();
		}
	}
}
