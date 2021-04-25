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
	
	

	private int[][] storeAllocatedArray(int[][] arr)
	{
		for(int i=0 ; i<this.numOfProcesses ; i++)
		{
			for(int j=0 ; j<this.numOfResources ; j++)
			{
				arr[i][j] = this.allocatedInstance[i][j];
			}
		}
		return arr;
	}
	
	
	
	
	@Override
	public void doAlgorithm() {
		
		ArrayList<Integer> storedAvailableInstance = new ArrayList<Integer>(); /** The work vector **/
		for(int i=0 ; i<this.numOfResources ; i++)
		{
			storedAvailableInstance.add(this.availableInstance.get(i));
		}
		
		int[][] storedMaximumDemand = new int[this.numOfProcesses][this.numOfResources];
		for(int i=0 ; i<this.numOfProcesses ; i++)
		{
			for(int j=0 ; j<this.numOfResources ; j++)
			{
				storedMaximumDemand[i][j] = this.maximumDemand[i][j];
			}
		}
		
		int[][] storedAllocatedInstance = new int[this.numOfProcesses][this.numOfResources];
		storedAllocatedInstance = storeAllocatedArray(storedAllocatedInstance);
		
		int[][] storedRemaining = new int[this.numOfProcesses][this.numOfResources];
		for(int i=0 ; i<this.numOfProcesses ; i++)
		{
			for(int j=0 ; j<this.numOfResources ; j++)
			{
				storedRemaining[i][j] = this.allocatedInstance[i][j];
			}
		}
		/////////////////////////////////
		initialFinishVectorToFalse();
		boolean passed = false; 
		calcRemainingNeed();
		int state = 0;
		while(state < this.numOfProcesses)
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
						if(storedRemaining[i][j] <= storedAvailableInstance.get(j))
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
							int newInstance = storedAvailableInstance.get(k);
							newInstance += storedAllocatedInstance[i][k];
							storedAvailableInstance.set(k , newInstance);
						}
						finish[i] = true;
						System.out.println("Process-" + i + " Can take all its requests in state-" + state);
						for(int n = 0 ; n<this.numOfResources ; n++)
						{
							/** When the maximumDemand is equal to zero
							 this means that this process has released all resources **/
							storedMaximumDemand[i][n] = 0;
						}
						/*** Print all the data structures in this state ****/
						System.out.println("Maximum Demand for all processes in state-"
								+ state);
						for(int n1 = 0 ; n1<this.numOfProcesses ; n1++)
						{
							System.out.print("P-" + n1 + ": ");
							for(int n2 = 0 ; n2<this.numOfResources ; n2++)
							{
								System.out.print(storedMaximumDemand[n1][n2] + "  ");
							}
							System.out.println();
						}
						System.out.println("******************************");
						System.out.println("Available Instances for all Resources in state-"
								+ state);
						for(int n=0 ; n<this.numOfResources ; n++)
						{
							System.out.println("R-" + n + ": " + storedAvailableInstance.get(n));
						}
					}
					else
					{
						System.out.println("Process-" + i + " Cannot take all its requests in state-" + state);
					}
					
				}
			}
			if(state == this.numOfProcesses)
			{
				break;
			}
			state++;
		}
		boolean checkSafetyState = checkWhetherTheProcessesFinish();
		if(checkSafetyState == true)
		{
			System.out.print("The system will approve all requests by the following sequence:");
			printTheSequenceOfProcesses();
		}
		else
		{
			System.out.print("The system denied all requests , since it is in the unsafe state");
		}
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
			System.out.println("The system will not grant this request , since the resource request exceed the remainingNeed");
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
				System.out.println("The system will not grant this request , since the resource request exceed the availabelResource");
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
					int calcNewRemain = this.remainingNeed[numOfProcess][i] - 
							request.get(i);
					this.remainingNeed[numOfProcess][i] = calcNewRemain;

				}
				doAlgorithm();
				boolean checkIfSafetyState = checkWhetherTheProcessesFinish();
				if(checkIfSafetyState == true)
				{
					System.out.println("The system will grant this request");
					for(int i=0 ; i<this.numOfResources ; i++)
					{
						int calcNewAvailable = this.availableInstance.get(i) + 
								request.get(i);
						this.availableInstance.set(i , calcNewAvailable);
						
						////////////////////////////////////////
						int calcNewAllocation = this.allocatedInstance[numOfProcess][i] -
								request.get(i);
						this.allocatedInstance[numOfProcess][i] = calcNewAllocation;
						
						//////////////////////////////////////////
						int calcNewRemain = this.remainingNeed[numOfProcess][i] +
								request.get(i);
						this.remainingNeed[numOfProcess][i] = calcNewRemain;
						
					}
					return;
				}
				else
				{
					System.out.println("The system will not grant this request");
					for(int i=0 ; i<this.numOfResources ; i++)
					{
						int calcNewAvailable = this.availableInstance.get(i) + 
								request.get(i);
						this.availableInstance.set(i , calcNewAvailable);
						
						////////////////////////////////////////
						int calcNewAllocation = this.allocatedInstance[numOfProcess][i] -
								request.get(i);
						this.allocatedInstance[numOfProcess][i] = calcNewAllocation;
						
						//////////////////////////////////////////
						int calcNewRemain = this.remainingNeed[numOfProcess][i] +
								request.get(i);
						this.remainingNeed[numOfProcess][i] = calcNewRemain;
						
					}
					return;
				}
			}
			return;
		}
	}
	
	
	public void releaseResource(ArrayList<Integer> release , int numOfProcess)
	{
		ArrayList<Boolean> checkWhetherResourceLessThanAllocated = new ArrayList<Boolean>();
		
		for(int i=0 ; i<this.numOfResources ; i++)
		{
			if(release.get(i) <= this.allocatedInstance[numOfProcess][i])
			{
				checkWhetherResourceLessThanAllocated.add(true);
			}
			else
			{
				checkWhetherResourceLessThanAllocated.add(false);
			}
		}
		boolean pass = true;
		for(int i=0 ; i<this.numOfResources ; i++)
		{
			if(checkWhetherResourceLessThanAllocated.get(i) == false)
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
				int newAllocated = 0;
				newAllocated = this.allocatedInstance[numOfProcess][i] - 
						release.get(i);
				this.allocatedInstance[numOfProcess][i] = newAllocated;
			}
			System.out.println("The releases resources are less than the allocated resource");
			System.out.println("The new allocated resource for this process is :");
			for(int i=0 ; i<this.numOfResources ; i++)
			{
				System.out.print("allocatedResource-" + i + " : " +
			this.allocatedInstance[numOfProcess][i]);
				System.out.println();
			}
			return;
		}
	}
	
}
