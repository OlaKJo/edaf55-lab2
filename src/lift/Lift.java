package lift;

public class Lift extends Thread {
	private LiftMonitor mon;
	
	public Lift(LiftMonitor mon) {
		this.mon = mon;
	}
	
	public void run() {
		while (true) {
			long waitTime = Math.round(Math.random() * 5);
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mon.updateElevator();
			
		}
	}
}
