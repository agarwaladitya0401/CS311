
class Infiltrator extends Clock {
	Sensor s;	
	int current_x;
	int current_y = 0;
	
	public Infiltrator(Sensor sensor) {
		s = sensor;
		current_x = s.length / 2;
	}
	
	private void next_move() {
		int[] x;
		int[] y;
		if (current_x == 0 && current_y == s.width - 1) {
			x = new int[1];
			y = new int[1];
			x[0] = current_x+1;
			y[0] = current_y;
		} else if (current_x == s.length-1  && current_y == s.width-1) {
			x = new int[1];
			y = new int[1];
			x[0] = current_x-1;
			y[0] = current_y;
		} else if (current_x == 0) {
			x = new int[2];
			y = new int[2];
			x[0] = current_x;
			y[0] = current_y+1;
			x[1] = current_x+1;
			y[1] = current_y;
		} else if (current_x == s.length-1) {
			x = new int[2];
			y = new int[2];
			x[0] = current_x;
			y[0] = current_y+1;
			x[1] = current_x-1;
			y[1] = current_y;
		} else if (current_y == s.width-1) {
			x = new int[2];
			y = new int[2];
			x[0] = current_x-1;
			x[1] = current_x+1;
			y[0] = current_y;
			y[1] = current_y;
		}
		else {
			x = new int[5];
			y = new int[5];
			x[0] = current_x;
			x[1] = current_x-1;
			x[2] = current_x+1;
			x[3] = current_x-1;
			x[4] = current_x+1;
			y[0] = current_y+1;
			y[1] = current_y+1;
			y[2] = current_y+1;
			y[3] = current_y;
			y[4] = current_y;
		}
		for (int i=0; i<x.length; i++) {
			if (!s.border[x[i]][y[i]]) {
				current_x = x[i];
				current_y = y[i];
				break;
			}
		}
	}
	
	public int find_total_moves() {
		while(current_y < s.width) {
			s.update_border();
			next_move();
			moves++;
		}
		return moves;
	}
}
