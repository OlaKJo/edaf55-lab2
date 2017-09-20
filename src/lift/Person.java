package lift;

public class Person extends Thread{
	private LiftMonitor mon;

	public Person(LiftMonitor mon) {
		this.mon = mon;
	}

	public void run() {
		while (true) {
			long waitTime = Math.round(Math.random() * 1);
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

//			int initialFloor = (int) Math.round(Math.random() * 6);
//			int destinationFloor = (int) Math.round(Math.random() * 6);
			int initialFloor = 0;
			int destinationFloor = 6;
			while (initialFloor == destinationFloor) {
				destinationFloor = (int) Math.round(Math.random() * 6);
			}
			System.out.println("Created new person. initial floor: " + initialFloor + " destination floor: " + destinationFloor);
			mon.addPassenger(initialFloor);
			//mon.updateLevel(initialFloor);
			
			mon.load(destinationFloor);
			mon.unload();
		}
	}
}
