package codigo;



import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import codigo.GameOver.Estadisticas;
import codigo.PantallaPrincipal.MiRunnable;


///** Clase para gestionar visibilización de ventanas de forma global.
// * Las ventanas se deben crear sin ser visibles, y añadirse al gestor
// *   con el método add( JFrame )
// * Se deben crear sin dispose automático al cierre 
// *   (para poderse visualizar varias veces).
// * Este método se ejecuta una única vez al inicio de la clase.
// * @author 
// */
public class GestorVentanas 
{

	/* Lista de ventanas. Gestión interna de todas las ventanas utilizadas */
	private static ArrayList<JFrame> listaVentanas = init();

	/** Inicializador de Ventanas.
	 * Podría crear e inicializar todas las ventanas del gestor.
	 * (o se van añadiendo con el método add)
	 * @return	ArrayList de todas las ventanas creadas
	 */
	private static ArrayList<JFrame> init() {
		ArrayList<JFrame> lista = new ArrayList<JFrame>();
		// Si queremos ventanas por defecto creadas se podrían poner aquí
		// lista.add( new Ventana... );
		return lista;
	}

	/** Añade una ventana al gestor
	 * @param vent
	 */
	public static void add( JFrame vent ) {
		listaVentanas.add( vent );
	}
	
	/** Libera y cierra todas las ventanas del gestor
	 */
	public static void closeAndDispose() {
		for (JFrame vent : listaVentanas) {
			vent.dispose();
		}
		listaVentanas.clear();
	}
	
