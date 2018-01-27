package codigo;


import javax.swing.JLabel;

public class bloqueJuego extends bloque
{
	 private JLabelBloque miGrafico;
	 
	 /**  Crea un nuevo bloque de juego
		 */
		public bloqueJuego() 
		{
			miGrafico = new JLabelBloque();
			miGrafico.setVerticalAlignment(JLabel.CENTER);
			miGrafico.setHorizontalAlignment(JLabel.CENTER);
		}
		
		/** Devuelve el JLabel gr�fico asociado al bloque de juego
		 * @return	Etiqueta gr�fica de la serpiente
		 */
		public JLabelBloque getGrafico() 
		{
			return miGrafico;
		}

		@Override
		public void setPosX(double posX) 
		{
			super.setPosX(posX);
			miGrafico.setLocation((int) posX, (int) posY);
			miGrafico.repaint();
		}

		@Override
		public void setPosY(double posY) 
		{
			super.setPosY(posY);
			miGrafico.setLocation((int) posX, (int) posY);
			miGrafico.repaint(); 
		}

		@Override
		public void setDireccionActual( double dir ) 
		{
			super.setDireccionActual(dir);
			miGrafico.setGiro(miDireccionActual);
			miGrafico.repaint();  
		}
}
