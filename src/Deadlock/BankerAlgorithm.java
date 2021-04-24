package Deadlock;

import java.util.ArrayList;
import java.util.Iterator;

public class BankerAlgorithm implements Strategies{

	private ArrayList<Integer> availableInstance; /** The work vector **/
	private int[][] maximumDemand;
	private int[][] allocatedInstance;
	private int[][] remainingNeed;
	private boolean[] finish; 
	private ArrayList<Integer> arrangeProcesses;/** To know which process has
	finished firstly **/
	
	private int numOfProcesses;
	private int numOfResources;
	
	
	

	public BankerAlgorithm(int numOfProcesses , int numOfResources) {
		super();
		this.availableInstance = new ArrayList<Integer>();
		this.arrangeProcesses = new ArrayList<Integer>();
		this.numOfProcesses = numOfProcesses;
		this.numOfResources = numOfResources;
		this.maximumDemand = new int[this.numOfProcesses][this.numOfResources];
		this.allocatedInstance = new int[this.numOfProcesses][this.numOfResources];
		this.remainingNeed = new int[this.numOfProcesses][this.numOfResources];
		this.finish = new boolean[this.numOfProcesses];
	}

	
	private void initialFinishVectorToFalse()
	{
		for(int i=0 ; i<this.numOfProcesses ; i++)
		{
			this.finish[i] = false;
		}
	}
	
	public void addAvailableInstance(int available)
	{
		this.availableInstance.add(available); 
	}

	/*public void addInstanceForSpecificResource(int instance , int i) {
		this.availableInstance[i] = instance;
	}
	*/
	public void addMaxDemand(int maxDemand , int whichRow , int whichCol)
	{
		this.maximumDemand[whichRow][whichCol] = maxDemand;
	}
	
	public void addAllocatedInstance(int allocated , int whichRow , int whichCol)
	{
		this.allocatedInstance[whichRow][whichCol] = allocated;
	}
	
	private void calcRemainingNeed()
	{
		for(int i=0 ; i<numOfProcesses ; i++)
		{
			for(int j=0 ; j<numOfResources ; j++)
			{
				int remain = 0;
				remain = this.maximumDemand[i][j] - this.allocatedInstance[i][j];
				this.remainingNeed[i][j] = remain;
			}
		}
	}

	private boolean checkWhetherTheProcessesFinish()
	{
		for(int i=0 ; i<this.numOfProcesses ;  i++)
		{
			if(this.finish[i] == false)
				return false;
		}
		return true;
	}
	
	/** This function to print the processes after finishing their jobs **/
	private void printTheSequenceOfProcesses()
	{
		System.out.print("<");
		for(int i=0 ; i<this.numOfProcesses ; i++)
		{
			System.out.print("P" + this.arrangeProcesses.get(i));
			System.out.print("   "); 
		}
		System.out.print(">");
	}
	
	private boolean checkIfAllProcessesFinishInCurrentState()
	{
		int counter = 0;
		for(int i = 0 ; i<this.numOfProcesses ; i++)
		{
			if(this.finish[i] == false)
			{
				counter++;
			}
		}
		
		if(counter == this.numOfProcesses)
		{
			return true;/** All processes do not terminate **/
		}
		return false;
	}
	
