package Deadlock;

import java.util.ArrayList;

public class BankerAlgorithm implements Strategies{

	private ArrayList<Integer> availableInstance;
	private Integer[][] maximumDemand;
	private Integer[][] allocatedInstance;
	private Integer[][] remainingNeed;
	
	private int numOfProcesses;
	private int numOfResources;
	
	
	

	public BankerAlgorithm(int numOfProcesses , int numOfResources) {
		super();
		this.availableInstance = new ArrayList<>(this.numOfResources);
		this.numOfProcesses = numOfProcesses;
		this.numOfResources = numOfResources;
		maximumDemand = new Integer[this.numOfProcesses][this.numOfResources];
		allocatedInstance = new Integer[this.numOfProcesses][this.numOfResources];
		remainingNeed = new Integer[this.numOfProcesses][this.numOfResources];
	}


	public void addInstanceForSpecificResource(Integer instance) {
		availableInstance.add(instance);
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

	@Override
	public void doAlgorithm() {
		
		
		
	}

}
