package codigo;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.LineNumberInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import COMUN.clsUsuarioVacio;
import codigo.GameOver.Estadisticas;
import codigo.PantallaPrincipal.MiRunnable;



public class GestorVentanas 
{
	
	private static ArrayList<JFrame> listaVentanas = init();

	
	private static ArrayList<JFrame> init() {
		ArrayList<JFrame> lista = new ArrayList<JFrame>();
		
		return lista;
	}

	
	public static void add( JFrame vent ) {
		listaVentanas.add( vent );
	}
	
	
	public static void closeAndDispose() {
		for (JFrame vent : listaVentanas) {
			vent.dispose();
		}
		listaVentanas.clear();
	}
	
	
	public static void hacerVisible(Class<?> ventanaAVisibilizar, boolean ocultarElResto, int numDeVentana ) {
		for (JFrame vent : listaVentanas) {
			if (vent.getClass().isAssignableFrom( ventanaAVisibilizar )) {
				
				if (numDeVentana > 0) {
					
					numDeVentana--;
					if (ocultarElResto) vent.setVisible( false );
				} else if (numDeVentana == 0) {  
					vent.setVisible( true );
					numDeVentana--;
				} else {
					vent.setVisible( false );
				}
			} else {
				if (ocultarElResto) vent.setVisible( false );
			}
		}
	}

	
	public static void ocultar(Class<?> ventanaAVisibilizar, int numDeVentana ) {
		for (JFrame vent : listaVentanas) {
			if (vent.getClass().isAssignableFrom( ventanaAVisibilizar )) {
				
				if (numDeVentana > 0) {
					
					numDeVentana--;
				} else {  
					vent.setVisible( false );
					break;
				}
			}
		}
	}
	
	public static void cerrar ()
	{
		Principal Ventana1;
		PantallaPrincipal Ventana2;
		Ventana1 = (Principal) listaVentanas.get(2);
		Ventana2 = (PantallaPrincipal) listaVentanas.get(3);
		
		Ventana2.miHilo.acaba();
		Ventana2.miHilo2.acaba();
		Ventana2.miHilo3.acaba();
		
		Ventana2.dispose();
		Ventana1.dispose();
		
		listaVentanas.remove(3);
		listaVentanas.remove(2);
		
		
		Principal.pPrincipal.removeAll();
		Principal.pPrincipal.repaint();
		
		Principal.growUp = 1;
		Principal.creaBloque();
		Principal.miManzana = null;
		
		
		
	}

	
	
	public static void main (String s[]) {
		add( new PantallaInicio() );
	
		add( new GameOver() );
		String es = "estadisticas";
		clsBD.initBD(es);
		clsBD.crearTablaEstadisticas();
		
		try
		{
		
		hacerVisible( PantallaInicio.class, true, 0 ); 
		if(PantallaInicio.jugar.isSelected()==true)
		{
		hacerVisible( Principal.class, true, 0 ); 
		}
		else
		{
	
		}

		if(GameOver.retry.isSelected()==true)
		{
		hacerVisible( Principal.class, true, 0 );
		}

		else if(GameOver.estadisticas.isSelected()==true)
		{
			hacerVisible( Estadisticas.class, true, 0);
		}
		else
		{
		Thread.sleep( 150000 );
		ocultar( GameOver.class, 0 );
		}
		
		closeAndDispose();
		}catch(InterruptedException e)
		{
			
		}
	}
}






@SuppressWarnings("serial")
class PantallaInicio extends JFrame 
{
	protected static String Nombre;
	JTextField user;
	protected static JButton jugar;
	JLabel usuario;
	Date fecha;

	JPanel panel;
	