	@Override
	public void doAlgorithm() {
		
		initialFinishVectorToFalse();
		boolean passed = false; 
		calcRemainingNeed();
		int state = 0;
		boolean statePassed = false;
		do
		{
			ArrayList<Boolean> checkForResource;
			System.out.println("State-" + state);
			System.out.println("***************");
			for(int i = 0 ; i<this.numOfProcesses ; i++)
			{
				if(this.finish[i] == false)
				{
					checkForResource = new ArrayList<>();
					boolean pass = true;
					for(int j=0 ; j<this.numOfResources ; j++)
					{	
						if(this.remainingNeed[i][j] <= this.availableInstance.get(j))
						{
							checkForResource.add(true);
						}
						else
						{
							checkForResource.add(false);
						}
					}
					for(int j=0 ; j<this.numOfResources ; j++)
					{
						if(checkForResource.get(j) == false)
						{
							pass = false;
						}
					}
					if(pass == true)
					{
						this.arrangeProcesses.add(i);
						for(int k=0 ; k<this.numOfResources ; k++)
						{
							int newInstance = this.availableInstance.get(k);
							newInstance += this.allocatedInstance[i][k];
							this.availableInstance.set(k , newInstance);
						}
						finish[i] = true;
						System.out.println("Process-" + i + " Can take all its requests in state-" + state);
						for(int n = 0 ; n<this.numOfResources ; n++)
						{
							/** When the maximumDemand is equal to zero
							 this means that this process has released all resources **/
							this.maximumDemand[i][n] = 0;
						}
					}
					else
					{
						System.out.println("Process-" + i + " Cannot take all its requests in state-" + state);
					}
					/*** Print all the data structures in this state ****/
					System.out.println("Maximum Demand for all processes in state-"
							+ state);
					for(int n1 = 0 ; n1<this.numOfProcesses ; n1++)
					{
						System.out.print("P-" + n1 + ": ");
						for(int n2 = 0 ; n2<this.numOfResources ; n2++)
						{
							System.out.print(this.maximumDemand[n1][n2] + "  ");
						}
						System.out.println();
					}
					System.out.println("******************************");
					System.out.println("Available Instances for all Resources in state-"
							+ state);
					for(int n=0 ; n<this.numOfResources ; n++)
					{
						System.out.println("R-" + n + ": " + this.availableInstance.get(n));
					}
				}
			}
			state++;
			passed = checkWhetherTheProcessesFinish();
			statePassed = checkIfAllProcessesFinishInCurrentState();
			if(statePassed == true)
			{
				System.out.println("The system denies all requests");
				return;
			}
		}while(passed == false);
		
		System.out.print("The system will approve all requests by the following sequence:");
		printTheSequenceOfProcesses();
	}
	
	
	public void requestResources(ArrayList<Integer> request , int numOfProcess /** zero based **/)
	{
		ArrayList<Boolean> checkWhetherResourceLessThanRemain = new ArrayList<Boolean>();
		ArrayList<Boolean> checkWhetherResourceLessThanAvailable = new ArrayList<Boolean>();
		for(int i=0 ; i<this.numOfResources ; i++)
		{
			if(request.get(i) <= this.remainingNeed[numOfProcess][i])
			{
				checkWhetherResourceLessThanRemain.add(true);
			}
			else
			{
				checkWhetherResourceLessThanRemain.add(false);
			}
		}
		boolean pass = true;
		for(int i=0 ; i<this.numOfResources ; i++)
		{
			if(checkWhetherResourceLessThanRemain.get(i) == false)
			{
				pass = false;
			}
		}
		if(pass == false)
		{
			System.out.println("The process has exceeded its maximum claim");
			return;
		}
		else
		{
			for(int i=0 ; i<this.numOfResources ; i++)
			{
				if(request.get(i) <= this.availableInstance.get(i))
				{
					checkWhetherResourceLessThanAvailable.add(true);
				}
				else
				{
					checkWhetherResourceLessThanAvailable.add(false);
				}
			}
			boolean pass2 = true;
			for(int i=0 ; i<this.numOfResources ; i++)
			{
				if(checkWhetherResourceLessThanAvailable.get(i) == false)
				{
					pass2 = false;
				}
			}
			
			if(pass2 == false)
			{
				System.out.println("The process must wait , since resoures are not available");
				
			}
			else
			{
				for(int i=0 ; i<this.numOfResources ; i++)
				{
					int calcNewAvailable = this.availableInstance.get(i) - 
							request.get(i);
					this.availableInstance.set(i , calcNewAvailable);
					
					////////////////////////////////////////
					int calcNewAllocation = this.allocatedInstance[numOfProcess][i] + 
							request.get(i);
					this.allocatedInstance[numOfProcess][i] = calcNewAllocation;
					
					//////////////////////////////////////////
					int calcNewRemain = this.remainingNeed[numOfProcess][i] + 
							request.get(i);
					this.remainingNeed[numOfProcess][i] = calcNewRemain;
					
					
				}
				System.out.println("The process will allocate its requests , since the system will be in the safe state");
				return;
			}
			return;
		}
	}
	
}
