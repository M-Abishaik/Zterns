/*
 *  Creates three threads and accesses three arrays.
 */

public class Main  {
		
		static int[] array1 = new int[]{2,4,6,8,10,12};
		static int[] array2 = new int[]{12,14,16,18,20,22};
		static int[] array3 = new int[]{22,24,26,28,30,32};
		static int i=0, j=0, k=0,l=0;
		static Object monitor = new Object();
		static boolean one = true;
		static boolean two = false;
		static boolean three = false;

		public static void main(String[] args) {

		  Thread t1 = new Thread(new MultiprogramImpl(1));
		  Thread t2 = new Thread(new MultiprogramImpl(2));
		  Thread t3 = new Thread(new MultiprogramImpl(3));
		  t1.start();
		  t2.start();
		  t3.start();
		 }

		 static class MultiprogramImpl implements Runnable {

			 int threadId;

			 MultiprogramImpl(int threadId) {
				 this.threadId = threadId;
			 }

			 public void run() {
				 print();
			 }

			 private void print() {
				 try {
					 while (true) {
						 Thread.sleep(500);
						 synchronized (monitor) {
							 if (1 == threadId) {
								 if (!one) {
									 monitor.wait();
								 } else {
									 System.out.println(array1[i++]);
									 one = false;
									 two = true;
									 three = false;
									 monitor.notifyAll();
								 }
							 }
		      
							 if (2 == threadId) {
								 if (!two) {
									 monitor.wait();
								 } else {
									 System.out.println(array2[j++]);
									 one = false;
									 two = false;
									 three = true;
									 monitor.notifyAll();
								 }
							 }
		      
							 if (3 == threadId) {
								 if (!three) {
									 monitor.wait();
								 } else {
									 System.out.println(array3[k++]);
									 one = true;
									 two = false;
									 three = false;
									 monitor.notifyAll();
									 if(k == array3.length){
										 System.exit(0);
									 }
								 }
							 }
						 }
					 }
				 } catch (InterruptedException e) {
					 e.printStackTrace();
				 }
			 }
		 }
}

	
	/*private static int arr[] = new int[] {2,4,6,8,10,12,12,14,16,18,20,22,22,24,26,28,30,32};

	public static void main(String[] args) throws InterruptedException {
		
		PrintSequenceRunnable runnable1=new PrintSequenceRunnable(1);
		PrintSequenceRunnable runnable2=new PrintSequenceRunnable(2);
		PrintSequenceRunnable runnable3=new PrintSequenceRunnable(0);
		
		Thread t1=new Thread(runnable1,"T1");
		Thread t2=new Thread(runnable2,"T2");
		Thread t3=new Thread(runnable3,"T3");
		
		t1.start();
		t2.start();
		t3.start();	
	}
	
	public static class PrintSequenceRunnable implements Runnable{
		
		public int PRINT_NUMBERS_UPTO=18;
		static int  number=0;
		int remainder,i=0;
		static Object lock=new Object();
	 
		PrintSequenceRunnable(int remainder)
		{
			this.remainder=remainder;
		}
	 
		@Override
		public void run() {
			while (i < arr.length) {
				synchronized (lock) {
					while (number % 3 != remainder) { // wait for numbers other than remainder
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}									
					}
					System.out.println(Thread.currentThread().getName() + " " +arr[number]);
					if(arr[number]==32)
							break;
					number = number+6;
					if(number>=18)
						number=(number%18)+1;
					i++;
					lock.notifyAll();
				}
			}
		}
	}*/
	
	

