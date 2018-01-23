package codigo;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import javax.swing.JOptionPane;


public class clsBD
{
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet rs=null;
	/** Inicializa una BD SQLITE y devuelve una conexiÃ³n con ella. Debe llamarse a este 
	 * mÃ©todo antes que ningÃºn otro, y debe devolver no null para poder seguir trabajando con la BD.
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	ConexiÃ³n con la base de datos indicada. Si hay algÃºn error, se devuelve null
	 */
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
	
	/** Cierra la conexiÃ³n con la Base de Datos
	 */
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
	
	/** Devuelve la conexiÃ³n si ha sido establecida previamente (#initBD()).
	 * @return	ConexiÃ³n con la BD, null si no se ha establecido correctamente.
	 */
	public static Connection getConnection() 
	{
		return connection;
	}
	
	/** Devuelve una sentencia para trabajar con la BD,
	 * si la conexiÃ³n si ha sido establecida previamente (#initBD()).
	 * @return	Sentencia de trabajo con la BD, null si no se ha establecido correctamente.
	 */
	public static Statement getStatement() 
	{
		return statement;
	}
	
	
	//Crear tablas 
	
	
	public static void crearTablaXXX() 
	{
		if (statement==null) return;
		try
		{ 
			statement.executeUpdate("create table estadisticas " +
					"("
					+ " nombre string,"
					+ " puntuacion int,"
					+ " tiempo string, "
					+ " fecha string" //Si quitamos esto, quitar la coma del anterior
					+ ")");
		} 
		
		catch (SQLException e) 
		{
			// Si hay excepciÃ³n es que la tabla ya existÃ­a (lo cual es correcto). No la creamos y listo  
		}
	}
	
	public static boolean InsertEstadisticas (String nombre, int puntuacion, String tiempo, String fecha)
	{
				try 
				{					
					String sentSQL = "insert into XXX values(" +
							"'" + nombre + "', " +
							"'" + puntuacion + "', " +
							"'" + tiempo + "', " +
							"'" + fecha + "')";
					
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false; //Error, no se ha insertado
					return true;
				} 
				catch (SQLException e) 
				{
					return false;
				}
	}
	
	public static boolean UpdateXXX (String nombre, int puntuacion, String tiempo, String fecha)
	{
		try 
		{
			String sentSQL = "update estadisticas set " +
					 "nombre = '"+ nombre + "'" +
					 "puntuacion = '"+ puntuacion + "'" +
					 "tiempo = '"+ tiempo + "'" +
					 "fecha = '"+ fecha + "'" +
					"where nombre= '" + nombre + "'";//??????????????????????????????????????????'
			
			int val = statement.executeUpdate( sentSQL );
			if (val!=1) return false;  
			return true;
		} 
		
		catch (SQLException e) 
		{
			return false;
		}
	}
	
	public static XXX LeerXXX( Atributo identificativo)
	{			
		int retorno = 0; //Aquí tendréis que hacer una variable del tipo de lo que vayáis a devolver (si fuera precio, double; si fuera toda la fila, ArrayList etc.) 
		// Para inicializarlo, hay que inicializarlo con un valor que sepamos que si está en la tabla nunca tendrá (por ejemplo, precio = 0, ArrayList <> = null etc.)
		 	try 
			{
				//Select * si necesitáis todo lo de la tabala (el String, el boolean y el int) y si no, poner el nombre de la columna después del select (pe. select precio from ...)
				String sentSQL = "select XXX from estadisticas where XXX = '"+XXX+"'";
				rs=statement.executeQuery( sentSQL );
				
				if(rs!=null)
				{
					retorno = rs.getInt("XXX"); //Si es una sola variable, rs.get<Tipo de la variable de retorno>
					retorno.add(rs.getInt("XXX")); //Si es un ArrayList, hacemos un get de cada uno de sus columnas y hacemos add de cada uno de ellos (tantos add como columnas haya)
				}
	
				return retorno;
			}
			catch (SQLException e) 
			{
				return retorno; //Si devuelve 0 es que ha entrado aquí
			}	
	}
	//Este no creo que salga nunca, pero por si acaso
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
	
//este podría llegar a entrar, aunque es menos probable que update. Si en algún momento veis que update no va bien, podéis hacer primero delete y luego insert otra vez.
	public static boolean BorrarFila (Atributo que determine qué fila se debe borrar)
	{
		try 
				{
					//Borrando
					int codArchivo=(Integer)ident;
					String sentSQL = "DELETE FROM estadisticas WHERE XXX = "+XXX;
					int val = statement.executeUpdate( sentSQL );
					if (val!=1) return false;   //Error de borrado
					return true;//Borrado correctamente
				}
				catch (SQLException e) 
				{
					logger.log( Level.WARNING, e.getMessage(), e );
					return false;
				}
	}
}