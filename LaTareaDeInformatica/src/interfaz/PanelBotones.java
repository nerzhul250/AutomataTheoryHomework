package interfaz;

import java.awt.Dimension;
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
	public final static String ELIMINARNOALCAZABLES="DELETENOALCA";
	public final static String RETROCESO="ATRAS";
	
	private JButton butSeleccion;
	private JButton butEstadoNor;
	private JButton butDetermEquiv;
	private JButton butDelteNoAlc;
	private JButton butRetroceso;
	
	private InterfazAutomata principal;
	
	public PanelBotones(InterfazAutomata v){
		setPreferredSize(new Dimension(140,0));
		principal=v;
		
		setLayout(new GridLayout(5,1));
		
		
		
		butRetroceso=new JButton("RETROCEDER");
		butRetroceso.addActionListener(this);
		butRetroceso.setActionCommand(RETROCESO);
		
		butSeleccion=new JButton("<html>Accion:<br>Seleccionando</html>");
		butSeleccion.addActionListener(this);
		butSeleccion.setActionCommand(SELECCION);
		
		butEstadoNor=new JButton();
		butEstadoNor.addActionListener(this);
		butEstadoNor.setActionCommand(ESTADONORMAL);
		butEstadoNor.setIcon(new ImageIcon("./icons/estadoNormal.png"));
		
		butDetermEquiv=new JButton("<html>¿Son los dos<br>automatas equivalentes?</html>");
		butDetermEquiv.addActionListener(this);
		butDetermEquiv.setActionCommand(EQUIVALENCIA);
		
		butDelteNoAlc=new JButton("<html>Eliminar Estados<br>no alcanzables</html>");
		butDelteNoAlc.addActionListener(this);;
		butDelteNoAlc.setActionCommand(ELIMINARNOALCAZABLES);
		
		add(butSeleccion);
		add(butEstadoNor);
		add(butDetermEquiv);
		add(butDelteNoAlc);
		add(butRetroceso);
	}
	public void actionPerformed(ActionEvent e) {
		String command=e.getActionCommand();
		if(command.equals(SELECCION)){
			principal.cambiarEstadoUsuario(EstadoUsuario.SELECCIONANDO);
		}else if(command.equals(ESTADONORMAL)){
			principal.cambiarEstadoUsuario(EstadoUsuario.CREANDOESTADOS);
		}else if(command.equals(EQUIVALENCIA)){
			principal.detEquivalencia();
		}else if(command.equals(ELIMINARNOALCAZABLES)){
			principal.DeleteEstadosNoAlc();
		}else if(command.equals(RETROCESO)) {
			principal.atras();
		}
	}
}
