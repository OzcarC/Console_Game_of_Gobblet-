import java.util.Arrays;

public class Hand {
	private Piece[][] playerHand = new Piece[3][4];
	private boolean empty;

	public Hand(int color) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				playerHand[i][j] = new Piece(color, j);
			}
		}
		this.empty = false;
	}

	public boolean remove(int size) {
		int emptyCounter = 0;
		for (int i = 2; i >= 0; i--) {
			for (int j = 3; j >= 0; j--) {
				if (this.playerHand[i][j] != null) {
					if (j == size) {
						this.playerHand[i][j] = null;
						return true;
					}
					break;
				}
				emptyCounter++;
				System.out.println(emptyCounter);
			}
		}
		if (emptyCounter == 12) {
			this.empty = true;
		}
		return false;
	}

	public boolean isEmpty() {
		return this.empty;
	}

	public boolean hasSize(int size) {
		for (int i = 2; i >= 0; i--) {
			for (int j = 3; j >= 0; j--) {
				if (this.playerHand[i][j] != null) {
					if (j == size) {
						return true;
					}
					break;
				}
			}
		}
		return false;
	}

	public String toString() {
		String str = "";
		int count = 0;
		Piece toPrint = new Piece();
		for (Piece[] pArr : this.playerHand) {
			count = 0;
			for (Piece p : pArr) {
				if (p != null)
					toPrint = p;
				else {
					count++;
				}
			}
			if (count == 4)
				str += "      ";
			else
				str += "  " + toPrint.toString() + "    ";
		}
		return str;
	}

	// public void testEmpty() {
	// this.empty = true;
	// }
}