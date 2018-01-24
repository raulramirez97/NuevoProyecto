package codigo;


import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



import java.util.logging.*;

public class PantallaPrincipal extends JFrame
{

	private static final long serialVersionUID = 1L;  // Para serialización
	
	MiRunnable miHilo = null;// Hilo del bucle principal de juego
	RandomApple miHilo2 = null;
	cronometro miHilo3 = null;
	int num_casillas[][] = new int [17][15];
	Point posicion = new Point(6, 8);
	boolean posible = true;
	int puntuacion = 0;
	String tiempofinal;
	
	
	/** Constructor de la ventana de juego. Crea y devuelve la ventana inicializada
	 * sin coches dentro
//	 */

	class MiRunnable implements Runnable {
		boolean sigo = true;
		String Nombre = PantallaInicio.Nombre;
		private Logger logger = Logger.getLogger( MiRunnable.class.getName());
		Player a;

		@Override
		public void run() {
			logger.setLevel(Level.INFO);
			// Bucle principal forever hasta que se pare el juego...
			while (sigo) {
				// Mover coche
			posible = false;
			
//				for (int i = 0; i<Principal.growUp; i++)
//				{
//					try {
//						Principal.miBloque[i].mueveX();
//						Principal.miBloque[i].mueveY();
//						if(i!=0)	Principal.miBloque[i].setDireccionActual(Principal.miBloque[i-1].miDireccionActual);
//						
//						Thread.sleep(5);
//					} catch (Exception e) {
//					}
//				}
//			
			double xAnterior = Principal.miBloque[0].getPosX();
			double yAnterior = Principal.miBloque[0].getPosY();
			
			Principal.miBloque[0].mueveX();
			Principal.miBloque[0].mueveY();
			
			for (int i = 1; i<Principal.growUp; i++)
			{
				double xGuardado = Principal.miBloque[i].getPosX();
				double yGuardado = Principal.miBloque[i].getPosY();
				
				Principal.miBloque[i].setPosicion(xAnterior, yAnterior);
				xAnterior = xGuardado;
				yAnterior = yGuardado;
			}
				
				
			
			posible = true;
				// Chequear choques
				if (Principal.miBloque[0].getPosX() < -JLabelBloque.TAMANYO_BLOQUE/2 || Principal.miBloque[0].getPosX()>Principal.pPrincipal.getWidth()-JLabelBloque.TAMANYO_BLOQUE/2 ) {
					
					a = RegistrarJugador(Nombre, puntuacion, tiempofinal);
					Date fechayHora = a.getFecha();
					SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );						
					
					String fecha = formato.format(fechayHora);
					logger.info("El juego ha acabado");
					//BD.insertEstadisticas(Nombre, puntuacion, tiempofinal);
					
					clsBD.InsertEstadisticas(a.getNick(), a.getPuntuacion(), a.getTiempo(), fecha);
					
					logger.info("El usuario se ha registrado");

					
					logger.info("La puntuación es de: " + puntuacion);
					
					GestorVentanas.hacerVisible( GameOver.class, true, 0);
					GestorVentanas.ocultar( Principal.class, 0 );
					
					miHilo.acaba();
					miHilo2.acaba();
					miHilo3.acaba();
				}
				// Se comprueba tanto X como Y porque podría a la vez chocar en las dos direcciones
				if (Principal.miBloque[0].getPosY() < -JLabelBloque.TAMANYO_BLOQUE/2 || Principal.miBloque[0].getPosY()>Principal.pPrincipal.getHeight()-JLabelBloque.TAMANYO_BLOQUE/2 ) {
					// Espejo vertical si choca en Y
					
					a = RegistrarJugador(Nombre, puntuacion, tiempofinal);
					Date fechayHora = a.getFecha();
					SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );						
					
					String fecha = formato.format(fechayHora);
					logger.info("El juego ha acabado");
					//BD.insertEstadisticas(Nombre, puntuacion, tiempofinal);
					
					clsBD.InsertEstadisticas(a.getNick(), a.getPuntuacion(), a.getTiempo(), fecha);
					
					logger.info("El usuario se ha registrado");

					
					logger.info("La puntuación es de: " + puntuacion);
					
					GestorVentanas.hacerVisible( GameOver.class, true, 0);
					GestorVentanas.ocultar( Principal.class, 0 );
					
					miHilo.acaba();
					miHilo2.acaba();
					miHilo3.acaba();
					
				}
				