	public PantallaInicio()
	{
	user =new JTextField();
	user.setBounds(new Rectangle(50,25,100,25));
	jugar = new JButton("Empezar");
	jugar.setBounds(new Rectangle(150,25,100,25));
	
	usuario= new JLabel();
	usuario.setText("Usuario: ");
	usuario.setBounds(0, 25, 100, 25);
	
	user.setVisible(true);
	jugar.setVisible(true);
	usuario.setVisible(true);
	

	panel =new JPanel();
	
	add(panel, BorderLayout.CENTER);
	
	panel.add(user);
	panel.add(jugar);
	panel.add(usuario);
	
	panel.setLayout(null);
	
			jugar.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					Nombre=user.getText();
					
					if (!Nombre.isEmpty())
					{	
						Principal miVentana = new Principal();
						GestorVentanas.add(miVentana);
						PantallaPrincipal miVentana1 = new PantallaPrincipal();
						GestorVentanas.add(miVentana1);
					
						
						miVentana.creaBloque();
						miVentana.setVisible( true );
					
						miVentana1.miHilo = miVentana1.new MiRunnable(); 
					
						Thread nuevoHilo = new Thread( miVentana1.miHilo );
						nuevoHilo.start();
					
						miVentana1.miHilo2 = miVentana1.new RandomApple();
					
						Thread nuevoHilo2 = new Thread( miVentana1.miHilo2 );
						nuevoHilo2.start();
					
						miVentana1.miHilo3 = miVentana1.new cronometro();
					
						Thread nuevoHilo3 = new Thread (miVentana1.miHilo3 );
						nuevoHilo3.start();
				
					} else  
					{
						try {
							throw new clsUsuarioVacio();
						} catch (clsUsuarioVacio e) 
						{
							JOptionPane.showMessageDialog((JButton) arg0.getSource(), e.getMessage());
						}
					}
				
	
				}
				
			});
		
		
		
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setTitle("Pantalla de Inicio");
		setSize( 300, 100 );
		setLocationRelativeTo(null);
	}
}
@SuppressWarnings("serial")
class Principal extends JFrame
{
	private static final long serialVersionUID = 1L; 
	static JPanel pPrincipal; 
	JPanel pCabecera;
	static bloqueJuego [] miBloque; 
	static manzana miManzana = null;
	PantallaPrincipal.MiRunnable miHilo = null;
	PantallaPrincipal.RandomApple miHilo2 = null;
	PantallaPrincipal.cronometro miHilo3 = null;
	boolean posible = true;
	static int growUp = 1;
	
	public Principal()
	{
			
			setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
	
			pPrincipal = new JPanel();
			pCabecera = new JPanel();
			
			
			pPrincipal.setLayout( null );
			pPrincipal.setBackground( Color.CYAN);
			
			add( pPrincipal, BorderLayout.CENTER );

			pCabecera.setBounds(0,100,850,100);
			
			add( pCabecera, BorderLayout.NORTH );
			
			
			setSize( 850, 750);
			
			this.addKeyListener( new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					
				for (int i = 0; i<growUp; i++)
				{
				if(posible)
				{
					switch (e.getKeyCode()) {
						case KeyEvent.VK_UP: 
						{
							if(miBloque[i].getDireccionActual()==90||miBloque[i].getDireccionActual()==270)
							{
								
							}
							else
							{
								miBloque[i].setDireccionActual(90);  
							}
							break;
						}
						case KeyEvent.VK_DOWN: 
						{
							if(miBloque[i].getDireccionActual()==90||miBloque[i].getDireccionActual()==270)
							{
								
							}
							else
							{
								miBloque[i].setDireccionActual(270); 
							}
							break;
							
						}
						case KeyEvent.VK_LEFT: 
						{
							
							if(miBloque[i].getDireccionActual()==0||miBloque[i].getDireccionActual()==180)
							{
								
							}
							else
							{
								miBloque[i].setDireccionActual(180); 
							}
							break;
						}
						case KeyEvent.VK_RIGHT: 
						{
							if(miBloque[i].getDireccionActual()==0||miBloque[i].getDireccionActual()==180)
							{
								
							}
							else
							{
								miBloque[i].setDireccionActual(0);  
							}
							break;
						}
					}
					
				}
				}
				}
			});
			
			
			addWindowListener( new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					if (miHilo!=null) 
					{
						miHilo.acaba();
						miHilo2.acaba();
						miHilo3.acaba();
						
						
						clsBD.close();
					}
					
				}
			});
		
	}
	public static void creaBloque( ) 
	{

		miBloque = new bloqueJuego[300];
		miBloque[0]=new bloqueJuego();
		miBloque[0].setPosicion( 300, 400);
		pPrincipal.add( miBloque[0].getGrafico());
		

	}
	
	
}



