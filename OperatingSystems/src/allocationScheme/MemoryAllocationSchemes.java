package allocationScheme;
import java.util.ArrayList;
import java.util.Scanner;

public class MemoryAllocationSchemes 
{
	Partition par;
	Jobs job;
	Allocated all;
	Unallocated unall;
	private int n; // Partitions amount
	private int m; // Partitions size
	
	private ArrayList <Partition> partition; // For Dynamic Partitions - will hold partition sizes and IDs
	private ArrayList <Jobs> jobs; // Holds the amount of jobs' memory requirements and IDs
	private ArrayList <Allocated> allocated; // Allocated Partitions
	private ArrayList <Unallocated> unallocated; // Unallocated Partitions
		
	private Scanner scan;
	
	/**
	 * MemoryAllocationSchemes Constructor 
	 */
	public MemoryAllocationSchemes ()
	{
		n = 0; m = 0;
		partition = new ArrayList <Partition> ();
		jobs = new ArrayList <Jobs> ();
		allocated = new ArrayList <Allocated> ();
		unallocated = new ArrayList <Unallocated> ();
		scan = new Scanner (System.in);
	}
	
	/**
	 * run Method - Runs the program
	 */
	public void run ()
	{
		Character choice;
		
		// Fixed Scheme 
		
		fixedScheme ();
		assigningJobs ();
		allocationMethodsFixed ();
		
		// Dynamic Scheme 
		
		dynamicScheme ();
		System.out.printf("%n===========================================%nKeep previous jobs or create new ones? | Choice: Y or N ");
		choice = scan.next().charAt(0);
		System.out.printf("%n===========================================%n");

		if (choice.equals('Y') || choice.equals('y'))
		{
			jobs.clear();
			assigningJobs ();
		}
		else if ((choice.equals('N') || choice.equals('n')))
		{
			// Continue
		}
		else
		{
			System.out.printf("%n===========================================%nIncorrect character: Ending program%n===========================================%n");
			System.exit(0);
		}
	}
	
	/**
	 * fixedScheme Method - Defines the fixed partition sizes block via user input
	 */
	public void fixedScheme ()
	{
		System.out.printf("===========================================%nConstructing a Fixed Memory Scheme%n===========================================%n");
		
		System.out.printf("%nInput amount of partitions available in your fixed scheme: ");
		n = scan.nextInt();
		
		System.out.printf("%nInput the max size for each partition: ");
		m = scan.nextInt();
	}
	
	/**
	 * dynamicScheme Method - Defines the dynamic partition sizes via user input
	 */
	public void dynamicScheme ()
	{
		boolean run;
		int size, i;
		
		run = true;
		size = 0;
		i = 1;
		
		System.out.printf("Constructing a Dynamic Memory Scheme%n===========================================%n");
		
		while (run == true)
		{
			System.out.printf("%nInput the size of Partition %s |NOTE: -1 to stop|: ", partition.size());
			
			size = scan.nextInt();
			
			if (size == -1)
			{
				run = false;
			}
			else
			{
				par = new Partition (i, size);
				partition.add(par);
			}
		}
	}
	
	/**
	 * assigningJobs Method - Allows user to assign jobs with specific memory requirements
	 */
	public void assigningJobs ()
	{
		boolean run;
		int size, i;
		
		run = true;
		size = 0;
		i = 1;
		
		System.out.printf("%n===========================================%nAssigning Jobs%n===========================================%n");

		
		while (run == true)
		{
			System.out.printf("%nInput the memory requirement of Job %s |NOTE: -1 to stop|: ", jobs.size() + 1);
			
			size = scan.nextInt();
			
			if (size == -1)
			{
				run = false;
			}
			else
			{
				job = new Jobs (i, size);
				jobs.add(job);
				i++;
			}

		}
	}
	
	/**
	 * allocationMethodsFixed Method - Containing allocation methods First-Fit, Best-Fit, and Worst-Fit, which will be 0, 1, and 2 respectively.
	 * This is for the Fixed Allocation Scheme 
	 */
	public void allocationMethodsFixed ()
	{
		System.out.printf("%n===========================================%nPerforming Allocations for Fixed Scheme%n===========================================%n");

		// Performing the fixed partition allocation on the methods
		for (int i = 0; i < 3; i++)
		{
			unallocated ();
			
			if (i == 0)
			{
				System.out.printf("%n*------- First-Fit -------*%n");
				firstFit ();
			}
			else if (i == 1)
			{
				System.out.printf("%n*------- Best-Fit -------*%n");
			}
			else 
			{
				System.out.printf("%n*------- Worst-Fit -------*%n");
			}
			
			fragmentation ();
			System.out.printf("%n===========================================%n");
		}
	}
	
	/**
	 * unallocated Method - Restarts unallocated
	 */
	private void unallocated ()
	{
		unallocated.clear();
		allocated.clear();
	}
	
	/**
	 * firstFit Method - Whatever jobs fit first will be allocated to a partition or until max partition size is reached
	 */
	private void firstFit ()
	{
		int x, y, allocateTotal;
		x = 1;
		y = 1;
		
		allocateTotal = 0;
		
		for (int i = 0; i < jobs.size(); i++)
		{
			if (jobs.get(i).getSize() <= m && allocateTotal != n) // If the job is less than or equal to the partition size requirement
			{
				all = new Allocated (x, jobs.get(i).getSize(), jobs.get(i).getID());
				allocated.add(all);
				allocateTotal++;
				x++;
			}
			else
			{
				unall = new Unallocated (y, jobs.get(i).getSize(), jobs.get(i).getID());
				unallocated.add(unall);
				y++;
			}
		}
	}
	
	/**
	 * fragmentation Method - Calculates the total amount of fragmentation, how much memory was wasted
	 */
	private void fragmentation ()
	{
		int total;
		total = 0;
		
		// Calculating sum of fragmentation
		for (int i = 0; i < unallocated.size(); i++)
		{
			total = total + unallocated.get(i).getSize();
		}
		
		// Printing allocated partitions 
		for (int i = 0; i < allocated.size(); i++)
		{
			System.out.printf("%n|ALLOCATED| Partition %s | Size %s of %s %n", allocated.get(i).getID(), allocated.get(i).getSize(), allocated.get(i).getJob());
		}
		
		// Printing unallocated partitions 
		for (int i = 0; i < unallocated.size(); i++)
		{
			System.out.printf("%n|UNALLOCATED| Partition %s | Size %s of %s %n", unallocated.get(i).getID(), unallocated.get(i).getSize(), unallocated.get(i).getJob());
		}
		
		System.out.printf("%nFRAGMENTATION | %s%n", total);
	}
}
