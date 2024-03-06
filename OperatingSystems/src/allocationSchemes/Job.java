package allocationSchemes;

public class Job 
{
	private int memory;
	private int jobID;
	private boolean partitioned;
	
	public Job (int memory, int jobID, boolean partitioned)
	{
		this.memory = memory;
		this.jobID = jobID;
		this.partitioned = partitioned;
	}
	
	public int getMemory ()
	{
		return memory;
	}
	
	public int getJobID ()
	{
		return jobID;
	}
	
	public boolean getIsPartitioned ()
	{
		return partitioned;
	}
	
	public void isPartitioned ()
	{
		partitioned = true;
	}
}
