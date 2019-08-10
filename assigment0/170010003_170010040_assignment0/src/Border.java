
class Border {
	boolean border[][];
	int width;
	int length = 500;
	
	public Border(int w) {
		this.border = new boolean[length][w];
		this.width = w;
	}
}
