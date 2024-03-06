package allocationSchemes;

public class Partition 
{
	private int memorySize;
	private int partitionID;
	
	public Partition (int memorySize, int partitionID)
	{
		this.memorySize = memorySize; 
		this.partitionID = partitionID;
	}
	
	public int getMemory ()
	{
		return memorySize;
	}
	
	public int getPartitionID ()
	{
		return partitionID;
	}
}
