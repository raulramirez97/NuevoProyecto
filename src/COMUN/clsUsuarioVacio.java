package COMUN;

public class clsUsuarioVacio  extends Exception
{
	private static final long serialVersionUID = -8896733625761373427L;
	private final String Message = "El nick de usuario no puede estar vac�o.";
	/**
	 * Devuelve el mensaje en caso de que se d� la excepci�n
	 **/
	public String getMessage() 
	{
		return Message;
	}
}