				for (int i = 1; i < Principal.growUp;i++)
				{
					if (Principal.miBloque[0].getPosX()==Principal.miBloque[i].getPosX()&&Principal.miBloque[0].getPosY()==Principal.miBloque[i].getPosY())
					{
						
						
						
						
						a = RegistrarJugador(Nombre, puntuacion, tiempofinal);
						Date fechayHora = a.getFecha();
						SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );						
						
						String fecha = formato.format(fechayHora);
						logger.info("El juego ha acabado");
						//BD.insertEstadisticas(Nombre, puntuacion, tiempofinal);
						
						clsBD.InsertEstadisticas(a.getNick(), a.getPuntuacion(), a.getTiempo(), fecha);

						logger.info("El usuario se ha registrado");

						
						logger.info("La puntuación es de: " + puntuacion);
						
						GestorVentanas.hacerVisible( GameOver.class, true, 0);
						GestorVentanas.ocultar( Principal.class, 1 );
						
						miHilo.acaba();
						miHilo2.acaba();
						miHilo3.acaba();
					}
				}
				// Dormir el hilo 40 milisegundos
				try {
					Thread.sleep(200);
				} catch (Exception e) {
				}
			}
		}
		
		public Player RegistrarJugador(String nick, int puntuacion, String tiempo) 
		{
			Player a;
			
			return a = new Player (nick, puntuacion, tiempo);	
			
		}

		/** Ordena al hilo detenerse en cuanto sea posible
		 */
		public void acaba() 
		{
			sigo = false;
		}
	};
	public void growUp()
	{
		Principal.miBloque[Principal.growUp]=new bloqueJuego();
		Principal.miBloque[Principal.growUp].setPosicion(Principal.miBloque[Principal.growUp-1].getPosX()-30, Principal.miBloque[Principal.growUp-1].getPosY());
		Principal.pPrincipal.add(Principal.miBloque[Principal.growUp].getGrafico());
		Principal.miBloque[Principal.growUp].setDireccionActual(Principal.miBloque[Principal.growUp-1].miDireccionActual);
		Principal.growUp++;
	}
	
	class RandomApple implements Runnable {
		boolean sigo2 = true;
		@Override
		public void run() {
			
			Random r;
			int  apple_posX;
			int  apple_posY;
			Point P;
			
		
			
			// Bucle principal forever hasta que se pare el juego...
			while (true) 
			{
				if(Principal.miManzana == null)
				{
					P = RecursividadManzana();
					
					Principal.miManzana = new manzana(P.x,P.y);
					Principal.pPrincipal.add(Principal.miManzana);
				}
				
				
				if(Principal.miManzana.getLocation().distance(Principal.miBloque[0].getPosX(), Principal.miBloque[0].getPosY()) < 30)
				{	
					P = RecursividadManzana();
					
					Principal.miManzana.setLocation(P);
					Principal.pPrincipal.add(Principal.miManzana);
					growUp();
					puntuacion = puntuacion + 1;
				}
			
				
				// Dormir el hilo 40 milisegundos
				try 
				{	
				Thread.sleep( 33 );
				} catch (Exception e) {
				}
				
			}
			
		}
		
		public Point RecursividadManzana()
		{
			Point P;
			Random r;
			int  apple_posX;
			int  apple_posY;
			
			r= new Random();
			apple_posX = r.nextInt(Principal.pPrincipal.getWidth());
			apple_posY = r.nextInt(Principal.pPrincipal.getHeight());
			
			for(int i = 0; i<Principal.growUp; i++)
			{
				if(new Point(apple_posX, apple_posY).distance(Principal.miBloque[i].getPosX(), Principal.miBloque[i].getPosY()) < 150)
				{
					RecursividadManzana();
				} 
			}
			
			
			P = new Point(apple_posX, apple_posY);
			
			return P;
		}
		
		
		/** Ordena al hilo detenerse en cuanto sea posible
		 */
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
					
					
					if (Principal.miBloque[0].getPosX() < -JLabelBloque.TAMANYO_BLOQUE/2 || Principal.miBloque[0].getPosX()>Principal.pPrincipal.getWidth()-JLabelBloque.TAMANYO_BLOQUE/2 ) {
						// Espejo horizontal si choca en X
						
						sigo3 = false;
						miHilo3.acaba();
					
					}
					// Se comprueba tanto X como Y porque podría a la vez chocar en las dos direcciones
					else if (Principal.miBloque[0].getPosY() < -JLabelBloque.TAMANYO_BLOQUE/2 || Principal.miBloque[0].getPosY()>Principal.pPrincipal.getHeight()-JLabelBloque.TAMANYO_BLOQUE/2 ) {
						// Espejo vertical si choca en Y
						
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
