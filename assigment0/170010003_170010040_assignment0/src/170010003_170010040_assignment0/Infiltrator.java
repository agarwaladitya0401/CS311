// Defines the Infiltrator
// Inherits from Clock
class Infiltrator extends Clock {
	Sensor s;
	// Starting position of Infiltrator
	int current_x;
	int current_y = 0;
	
	public Infiltrator(Sensor sensor) {
		s = sensor;
		current_x = s.length / 2;
	}
	
	private void get_best_move() {
		int[] x;
		int[] y;
		if (current_x == 0) {
			x = new int[2];
			y = new int[2];
			x[0] = current_x;
			y[0] = current_y+1;
			x[1] = current_x+1;
			y[1] = current_y+1;
		} else if (current_x == s.length-1) {
			x = new int[2];
			y = new int[2];
			x[0] = current_x;
			y[0] = current_y+1;
			x[1] = current_x-1;
			y[1] = current_y+1;
		} else {
			x = new int[3];
			y = new int[3];
			x[0] = current_x;
			y[0] = current_y+1;
			x[1] = current_x-1;
			y[1] = current_y+1;
			x[2] = current_x+1;
			y[2] = current_y+1;
		}
		for (int i=0; i<x.length; i++) {
			if (!s.border[x[i]][y[i]]) {
				if (!s.border[current_x][current_y]) {
					current_x = x[i];
					current_y = y[i];
				}
				break;
			}
		}
	}

	private void next_move() {
		// If infiltrator in on the edge the he will only go to ahead
		if (current_y == s.width - 1) {
			if (!s.border[current_x][current_y]) {
				current_y++;
			}
		// Otherwise it will find the best move out of front three
		} else {
			get_best_move();
		}
	}
	
	// Finds the total number of moves required by infiltrator
	public int find_total_moves() {
		while(current_y < s.width) {
			// Updates the sensors
			s.update_border();
			// Moves the Infiltrator
			next_move();
			moves++;
		}
		// Since each move takes 10s, final answer is total moves * 10
		return moves * 10;
	}
}
