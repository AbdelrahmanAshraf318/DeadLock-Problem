package Deadlock;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		int numOfProcesses = 0;
		int numOfResources = 0;
		
		
		System.out.println("Enter the number of Processes you want to be in the system: ");
		numOfProcesses = input.nextInt();
		
		System.out.println("Enter the number of Resources you want to be in the system: ");
		numOfResources = input.nextInt();
		
		BankerAlgorithm banker = new BankerAlgorithm(numOfProcesses, numOfResources);
		
		 
		
		
		/** Enter maxDemand **/
		for(int i=0 ; i<numOfProcesses ; i++)
		{
			for(int j=0 ; j<numOfResources ; j++)
			{
				System.out.println("Enter the maxDemand for process-" + i +
						" for resource-" + j);
				int maxDemand = 0;
				maxDemand = input.nextInt();
				banker.addMaxDemand(maxDemand , i , j);
			}
		}
		
		/** Enter allocatedResource **/
		for(int i=0 ; i<numOfProcesses ; i++)
		{
			for(int j=0 ; j<numOfResources ; j++)
			{
				System.out.println("Enter the allocatedResource for process-" + i +
						" for resource-" + j);
				int allocated = 0;
				allocated = input.nextInt();
				banker.addAllocatedInstance(allocated , i , j);
			}
		}
		
		/** addAvailableInstance **/
		for(int i=0 ; i<numOfResources ; i++)
		{
			System.out.println("Enter the available Instance for Resource-" + i);
			int available = 0;
			available = input.nextInt();
			banker.addAvailableInstance(available);
		}
		
		/** apply the banker algorithm **/
		banker.doAlgorithm();
	}

}