@SuppressWarnings("serial")
class GameOver  extends JFrame {
	
protected static JButton retry;
protected static JButton estadisticas;
protected static JButton close;
JPanel panel;
	
	public GameOver() 
	{
		setSize(300, 150);
		setLocation(400, 150);
		setTitle( "Game Over" );
		setResizable(false);
		
		retry =new JButton("Volver a jugar");
		retry.setBounds(70, 50, 60, 20);
		
		estadisticas = new JButton("Estadisticas");
		estadisticas.setBounds(70, 90, 60, 20);
		panel = new JPanel();
		
		close = new JButton("Cerrar");
		close.setBounds(70, 130, 60, 20);
		close.setVisible(true);
		
		
		retry.setVisible(true);

		estadisticas.setVisible(true);
		
		
		add(panel, BorderLayout.CENTER);
		
		panel.add(retry);
		panel.add(estadisticas);
		panel.add(close);
		
		retry.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				GestorVentanas.cerrar();
				
				Principal miVentana = new Principal();
				GestorVentanas.add(miVentana);
				PantallaPrincipal miVentana1 = new PantallaPrincipal();
				GestorVentanas.add(miVentana1);
			
				
				miVentana.creaBloque();
				miVentana.setVisible( true );
			
				miVentana1.miHilo = miVentana1.new MiRunnable();  
			
				Thread nuevoHilo = new Thread( miVentana1.miHilo );
				nuevoHilo.start();
			
				miVentana1.miHilo2 = miVentana1.new RandomApple();
			
				Thread nuevoHilo2 = new Thread( miVentana1.miHilo2 );
				nuevoHilo2.start();
			
				miVentana1.miHilo3 = miVentana1.new cronometro();
			
				Thread nuevoHilo3 = new Thread (miVentana1.miHilo3 );
				nuevoHilo3.start();
				
				
				
			}
		});
		estadisticas.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Estadisticas pantalla = new Estadisticas();
				pantalla.setVisible(true);
				
			}
			
		});
		

		
		close.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				// TODO Auto-generated method stub
				
				clsBD.close();
				System.exit(0);
				
				
			}
			
		
		});
		

		this.addWindowListener(new WindowListener ()
				{

					@Override
					public void windowActivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowClosed(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowClosing(WindowEvent arg0) 
					{
						System.exit(0);
						clsBD.close();
						
					}

					@Override
					public void windowDeactivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowDeiconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowIconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowOpened(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
			
				});
		
		
		
	}
	
	
	public static class Estadisticas extends JFrame
	{

		private static final long serialVersionUID = 1L;
		
		private JTextPane PanelTexto;
		private JButton Cerrar;
		private JPanel panel1;
		ArrayList<Player> jugadores = new ArrayList<Player>();
		private JScrollPane Scroll;
		
		public Estadisticas ()
		{
			setTitle ("Estadisticas");
			setSize (800, 600);
			
			PanelTexto = new JTextPane ();
			
			Scroll = new JScrollPane(PanelTexto);
			
			this.add(Scroll,BorderLayout.CENTER);
			
			jugadores = clsBD.LeerEstadisticas();
			String players = "";
			
			for (Player aux : jugadores)
			{
				players = players + "\n" + aux.toString() + "\n";
			}
		
			PanelTexto.setText(players);
			Cerrar = new JButton ("Cerrar");
			
			StyledDocument doc = PanelTexto.getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);
			
			PanelTexto.setVisible(true);
			PanelTexto.setEditable(false);
			
			Scroll.setPreferredSize(new Dimension(700,500));
			
			Scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		}

	}
}

