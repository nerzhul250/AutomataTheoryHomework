package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import mundo.AutomataManager;
import mundo.Conexion;
import mundo.EstadoUsuario;

public class PanelDibAut extends JPanel implements MouseListener{
	
	private AutomataManager mundo;
	private InterfazAutomata principal;
	public PanelDibAut(AutomataManager am,InterfazAutomata ventana){
		setPreferredSize(new Dimension(AutomataManager.ANCHOPAN,AutomataManager.ALTOPAN));
		mundo=am;
		principal=ventana;
		addMouseListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0,0,this.getWidth(), this.getHeight());
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("TimesRoman",Font.PLAIN,20));
		g2d.drawString("Estado Seleccionado: "+mundo.getEstadoSeleccionadoActual(),0,g2d.getFont().getSize());
		/////////////////////////////////////////////////////////
		if(mundo.getEu().equals(EstadoUsuario.SELECCIONANDO)){
			g2d.drawString("Accion actual: Seleccionando estados",0,g2d.getFont().getSize()*2);			
		}else if(mundo.getEu().equals(EstadoUsuario.CREANDOESTADOS)){
			g2d.drawString("Accion actual: Creando nuevos estados",0,g2d.getFont().getSize()*2);
		}else if(mundo.getEu().equals(EstadoUsuario.CONECTANDO)){
			g2d.drawString("Accion actual: Conectando estados",0,g2d.getFont().getSize()*2);
		}
		g2d.drawString("Simbolo Entrada Seleccionado: "+mundo.getSimboloEntradaActual(),0,g2d.getFont().getSize()*3);
		if(mundo.getMealyMoore()==AutomataManager.MEALY){
			g2d.drawString("Simbolo Salida Seleccionado: "+mundo.getSimboloSalidaActual(),0,g2d.getFont().getSize()*4);			
		}
		/////////////////////////////////////////////////////////
		for (int i = 0; i < mundo.getAutomataActual().getEstados().size(); i++) {
			/////////////////////////////////////////////////////////
			int posx=mundo.getAutomataActual().getEstados().get(i).getPosj();
			int posy=mundo.getAutomataActual().getEstados().get(i).getPosi();
			g2d.setFont(new Font("TimesRoman", Font.PLAIN,15));
			if(mundo.getEstadoSeleccionadoActual()==i){
				g2d.setColor(Color.YELLOW);
				g2d.fillOval(-5+(int)(posx-mundo.estadoRadio),-5+(int)(posy-mundo.estadoRadio),10+(int)mundo.estadoRadio*2,10+(int)mundo.estadoRadio*2);
			}
			g2d.setColor(Color.BLACK);
			g2d.fillOval((int)(posx-mundo.estadoRadio),(int)(posy-mundo.estadoRadio),(int)mundo.estadoRadio*2,(int)mundo.estadoRadio*2);
			//Flecha para el estado inicial
			if(i==0) {				
				int inicial=60;
				int inicialFlehca=30;
				g2d.fillRect(posx-inicial, posy-3,(int) (inicial-mundo.estadoRadio),6);
				int[] f0x={posx-inicialFlehca,posx-inicialFlehca-10,posx-inicialFlehca-20,posx-inicialFlehca-20};
				int[] f0y= {posy-3,posy-3,posy-13,posy-23};
				int[] f1x={posx-inicialFlehca,posx-inicialFlehca-10,posx-inicialFlehca-20,posx-inicialFlehca-20};
				int[] f1y= {posy+3,posy+3,posy+13,posy+23};
				g2d.fillPolygon(f1x, f1y, 4);
				g2d.fillPolygon(f0x,f0y,4);
			}
				
			g2d.setColor(Color.WHITE);
			String B="";
			if(!mundo.getAutomataActual().getEstados().get(i).getSimboloSalida().isEmpty()){
				B=","+mundo.getAutomataActual().getEstados().get(i).getSimboloSalida();
			}
			g2d.drawString("q_"+i+B, posx-10, posy+5);
			/////////////////////////////////////////////////////////
			ArrayList<Conexion> conexiones=mundo.getAutomataActual().getEstados().get(i).getConexiones();
			for (int j = 0; j < conexiones.size(); j++) {				
				int[] first=conexiones.get(j).getCamino().get(0);
				for (int j2 = 1; j2 < conexiones.get(j).getCamino().size(); j2++) {
					int[] second=conexiones.get(j).getCamino().get(j2);
					if(j2==1){
						int[] v={second[0]-first[0],second[1]-first[1]};
						g2d.setColor(Color.BLUE);
						g2d.setFont(new Font("TimesRoman", Font.PLAIN,20)); 
						String A="";
						if(mundo.getMealyMoore()==AutomataManager.MEALY){
							A="/"+conexiones.get(j).getSimboloSalida();
						}
						g2d.drawString(conexiones.get(j).getSimboloEntrada()+A,first[0]+15*v[0],first[1]+15*v[1]);
						g2d.setColor(Color.BLACK);
						int[] v1={(int) (((v[0]-v[1])*5)/Math.sqrt(2)),(int) (((v[0]+v[1])*5)/Math.sqrt(2))};
						int[] v2={(int) (((v[0]+v[1])*5)/Math.sqrt(2)),(int) (((v[1]-v[0])*5)/Math.sqrt(2))};
						g2d.drawLine(first[0],first[1],first[0]+v1[0],first[1]+v1[1]);
						g2d.drawLine(first[0],first[1],first[0]+v2[0],first[1]+v2[1]);
					}
					g2d.drawLine(first[0],first[1],second[0],second[1]);
					first=second;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		principal.eventoUsuario(e.getX(),e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