	/** Hace visible la ventana indicada.
	 * Si hay algún error en los parámetros, no hace nada.
	 * @param ventanaAVisibilizar	Clase de la ventana a hacer visible
	 * @param ocultarElResto	Si true, oculta el resto. Si no, las deja como estuvieran
	 * @param numDeVentana	Si hay más de una ventana de la misma clase, índice de la ventana a visibilizar
	 */
	public static void hacerVisible(Class<?> ventanaAVisibilizar, boolean ocultarElResto, int numDeVentana ) {
		for (JFrame vent : listaVentanas) {
			if (vent.getClass().isAssignableFrom( ventanaAVisibilizar )) {
				// Si la clase de la ventana es igual o descendiente de la indicada
				if (numDeVentana > 0) {
					// Si no es la primera, esperar la siguiente
					numDeVentana--;
					if (ocultarElResto) vent.setVisible( false );
				} else if (numDeVentana == 0) {  // Si lo es, visibilizarla
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

	/** Oculta la ventana indicada.
	 * Si hay algún error en los parámetros, no hace nada.
	 * @param ventanaAOcultar	Clase de la ventana a hacer visible
	 * @param numDeVentana	Si hay más de una ventana de la misma clase, índice de la ventana a ocultar
	 */
	public static void ocultar(Class<?> ventanaAVisibilizar, int numDeVentana ) {
		for (JFrame vent : listaVentanas) {
			if (vent.getClass().isAssignableFrom( ventanaAVisibilizar )) {
				// Si la clase de la ventana es igual o descendiente de la indicada
				if (numDeVentana > 0) {
					// Si no es la primera, esperar la siguiente
					numDeVentana--;
				} else {  // Si lo es, visibilizarla
					vent.setVisible( false );
					break;
				}
			}
		}
	}

	
	/* Método de prueba */
	public static void main (String s[]) {
		add( new PantallaInicio() );
		add( new Principal() );
		add( new GameOver() );
		
		try
		{
		
		hacerVisible( PantallaInicio.class, true, 0 ); //Pantalla de Inicio OK
		if(PantallaInicio.jugar.isSelected()==true)
		{
		hacerVisible( Principal.class, true, 0 ); //Sale la pantalla principal pero no desaparece la pantalla de inicio
		}
		else
		{
	
		}

		if(GameOver.retry.isSelected()==true)
		{
		hacerVisible( Principal.class, true, 0 );// Volver a jugar OK
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




/* Ejemplos de ventanas para la prueba */

@SuppressWarnings("serial")
class PantallaInicio extends JFrame 
{
	protected static String Nombre;
	JTextField user;
	protected static JButton jugar;
	JLabel usuario;

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
	
		if(usuario != null)
		{
			jugar.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					Nombre=user.getText();
					Principal miVentana = new Principal();
					PantallaPrincipal miVentana1 = new PantallaPrincipal();
					
					miVentana.creaBloque();
					miVentana.setVisible( true );
					BD.conexion();

					
					miVentana1.miHilo = miVentana1.new MiRunnable();  // Sintaxis de new para clase interna
					
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
		}
		
		
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setTitle("Pantalla de Inicio");
		setSize( 300, 100 );
		setLocationRelativeTo(null);
	}
}
@SuppressWarnings("serial")
class Principal extends JFrame
{
	private static final long serialVersionUID = 1L;  // Para serializaci�n
	static JPanel pPrincipal; // Panel del juego (layout nulo)
	JPanel pCabecera;
	static bloqueJuego [] miBloque; // Coche del juego
	static manzana miManzana = null;
	PantallaPrincipal.MiRunnable miHilo = null;// Hilo del bucle principal de juego
	PantallaPrincipal.RandomApple miHilo2 = null;
	PantallaPrincipal.cronometro miHilo3 = null;
	int num_casillas[][] = new int [17][15];
	Point posicion = new Point(6, 8);
	boolean posible = true;
	static int growUp = 1;
	/** Constructor de la ventana de juego. Crea y devuelve la ventana inicializada
	 * sin coches dentro
	 */
	public Principal()
	{
			
			setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
	
			pPrincipal = new JPanel();
			pCabecera = new JPanel();
			
			// Formato y layouts
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
			
			// Cierre del hilo al cierre de la ventana
			addWindowListener( new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					if (miHilo!=null) 
					{
						miHilo.acaba();
						miHilo2.acaba();
						miHilo3.acaba();
						
						BD.finConexion();
					}
					
				}
			});
		
	}
	public void creaBloque( ) 
	{
//		// Crear y a�adir el coche a la ventana
		miBloque = new bloqueJuego[300];
		miBloque[0]=new bloqueJuego();
		miBloque[0].setPosicion( 300, 400);
		pPrincipal.add( miBloque[0].getGrafico());
		

	}
	
	
}



@SuppressWarnings("serial")
class GameOver  extends JFrame {
	
protected static JButton retry;
//protected static JButton salir;
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
				Principal miVentana = new Principal();
				PantallaPrincipal miVentana1 = new PantallaPrincipal();
				
				miVentana.creaBloque();
				miVentana.setVisible( true );
				
				
				
				// Crea el hilo de movimiento del coche y lo lanza
				
				miVentana1.miHilo = miVentana1.new MiRunnable();  // Sintaxis de new para clase interna
				
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
				
				System.exit(0);
				BD.finConexion();
				
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
		
		public Estadisticas ()
		{
			ArrayList<Player> ListaJugadores;
			String Players = null;
			ListaJugadores = OperacionesGuardado.LeerFichero();
			setTitle ("Estadisticas");
			setSize (400,200);
			
			PanelTexto = new JTextPane ();
			this.add(PanelTexto, BorderLayout.CENTER);
			
			for (Player aux: ListaJugadores)
			{
				Players = Players + "\n" + aux.toString();
			}
			
			PanelTexto.setText(Players);
			Cerrar = new JButton ("Cerrar");
			this.add(PanelTexto, BorderLayout.SOUTH);
			PanelTexto.setVisible(true);
			PanelTexto.setEditable(false);
			
			panel1 =new JPanel();
			
			add(panel1, BorderLayout.CENTER);
			
			panel1.add(PanelTexto);
		}

	}
}

