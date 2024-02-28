package allocationScheme;

public class Unallocated 
{
	private int id;
	private int size;
	private String job;
	
	public Unallocated (int id, int size, int job)
	{
		this.id = id;
		this.size = size;
		this.job = "Job " + job;
	}
	
	public int getID ()
	{
		return id;
	}
	
	public int getSize ()
	{
		return size;
	}
	
	public String getJob ()
	{
		return job;
	}
}
