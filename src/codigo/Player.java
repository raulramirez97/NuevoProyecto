package codigo;



import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Date;

	/** Clase para gestionar usuarios. Ejemplo para ver guardado y recuperaciÃ³n desde ficheros
	 * @author 
	 */
	public class Player
	{
		
		private static final long serialVersionUID = 1L;
		private String nick;
		private int puntuacion;
		private String tiempo;
		//private Date fecha;
		private String fecha;
		
		public Player()
		{
			
		}
		
		public Player (String nick, int puntuacion, String tiempo, String fecha)
		{
			this.nick=nick;
			this.puntuacion=puntuacion;
			this.tiempo=tiempo;
			this.fecha = fecha;
		}
		

		public String getNick() {
			return nick;
		}

		public void setNick(String nick) {
			this.nick = nick;
		}

		public int getPuntuacion() {
			return puntuacion;
		}

		public void setPuntuacion(int puntuacion) {
			this.puntuacion = puntuacion;
		}

		public String getTiempo() {
			return tiempo;
		}

		public void setTiempo(String tiempo) {
			this.tiempo = tiempo;
		}
		
		public String getFecha() {
			return fecha;
		}

		public void setFecha(String fecha) {
			this.fecha = fecha;
	
		}

		@Override
		public String toString() {
			return "Usuario: " + nick + " ; Puntuación: " + puntuacion + " tiempo: " + tiempo + " fecha: " + fecha;
		}

		
	
	}