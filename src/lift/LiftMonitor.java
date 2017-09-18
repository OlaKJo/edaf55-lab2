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
	
	public LiftMonitor(int here, int next) {
		this.here = here;
		this.next = next;
		dir = here - next;
	}
	
	synchronized void updateElevator() {
		if (here == 6) {
			dir = -1;
		} else if (here == 0) {
			dir = 1;
		}

		next = here + dir;
		waitExit[here] = 0;
		if(waitEntry[here] > 0) {
			int currentCapacity = 4 - load;
			if (waitEntry[here] <= currentCapacity) {
				waitExit[here] = waitEntry[here];
				waitEntry[here] = 0;
			}
			waitExit[here]++;
		}
		notifyAll();
	}
	public void addPassenger(int initialFloor, int destinationFloor) {
		waitEntry[initialFloor]++;
	}
	
	synchronized void load(int dest) {
		waitExit[dest]++;
		load++;
	}
	
	synchronized void unload() {
		waitExit[here]--;
		load--;
	}
}
