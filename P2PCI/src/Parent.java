class CountMonitor {

	private int count = 0; // = # of active threads

	public synchronized void updateCount(int amt) {

		// don't decrement a zero count
		if (count <= 0 && amt <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
			;
		}

		count += amt;
		notify(); // start waiting threads
	}

	public int getCount() {
		return count;
	}

}

class Child extends Thread {

	private int myID;
	private CountMonitor myCount;

	public Child(int i, CountMonitor c) {
		myID = i;
		myCount = c;
	}

	public void run() {
		myCount.updateCount(1);
		for (int i = 0; i < 3; i++) {
			System.out.println("   Child-" + myID + ": thread count = "
					+ myCount.getCount());
			try {
				sleep(10);
			} catch (InterruptedException e) {
				System.exit(1);
			}

			System.out.println("   Child-" + myID + " dying ... ");
			myCount.updateCount(-1);
		}
	}
} // Child 