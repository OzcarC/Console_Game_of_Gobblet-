public class Piece {
	private int color;
	private int size;

	public Piece() {
		this.color = 0;
		this.size = 0;
	}

	public Piece(int color, int size) {
		this.color = color;
		this.size = size;
	}

	public Piece(Piece other) {
		this.color = other.color;
		this.size = other.size;
	}

	public int getSize() {
		return this.size;
	}

	public int getColor() {
		return this.color;
	}

	public String toString() {
		String str = "";
		if (color == 0) {
			str += "\033[0;31m"; // Red
		} else {
			str += "\033[0;34m"; // Blue
		}
		switch (this.size) {
			case 0:
				str += " S ";
				break;
			case 1:
				str += " M ";
				break;
			case 2:
				str += " L ";
				break;
			case 3:
				str += " X ";
				break;
		}
		str += "\033[0m";
		return str;
	}
}