import java.io.*;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class QuickSortMutliThreading
	extends RecursiveTask<Integer> {

	int start, end;
	int[] arr;
	/**
	* Finding random pivoted and partition array on a pivot.
	* There are many different partitioning algorithms.
	* @param start @param end @param arr
	* @return
	*/
	private int partition(int start, int end,
						int[] arr)
	{
		int i = start, j = end;
		int pivoted = new Random().nextInt(j - i)+ i; // Decide random pivot
		int t = arr[j]; // Swap the pivoted with end element of array
		arr[j] = arr[pivote];
		arr[pivote] = t;
		j--;
		// Start partitioning
		while (i <= j) {  
			if (arr[i] <= arr[end]) {
				i++;
				continue;
			}
			if (arr[j] >= arr[end]) {
				j--;
				continue;
			}
			t = arr[j];
			arr[j] = arr[i];
			arr[i] = t;
			j--;
			i++;
		}

		// Swap pivoted to its correct position
		t = arr[j + 1];
		arr[j + 1] = arr[end];
		arr[end] = t;
		return j + 1;
	}

	// Function to implement QuickSort method
	public QuickSortMutliThreading(int start,int end,int[] arr)
	{
		this.arr = arr;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute()
	{
		// Base case
		if (start >= end)
			return null;

		// Find partition
		int p = partition(start, end, arr);

		// Divide array
		QuickSortMutliThreading left = new QuickSortMutliThreading(start,p - 1,arr);
		QuickSortMutliThreading right = new QuickSortMutliThreading(p + 1,end,arr);

		left.fork(); // Left subproblem as separate thread
		right.compute();
		left.join(); // Wait until left thread complete
		return null;
	}

	// Driver Code
	public static void main(String args[])
	{
		int n = 7;
		int[] arr = { 54, 64, 95, 82, 12, 32, 63 };

		// Forkjoin ThreadPool to keep thread creation as per resources
		ForkJoinPool pool = ForkJoinPool.commonPool();

		// Start the first thread in fork join pool for range 0, n-1
		pool.invoke(
			new QuickSortMutliThreading(
				0, n - 1, arr));

		// Print shorted elements
		for (int i = 0; i < n; i++)
			System.out.print(arr[i] + " ");
	}
}
