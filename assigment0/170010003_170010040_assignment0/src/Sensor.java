
class Sensor extends Border {
	double prob;
	
	public Sensor(double p, int width) {
		super(width);
		prob = p;
	}
	
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
