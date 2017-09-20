package lift;

public class LiftMonitor {
	
	int here;
	// If here!=next, here (floor number) tells from which floor
	// the lift is moving and next to which floor it is moving.
	int next;
	// If here==next, the lift is standing still on the floor
	// given by here.
	int[] waitEntry;
	// The number of persons waiting to enter the lift at the
	// various floors.
	int[] waitExit;
	// The number of persons (inside the lift) waiting to leave
	// the lift at the various floors.
	int load;
	// The number of people currently occupying the lift.
	
	int dir;
	
	private LiftView liftView;
	
	public LiftMonitor(int here, int next, LiftView liftView) {
		this.here = here;
		this.next = next;
		dir = here - next;
		this.liftView = liftView;
		waitEntry = new int[7];
		waitExit = new int[7];
	}
	
	synchronized void updateElevator() {
		if (here == 6) {
			dir = -1;
		} else if (here == 0) {
			dir = 1;
		}
		here += dir;

		liftView.drawLift(here,load);
		notifyAll();
	}
	synchronized void addPassenger(int initialFloor) {
		waitEntry[initialFloor]++;
		liftView.drawLevel(initialFloor,waitEntry[initialFloor]);
		while (here != initialFloor || isFull())
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	synchronized void load(int dest) {
		waitExit[dest]++;
		load++;
		waitEntry[here]--;
		liftView.drawLift(here,load);
		liftView.drawLevel(here,waitEntry[here]);
		while (here != dest)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	synchronized void unload() {
		waitExit[here]--;
		load--;
		liftView.drawLevel(here,waitEntry[here]);
		liftView.drawLift(here, load);
	}

	private boolean isFull() {
		return load >= 4;
	}
}
