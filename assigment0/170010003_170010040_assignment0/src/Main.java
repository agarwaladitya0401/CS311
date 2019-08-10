import java.io.File;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) {
		// Probabilities of sensor being on
		double[] prob = {0.2, 0.4, 0.6, 0.8};
		// Width of border
		int[] width = {2, 4, 6, 8, 10, 12, 14, 16};
		// Writes the output to this file
		File file = new File("result.csv");
		try {
			PrintWriter writer = new PrintWriter("result.txt", "UTF-8");
			String header = "Probability, Width, Time";
			writer.println(header);
			for (double p: prob) {
				for (int w: width) {
					int total_time = 0;
					// For each combination of probability and width, program will run
					// 50 times and average time will be written to the file
					for (int i=0; i<50; i++) {
						Sensor s = new Sensor(p, w);
						Infiltrator spy = new Infiltrator(s);
						total_time += spy.find_total_moves();
					}
					int avg_time = total_time/50;
					String data = String.valueOf(p) + ", " + String.valueOf(w) + ", " + String.valueOf(avg_time);
					writer.println(data);
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
