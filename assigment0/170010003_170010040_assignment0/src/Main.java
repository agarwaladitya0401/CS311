
public class Main {

	public static void main(String[] args) {
		double prob = 0.5;
		int width = 4;
		Sensor s = new Sensor(prob, width);
		Infiltrator spy = new Infiltrator(s);
		System.out.println("Test 0");
		System.out.println(spy.find_total_moves());

	}

}
