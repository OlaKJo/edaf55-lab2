package lift;

public class LiftSimulator {

	public LiftSimulator() {
		LiftView lv = new LiftView();

		LiftMonitor mon = new LiftMonitor(0, 1, lv);
		for (int i = 0; i < 10; i++) {
			Person p = new Person(mon);
			p.start();
		}
		Lift lift = new Lift(mon);
		lift.start();
	}
	public static void main(String[] args) {
		new LiftSimulator();
	}
}
