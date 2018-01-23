package COMUN;

public class clsUsuarioVacio  extends Exception
{
	private static final long serialVersionUID = -8896733625761373427L;
	private final String Message = "El nick de usuario no puede estar vacío.";
	/**
	 * Devuelve el mensaje en caso de que se dé la excepción
	 **/
	public String getMessage() 
	{
		return Message;
	}
}


