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
		dir = next - here;
		this.liftView = liftView;
		waitEntry = new int[7];
		waitExit = new int[7];
	}

	synchronized void updateElevator() {
		while (!passengerIsWaiting() && load == 0)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		here += dir;
		if (here == 6) {
			dir = -1;
		} else if (here == 0) {
			dir = 1;
		}
		liftView.drawLift(here, load);
		notifyAll();
	}

	private boolean passengerIsWaiting() {
		boolean waiting = false;
		for (int i : waitEntry) {
			waiting = i > 0 ? true : false;
			if (waiting)
				break;
		}
		return waiting;
	}

	synchronized void addPassenger(int initialFloor, int destinationFloor) {
		waitEntry[initialFloor]++;
		liftView.drawLevel(initialFloor, waitEntry[initialFloor]);
		notifyAll();
		
	}


	// possibly merge load unload addpassenger to single public method
	
	synchronized void load(int initialFloor, int destinationFloor) {
		int passDir = destinationFloor - initialFloor;
		while ((here != initialFloor || isFull()) || (passDir * dir < 0))
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		waitExit[destinationFloor]++;
		load++;
		waitEntry[here]--;
		liftView.drawLift(here, load);
		liftView.drawLevel(here, waitEntry[here]);
		
	}

	synchronized void unload(int destinationFloor) {
		while (here != destinationFloor)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		waitExit[here]--;
		load--;
		liftView.drawLevel(here, waitEntry[here]);
		liftView.drawLift(here, load);
	}

	private boolean isFull() {
		return load >= 4;
	}
}
