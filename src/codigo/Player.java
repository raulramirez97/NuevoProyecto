package codigo;



import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		private Date fecha;
		
		
		public Player()
		{
			
		}
		
		public Player (String nick, int puntuacion, String tiempo)
		{
			this.nick=nick;
			this.puntuacion=puntuacion;
			this.tiempo=tiempo;
			this.fecha = new Date();
		}
		
		public Player (String nick, int puntuacion, String tiempo, String fecha)
		{
			this.nick=nick;
			this.puntuacion=puntuacion;
			this.tiempo=tiempo;
			SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
			try {
				this.fecha = formato.parse(fecha);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
		public Date getFecha() {
			return fecha;
		}

		public void setFecha(Date fecha) {
			this.fecha = fecha;
	
		}

		@Override
		public String toString() {
			return "Usuario: " + nick + " ; Puntuación: " + puntuacion + " tiempo: " + tiempo + " fecha: " + fecha;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Player other = (Player) obj;
			if (fecha == null) {
				if (other.fecha != null)
					return false;
			} else if (!fecha.equals(other.fecha))
				return false;
			return true;
		}

		
		
	
	}