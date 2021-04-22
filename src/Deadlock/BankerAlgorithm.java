package Deadlock;

import java.util.ArrayList;

public class BankerAlgorithm implements Strategies{

	private Integer[] availableInstance; /** The work vector **/
	private Integer[][] maximumDemand;
	private Integer[][] allocatedInstance;
	private Integer[][] remainingNeed;
	private boolean[] finish; 
	private ArrayList<Integer> arrangeProcesses;/** To know which process has
	finished firstly **/
	
	private int numOfProcesses;
	private int numOfResources;
	
	
	

	public BankerAlgorithm(int numOfProcesses , int numOfResources) {
		super();
		this.availableInstance = new Integer[this.numOfResources];
		this.numOfProcesses = numOfProcesses;
		this.numOfResources = numOfResources;
		this.maximumDemand = new Integer[this.numOfProcesses][this.numOfResources];
		this.allocatedInstance = new Integer[this.numOfProcesses][this.numOfResources];
		this.remainingNeed = new Integer[this.numOfProcesses][this.numOfResources];
		this.finish = new boolean[this.numOfProcesses];
	}

	
	private void initialFinishVectorToFalse()
	{
		for(int i=0 ; i<this.numOfProcesses ; i++)
		{
			this.finish[i] = false;
		}
	}

	public void addInstanceForSpecificResource(Integer instance , int i) {
		this.availableInstance[i] = instance;
	}
	
	public void addMaxDemand(Integer maxDemand , int whichRow , int whichCol)
	{
		this.maximumDemand[whichRow][whichCol] = maxDemand;
	}
	
	public void addAllocatedInstance(Integer allocated , int whichRow , int whichCol)
	{
		this.allocatedInstance[whichRow][whichCol] = allocated;
	}
	
	private void calcRemainingNeed()
	{
		for(int i=0 ; i<numOfProcesses ; i++)
		{
			for(int j=0 ; j<numOfResources ; j++)
			{
				this.remainingNeed[i][j] = this.maximumDemand[i][j] -
						this.allocatedInstance[i][j];
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
	
	@Override
	public void doAlgorithm() {
		
		initialFinishVectorToFalse();
		boolean passed = false; 
		calcRemainingNeed();
		do
		{
			ArrayList<Boolean> checkForResource;
			for(int i = 0 ; i<this.numOfProcesses ; i++)
			{
				if(this.finish[i] == false)
				{
					checkForResource = new ArrayList<>();
					boolean pass = true;
					for(int j=0 ; j<this.numOfResources ; j++)
					{	
						if(this.remainingNeed[i][j] <= this.availableInstance[j])
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
							this.availableInstance[k] += this.allocatedInstance[i][k]; 
						}
						finish[i] = true;
					}
				}
			}
			passed = checkWhetherTheProcessesFinish();
		}while(passed == false);
	}

}
