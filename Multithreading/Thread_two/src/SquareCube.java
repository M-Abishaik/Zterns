
/*
 *	Squares and cubes the array elements using two threads. 
 */

public class SquareCube {
	
	private static int arr1[] = new int[]{1,2,3,4,5};
	private static int arr2[] = new int[]{2,3,4,5,6};
	private static int sumSquare=0, sumCube=0, finalSum=0;
	
	public static void main(String[] args) {
		
		Runnable run1 = new Runnable1();
		Thread t1 = new Thread(run1);
		Runnable run2 = new Runnable2();
		Thread t2 = new Thread(run2);
		Runnable run3 = new Runnable3();
		Thread t3 = new Thread(run3);
    
		t1.start();
		t2.start();
		t3.start();
	}

	static class Runnable1 implements Runnable{
    
		public void run(){
			int length1 = arr1.length;
			for(int i=0;i<length1;i++) {
				sumSquare+=(arr1[i]*arr1[i]);
			}
		}
	}


	static class Runnable2 implements Runnable{
		public void run(){
			int length2 = arr2.length;
			for(int i=0;i<length2;i++) {
				sumCube+=(arr2[i]*arr2[i]*arr2[i]);
			}
		}
	}

	static class Runnable3 implements Runnable{
		public void run(){
			int arr[] = new int[arr1.length];
			int length1 = arr1.length;
			for(int i=0;i<length1;i++) {
				arr[i] = arr1[i]+arr2[i];
				System.out.println(arr[i]);
			}
		}
	}
}
