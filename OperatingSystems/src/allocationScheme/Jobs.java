package allocationScheme;

public class Jobs 
{
	private int id;
	private int size;
	
	public Jobs (int id, int size)
	{
		this.id = id;
		this.size = size;
	}
	
	public int getID ()
	{
		return id;
	}
	
	public int getSize ()
	{
		return size;
	}
}
