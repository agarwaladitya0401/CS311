
class Infiltrator extends Clock {
	Sensor s;	
	int current_x;
	int current_y = 0;
	
	public Infiltrator(Sensor sensor) {
		s = sensor;
		current_x = s.length / 2;
	}
	
	private void next_move() {
		if (current_y == s.width-1 && !s.border[current_x][current_y]) {
			current_y++;
			return;
		}
		if (!s.border[current_x][current_y] && !s.border[current_x][current_y+1]) {
			current_y++;
		}
		return;
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
