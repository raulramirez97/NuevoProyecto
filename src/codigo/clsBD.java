package codigo;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JOptionPane;


public class clsBD
{
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet rs=null;
	
	public static Connection initBD ( String nombreBD ) 
	{		
		try
		{
		    Class.forName("org.sqlite.JDBC");
		    connection = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			statement = connection.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
		    return connection;
		} 
		
		catch (ClassNotFoundException | SQLException e) 
		{
			JOptionPane.showMessageDialog( null, "Error de conexiÃ³n!! No se ha podido conectar con " + nombreBD , "ERROR", JOptionPane.ERROR_MESSAGE );
			System.out.println( "Error de conexiÃ³n!! No se ha podido conectar con " + nombreBD );
			return null;
		}
	}
	
	public static void close() 
	{
		try 
		{
			statement.close();
			connection.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConnection() 
	{
		return connection;
	}
	
	
	public static Statement getStatement() 
	{
		return statement;
	}
	
	
	
	
	
	public static void crearTablaEstadisticas() 
	{
		if (statement==null) return;
		try
		{ 
			statement.executeUpdate("create table estadisticas " +
					"("
					+ " nombre string,"
					+ " puntuacion int,"
					+ " tiempo string, "
					+ " fecha string" 
					+ ")");
		} 
		
		catch (SQLException e) 
		{
			  
		}
	}
	
	public static boolean InsertEstadisticas (String nombre, int puntuacion, String tiempo, String fecha)
	{
				try 
				{					
					String sentSQL = "insert into estadisticas values(" +
							"'" + nombre + "', " +
							"'" + puntuacion + "', " +
							"'" + tiempo + "', " +
							"'" + fecha + "')";
					
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false; 
					return true;
				} 
				catch (SQLException e) 
				{
					return false;
				}
	}
	
	public static boolean UpdateEstadisticas (String nombre, int puntuacion, String tiempo, String fecha)
	{
		try 
		{
			String sentSQL = "update estadisticas set " +
					 "nombre = '"+ nombre + "'" +
					 "puntuacion = '"+ puntuacion + "'" +
					 "tiempo = '"+ tiempo + "'" +
					 "fecha = '"+ fecha + "'" +
					"where fecha= '" + fecha + "'";
			
			int val = statement.executeUpdate( sentSQL );
			if (val!=1) return false;  
			return true;
		} 
		
		catch (SQLException e) 
		{
			return false;
		}
	}
	
	public static ArrayList<Player> LeerEstadisticas()
	{			
		ArrayList<Player> retorno = new ArrayList<Player>(); 
		 	try 
			{
				
				String sentSQL = "select * from estadisticas";
				rs=statement.executeQuery( sentSQL );
				
				if(rs!=null)
				{
					while (rs.next())
					{
						String nombre = rs.getString("nombre"); 
						int puntuacion = rs.getInt("puntuacion");
						String tiempoFinal = rs.getString("tiempo");
						String fecha = rs.getString("fecha");
						Player a = new Player (nombre, puntuacion, tiempoFinal, fecha);
						retorno.add(a); 
					}
					rs.close();
				}
				
				return retorno;
			}
			catch (SQLException e) 
			{
				return retorno; 
			}	
	}
	public static Player LeerPlayer(String fecha)
	{			
		Player retorno = null; 
		 	try 
			{
				
				String sentSQL = "select * from estadisticas where fecha = '" + fecha + "'";
				rs=statement.executeQuery( sentSQL );
				
				if(rs!=null)
				{
					
						String nombre = rs.getString("nombre"); 
						int puntuacion = rs.getInt("puntuacion");
						String tiempoFinal = rs.getString("tiempo");
						String fecha1 = rs.getString("fecha");
						retorno = new Player (nombre, puntuacion, tiempoFinal, fecha1);
						
				}
					
				
				return retorno;
			}
			catch (SQLException e) 
			{
				return retorno; 
			}	
	}
	
	public static boolean DropTable()
	{
		try
		{
			String sentSQL = "drop table estadisticas";
			int val = statement.executeUpdate( sentSQL );
			return true;
		} 
		catch (SQLException e) 
		{
			return false;
		}
	}
	

	public static boolean BorrarFila (String fecha)
	{
		try 
				{
					
					String sentSQL = "DELETE FROM estadisticas WHERE fecha = "+ fecha;
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false;   
					return true;
				}
				catch (SQLException e) 
				{
					return false;
				}
	}
}
