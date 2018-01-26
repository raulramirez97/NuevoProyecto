package codigo;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import COMUN.clsUsuarioVacio;



public class GestorVentanas 
{

	public static ArrayList<JFrame> listaVentanas = init();
	
	
	
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
	
	
	public static  void cerrar ()
	{		
		Principal Ventana1 = (Principal) listaVentanas.get(1);
		
		Ventana1.miHilo.acaba();
		Ventana1.miHilo2.acaba();
		Ventana1.miHilo3.acaba();
		
		Ventana1.growUp = 1;
		Ventana1.creaBloque();
		Ventana1.miManzana = null;
		
		Ventana1.pPrincipal.removeAll();
		Ventana1.pPrincipal.repaint();
		
		Ventana1.dispose();
		
		listaVentanas.remove(1);
	}

	
	
	public static void main (String s[]) {
		String es = "estadisticas";
		clsBD.initBD(es);
		clsBD.crearTablaEstadisticas();
		
		add( new PantallaInicio() );
		add( new GameOver() );
		
		hacerVisible( PantallaInicio.class, true, 0 ); 
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
						PantallaInicio inicio = (PantallaInicio) GestorVentanas.listaVentanas.get(0);
						inicio.dispose();
						GestorVentanas.listaVentanas.remove(0);
						
						Principal miVentana = new Principal();
						GestorVentanas.add(miVentana);
					
						
						miVentana.creaBloque();
						miVentana.setVisible( true );
					
						miVentana.miHilo = miVentana.new MiRunnable(); 
					
						Thread nuevoHilo = new Thread( miVentana.miHilo );
						nuevoHilo.start();
					
						miVentana.miHilo2 = miVentana.new RandomApple();
					
						Thread nuevoHilo2 = new Thread( miVentana.miHilo2 );
						nuevoHilo2.start();
						
						miVentana.miHilo3 = miVentana.new cronometro();
					
						Thread nuevoHilo3 = new Thread (miVentana.miHilo3 );
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
class Principal extends JFrame
{
	private static final long serialVersionUID = 1L; 
	 JPanel pPrincipal; 
	JPanel pCabecera;
	 bloqueJuego [] miBloque; 
	 manzana miManzana = null;
	 int growUp = 1;
	 
	 MiRunnable miHilo = null;
		RandomApple miHilo2 = null;
		cronometro miHilo3 = null;
		boolean posible = true;
		int puntuacion = 0;
		String tiempofinal;
		
		public static int Altura;
		public static int Anchura;
	
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
			
			
			setSize( 800, 800);
			Altura = 800 - 150;
			Anchura = 800 - 150;
			
			this.setResizable(false);
			
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
	public void creaBloque( ) 
	{

		miBloque = new bloqueJuego[300];
		miBloque[0]=new bloqueJuego();
		miBloque[0].setPosicion( 300, 400);
		pPrincipal.add( miBloque[0].getGrafico());
		

	}
	
	class MiRunnable implements Runnable {
		boolean sigo = true;
		String Nombre = PantallaInicio.Nombre;
		private Logger logger = Logger.getLogger(MiRunnable.class.getName());
		Player a;

		@Override
		public void run() {
			logger.setLevel(Level.INFO);
			
			while (sigo) {
				
			posible = false;
			
			Principal miVentana = (Principal) GestorVentanas.listaVentanas.get(1);

			double xAnterior = miVentana.miBloque[0].getPosX();
			double yAnterior = miVentana.miBloque[0].getPosY();
			
			miVentana.miBloque[0].mueveX();
			miVentana.miBloque[0].mueveY();
			
			for (int i = 1; i<miVentana.growUp; i++)
			{
				double xGuardado = miVentana.miBloque[i].getPosX();
				double yGuardado = miVentana.miBloque[i].getPosY();
				
				miVentana.miBloque[i].setPosicion(xAnterior, yAnterior);
				xAnterior = xGuardado;
				yAnterior = yGuardado;
			}
				
				
			
			posible = true;
				
				if (miVentana.miBloque[0].getPosX() < -JLabelBloque.TAMANYO_BLOQUE/2 || miVentana.miBloque[0].getPosX()>miVentana.pPrincipal.getWidth()-JLabelBloque.TAMANYO_BLOQUE/2 ) {
					
					a = RegistrarJugador(Nombre, puntuacion, tiempofinal);
					Date fechayHora = a.getFecha();
					SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );						
					
					String fecha = formato.format(fechayHora);
					logger.info("El juego ha acabado");
					
					
					clsBD.InsertEstadisticas(a.getNick(), a.getPuntuacion(), a.getTiempo(), fecha);
					
					logger.info("El usuario se ha registrado");

					
					logger.info("La puntuación es de: " + puntuacion);
					
					GestorVentanas.hacerVisible( GameOver.class, true, 0);
					
					miHilo2.acaba();
					miHilo3.acaba();
					this.acaba();
					
				}
				
				if (miVentana.miBloque[0].getPosY() < -JLabelBloque.TAMANYO_BLOQUE/2 || miVentana.miBloque[0].getPosY()>miVentana.pPrincipal.getHeight()-JLabelBloque.TAMANYO_BLOQUE/2 ) {
				
					a = RegistrarJugador(Nombre, puntuacion, tiempofinal);
					Date fechayHora = a.getFecha();
					SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );						
					
					String fecha = formato.format(fechayHora);
					logger.info("El juego ha acabado");
				
					
					clsBD.InsertEstadisticas(a.getNick(), a.getPuntuacion(), a.getTiempo(), fecha);
					
					logger.info("El usuario se ha registrado");

					
					logger.info("La puntuación es de: " + puntuacion);
					
					GestorVentanas.hacerVisible( GameOver.class, true, 0);
					
					miHilo2.acaba();
					miHilo3.acaba();
					this.acaba();
					
					
				}
				
				for (int i = 1; i < miVentana.growUp;i++)
				{
					if (miVentana.miBloque[0].getPosX()==miVentana.miBloque[i].getPosX()&&miVentana.miBloque[0].getPosY()==miVentana.miBloque[i].getPosY())
					{
						
						a = RegistrarJugador(Nombre, puntuacion, tiempofinal);
						Date fechayHora = a.getFecha();
						SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );						
						
						String fecha = formato.format(fechayHora);
						logger.info("El juego ha acabado");
						
						clsBD.InsertEstadisticas(a.getNick(), a.getPuntuacion(), a.getTiempo(), fecha);

						logger.info("El usuario se ha registrado");

						
						logger.info("La puntuación es de: " + puntuacion);
						
						GestorVentanas.hacerVisible( GameOver.class, true, 0);
						
						miHilo2.acaba();
						miHilo3.acaba();
						this.acaba();
					}
				}
				
				try {
					Thread.sleep(200);
				} catch (Exception e) {
				}
			}
		}
		
