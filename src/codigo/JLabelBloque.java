package codigo;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.AreaAveragingScaleFilter;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

	/** Clase para visualizar un coche en Swing como un JLabel,
	 *  con un gráfico específico de coche
	 * @author
	 */
	public class JLabelBloque extends JLabel 
	{
		private static final long serialVersionUID = 1L;  
		public static final int TAMANYO_BLOQUE= 30; 	
		ImageIcon icono;
		
		
	
		/** Construye y devuelve el JLabel del coche con su gráfico y tamaño
		 */
		public JLabelBloque() {
	
			
			try {
				
				icono= new ImageIcon(JLabelBloque.class.getResource( "Image.jpg" ).toURI().toURL() );
				
			} catch (Exception e) {
				System.err.println( "Error en carga de recurso: coche.png no encontrado" );
				e.printStackTrace();
			}
			setSize(TAMANYO_BLOQUE, TAMANYO_BLOQUE);
			
			setBorder( BorderFactory.createLineBorder( Color.CYAN, 1 ));
		}
		
		
		private double miGiro = Math.PI/2;
		/** Cambia el giro del JLabel
		 * @param gradosGiro	Grados a los que tiene que "apuntar" el coche,
		 * 						considerados con el 0 en el eje OX positivo,
		 * 						positivo en sentido antihorario, negativo horario.
		 */
		public void setGiro( double gradosGiro ) 
		{
			
			miGiro = gradosGiro/180*Math.PI;
			
			miGiro = -miGiro;  
			
			miGiro = miGiro + Math.PI/2; 
		}
		

		
		@Override
			protected void paintComponent(Graphics g) {
			Icon nuevoIcono = new ImageIcon(icono.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			setIcon(nuevoIcono);
			
			super.paintComponent(g);

	}
	}

	
	

