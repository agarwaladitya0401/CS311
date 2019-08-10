
class Border {
	boolean border[][];
	int width;
	int length = 100;
	
	public Border(int w) {
		this.border = new boolean[length][w];
		this.width = w;
	}
}
