package lift;

public class Person {
	private LiftMonitor mon;

	public Person(LiftMonitor mon) {
		this.mon = mon;
	}

	public void run() {
		while (true) {
			long waitTime = Math.round(Math.random() * 45);
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			int initialFloor = (int) Math.round(Math.random() * 6);
			int destinationFloor = (int) Math.round(Math.random() * 6);
			while (initialFloor == destinationFloor) {
				destinationFloor = (int) Math.round(Math.random() * 6);
			}
			
			while (mon.here != initialFloor)
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			mon.load(destinationFloor);
			
			while (mon.here != destinationFloor)
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			mon.unload();
		}
	}
}
