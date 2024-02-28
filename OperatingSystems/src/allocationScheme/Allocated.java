package allocationScheme;

public class Allocated 
{
	private int id;
	private int size;
	private String jobName;
	
	public Allocated (int id, int size, int jobName)
	{
		this.id = id;
		this.size = size;
		this.jobName = "Job " + jobName;
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
		return jobName;
	}
}
