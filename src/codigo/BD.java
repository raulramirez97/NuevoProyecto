package codigo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BD 
{
	
	private static Connection connection;
	private static Statement statement;
	public static void conexion() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:circuitos.db");
			statement = connection.createStatement();
			try {
				statement.executeUpdate("create table estadisticas (nombre string, puntuacion integer, TiempoFinal string)");
			} catch (SQLException e) {
				if (!e.getMessage().equals("table estadisticas already exists"))  // Este error sí es correcto si la tabla ya existe
					e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// Versión directa (suficiente para examen)
	public static void insertEstadisticas(String nombre, int puntuacion, String tiempofinal) 
	{
		
		String sent = "insert into estadisticas values('" + nombre + "', " + puntuacion + ", '" + tiempofinal + "')";
		try {
			statement.executeUpdate(sent);
		} catch (SQLException e) {
			System.out.println( "ERROR EN SENTENCIA SQL: " + sent);
			e.printStackTrace();
		}
		
	}
	
	
	public static void finConexion() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}