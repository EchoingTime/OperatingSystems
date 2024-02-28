package allocationScheme;

public class Partition 
{
	private int id;
	private int size;
	
	public Partition (int id, int size)
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
