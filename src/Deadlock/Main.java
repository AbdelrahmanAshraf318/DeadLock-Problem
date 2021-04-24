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
		System.out.println();
		System.out.println("***************************");
		/*** See whether the user wants to continue or not ****/
		String keep = "";
		System.out.println("Write (exit , RQ , RL)");
		keep = input.next();
		input.nextLine();
		if(keep.equals("exit"))
		{
			System.out.println("Thanks for using our app");
			return;
		}
		if(keep.equals("RQ"))
		{
			int numOfProcess = 0;
			ArrayList<Integer> request = new ArrayList<Integer>();
			System.out.println("Enter the number of process (zero based): ");
			numOfProcess = input.nextInt();
			
			for(int counter = 0 ; counter<numOfResources ; counter++)
			{
				int requestResource = 0;
				System.out.print("Enter request number-" + counter + " ");
				requestResource = input.nextInt();
				request.add(requestResource);
				System.out.println();
			}
			banker.requestResources(request, numOfProcess); 
			return;
		}
	}
	

}
