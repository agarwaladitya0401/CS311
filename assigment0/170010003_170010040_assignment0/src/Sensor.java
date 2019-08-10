// Defines the Sensor
// It inherits from Border class
class Sensor extends Border {
	// Probability of sensor being on
	double prob;
	
	public Sensor(double p, int width) {
		super(width);
		prob = p;
	}
	
	// Updates whether sensor is on or not 
	public void update_border() {
		for (int i=0; i<border.length; i++) {
			for (int j=0; j<border[i].length; j++) {
				double local_p = Math.random();
				if (local_p < prob)
					border[i][j] = true;
				else
					border[i][j] = false;
			}
		}
	}
}
