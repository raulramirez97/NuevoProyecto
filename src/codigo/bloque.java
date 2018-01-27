package codigo;


public class bloque 

{
	protected double miDireccionActual; 
	protected double posX; 
	protected double posY; 
	
	public bloque()
	{
		miDireccionActual=0.0;	
	} 
	
	public double getDireccionActual() 
	{
		return miDireccionActual;
	}

	public void setDireccionActual( double dir ) 
	{
		miDireccionActual = dir;
	}
		
	public double getPosX() 
	{
		return posX;
	}

	public double getPosY() 
	{
		return posY;
	}
	
	public void setPosicion( double posX, double posY ) 
	{
		setPosX( posX );
		setPosY( posY );
	}
	
	public void getPosicion( )
	{
		getPosX( );
		getPosY( );
	}
	
	public void setPosX( double posX ) 
	{
		this.posX = posX; 
	}
	
	public void setPosY( double posY ) 
	{
		this.posY = posY; 
	}
	
	public void gira( double giro ) 
	{
		setDireccionActual(giro );
	}
	
	public void mueveX( ) 
	{
		if(miDireccionActual==0 || miDireccionActual==180)
		{
			setPosX(posX+(30*Math.cos(miDireccionActual/180.0*Math.PI)));
		}
	}
	
	public void mueveY( ) 
	{	
		if(miDireccionActual==270 || miDireccionActual==90)
		{
			setPosY(posY+(30*-Math.sin(miDireccionActual/180.0*Math.PI)));
		}
	}
}