		public Player RegistrarJugador(String nick, int puntuacion, String tiempo) 
		{
			Player a;
			
			a = new Player (nick, puntuacion, tiempo);
			
			return a;
			
		}

		
		public void acaba() 
		{
			sigo = false;
		}
	};
	public void growUp()
	{
		Principal miVentana = (Principal) GestorVentanas.listaVentanas.get(1);
		miVentana.miBloque[miVentana.growUp]=new bloqueJuego();
		miVentana.miBloque[miVentana.growUp].setPosicion(miVentana.miBloque[miVentana.growUp-1].getPosX()-30, miVentana.miBloque[miVentana.growUp-1].getPosY());
		miVentana.pPrincipal.add(miVentana.miBloque[miVentana.growUp].getGrafico());
		miVentana.miBloque[miVentana.growUp].setDireccionActual(miVentana.miBloque[miVentana.growUp-1].miDireccionActual);
		miVentana.growUp++;
	}
	
	class RandomApple implements Runnable {
		boolean sigo2 = true;
		private Logger logger = Logger.getLogger(MiRunnable.class.getName());
		
		@Override
		public void run() {
			
			logger.setLevel(Level.INFO);
			Point P;
			
			Principal miVentana = (Principal) GestorVentanas.listaVentanas.get(1);
			
			while (true) 
			{
				if(miVentana.miManzana == null)
				{
					P = RecursividadManzana(5);
					
					miVentana.miManzana = new manzana(P.x,P.y);
					miVentana.pPrincipal.add(miVentana.miManzana);
					miVentana.pPrincipal.repaint();
				}
				
				
				if(miVentana.miManzana.getLocation().distance(miVentana.miBloque[0].getPosX(), miVentana.miBloque[0].getPosY()) < 30)
				{	
					P = RecursividadManzana(5);
					
					miVentana.miManzana.setLocation(P);
					miVentana.pPrincipal.repaint();
					growUp();
					puntuacion = puntuacion + 1;
				}
			
				
				
				try 
				{	
				Thread.sleep( 33 );
				} catch (Exception e) {
				}
				
			}
			
		}
		
		public Point RecursividadManzana(int NumRecu)
		{
			Principal miVentana = (Principal) GestorVentanas.listaVentanas.get(1);
			String PosicionManzana;
			Point P = null;
			Random r;
			int  apple_posX = 0;
			int  apple_posY = 0;
			
			r= new Random();
			apple_posX = r.nextInt(Anchura);
			apple_posY = r.nextInt(Altura);
			
			apple_posX = apple_posX + 70;
			apple_posY = apple_posY + 70;
			
			if(NumRecu != 0)
			{	
				for(int i = 0; i<miVentana.growUp; i++)
				{
					if(new Point(apple_posX, apple_posY).distance(miVentana.miBloque[i].getPosX(), miVentana.miBloque[i].getPosY()) < 40)
					{
						RecursividadManzana(NumRecu-1);
					}else{
						P = new Point(apple_posX, apple_posY);
						PosicionManzana = "La posición de la manzana es ("+  P.getX() +", " + P.getY() + ")";
						logger.info(PosicionManzana);	
						return P;
					}
				}
				
			}else{
				P = new Point (200,300);
				PosicionManzana = "La posición de la manzana es ("+  P.getX() + ") , (" + P.getY() + ")";
				logger.info(PosicionManzana);	
			}		
			return P;
		}
		
		
		
		public void acaba() 
		{
			sigo2 = false;
		}
		
	};
	class cronometro implements Runnable
	{
		boolean sigo3 = true;
		private Logger logger = Logger.getLogger( MiRunnable.class.getName());
		
		
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			int segundos = 0;
			int decimas = 0;
			
			Principal miVentana = (Principal) GestorVentanas.listaVentanas.get(1);
			

			try {
			while (sigo3)
				{
				
				if(decimas != 9)
				{ 
					decimas ++;
				}
				else
				{
					segundos ++;
					decimas = 0;
				
				}
				
					tiempofinal = segundos +"."+decimas;
					
					
					if (miVentana.miBloque[0].getPosX() < -JLabelBloque.TAMANYO_BLOQUE/2 || miVentana.miBloque[0].getPosX()>miVentana.pPrincipal.getWidth()-JLabelBloque.TAMANYO_BLOQUE/2 ) {
						
						
						sigo3 = false;
						miHilo3.acaba();
					
					}
					
					else if (miVentana.miBloque[0].getPosY() < -JLabelBloque.TAMANYO_BLOQUE/2 || miVentana.miBloque[0].getPosY()>miVentana.pPrincipal.getHeight()-JLabelBloque.TAMANYO_BLOQUE/2 ) {
						
						
						sigo3 = false;
						 
						

						miHilo3.acaba();
					}
					
					
	         Thread.sleep(99);
	         }   
			
			logger.info("El tiempo es de: " + tiempofinal);
			
			
			
	     } catch (Exception ex) 
		{
	          System.out.println(ex.getMessage());
	     }                 	
		}
		public void acaba() {
			sigo3 = false;
		}
		
	
	};
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
			
				
				miVentana.creaBloque();
				miVentana.setVisible( true );
			
				miVentana.miHilo = miVentana.new MiRunnable();  
			
				Thread nuevoHilo = new Thread( miVentana.miHilo );
				nuevoHilo.start();
			
				miVentana.miHilo2 = miVentana.new RandomApple();
			
				Thread nuevoHilo2 = new Thread( miVentana.miHilo2 );
				nuevoHilo2.start();
			
				miVentana.miHilo3 = miVentana.new cronometro();
			
				Thread nuevoHilo3 = new Thread (miVentana.miHilo3 );
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
			
			PanelTexto.setVisible(true);
			PanelTexto.setEditable(false);
			
			Scroll.setPreferredSize(new Dimension(700,500));
			
			Scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}

	}

}

