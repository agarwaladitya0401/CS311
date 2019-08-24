// Defines the Border
class Border {
	// Length is 500 and width is varying
	boolean border[][];
	int width;
	int length = 500;
	
	public Border(int w) {
		this.border = new boolean[length][w];
		this.width = w;
	}
}
