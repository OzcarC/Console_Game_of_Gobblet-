import java.util.ArrayList;

public class Board {

	private ArrayList<ArrayList<ArrayList<Piece>>> gameBoard = new ArrayList<ArrayList<ArrayList<Piece>>>(4);
	private Piece winningPiece = new Piece();

	public Board() {
		for (int i = 0; i < 4; i++) {
			gameBoard.add(new ArrayList<ArrayList<Piece>>(4));
			for (int j = 0; j < 4; j++) {
				gameBoard.get(i).add(new ArrayList<Piece>(4));
			}
		}
	}

	public Piece getWinningPiece() {
		return this.winningPiece;
	}

	public String printBoard() {
		String str = "";

		for (int i = 0; i < 4; i++) {
			str += (4 - i) + " ";
			for (int j = 0; j < 4; j++) {
				if (gameBoard.get(i).get(j).isEmpty()) {
					str += "[ - ]";
				} else {
					for (int k = 0; k < gameBoard.get(i).get(j).size(); k++) {
						if (gameBoard.get(i).get(j).get(k) == null) {
							str += "[" + gameBoard.get(i).get(j).get(k - 1).toString() + "]";
						}
						if (k == gameBoard.get(i).get(j).size() - 1 && gameBoard.get(i).get(j).get(k) != null) {
							str += "[" + gameBoard.get(i).get(j).get(k).toString() + "]";
						}
					}

				}
			}
			str += "\n";
		}
		str += "    A    B    C    D";
		return str;
	}

	public void placePiece(Piece piece, String location) throws IllegalArgumentException, IndexOutOfBoundsException {
		int xIndex = findX(location);
		int yIndex = findY(location);

		if (xIndex > 3 || yIndex > 3 || xIndex < 0 || yIndex < 0) {
			throw new IndexOutOfBoundsException(
					"The location entered is not valid, ensure it is in the following notation: A1, B2, C3, D4, etc.");
		}

		if (!gameBoard.get(yIndex).get(xIndex).isEmpty()) {
			throw new IllegalArgumentException("When playing from the hand, you must place on an empty space.");
		}

		gameBoard.get(yIndex).get(xIndex).add(piece);
		if (this.checkWin()) {
			System.out.println(printBoard());
			System.out.println("\n\n" + Main.colorConvert(this.getWinningPiece().getColor()) + " wins!\n");
			System.exit(0);
		}

	}

	public void movePiece(String fromLocation, String toLocation)
			throws IllegalArgumentException, IndexOutOfBoundsException, WrongPieceException {
		int xFrom = findX(fromLocation);
		int yFrom = findY(fromLocation);
		int xTo = findX(toLocation);
		int yTo = findY(toLocation);

		if (xFrom > 3 || xFrom < 0 || yFrom > 3 || yFrom < 0) {
			throw new IndexOutOfBoundsException(
					"The location for the piece you want to move is not valid, ensure it is in the following notation: A1, B2, C3, D4, etc.");
		}

		if (xTo > 3 || xTo < 0 || yTo > 3 || yTo < 0) {
			throw new IndexOutOfBoundsException(
					"The location for the space you want to move the piece to is not valid, ensure it is in the following notation: A1, B2, C3, D4, etc.");
		}
		if (gameBoard.get(yFrom).get(xFrom).isEmpty()) {
			throw new IllegalArgumentException("There is no piece at the location you entered.");
		}
		if (!gameBoard.get(yTo).get(xTo).isEmpty()) {
			throw new IllegalArgumentException(
					"There is already a piece where you are trying to move your piece to, try Gobbling or moving to an empty space.");
		}

		switch (Main.getTurnCounter() % 2) {
			case 1:
				if (this.getPiece(fromLocation).getColor() != 0) {
					throw new WrongPieceException("You can not move someone else's piece!");
				}
				break;
			case 0:
				if (this.getPiece(fromLocation).getColor() == 0) {
					throw new WrongPieceException("You can not move someone else's piece!");
				}
				break;
		}

		Piece temp = gameBoard.get(yFrom).get(xFrom).remove(gameBoard.get(yFrom).get(xFrom).size() - 1);
		if (this.checkWin()) {
			System.out.println(printBoard());
			System.out.println("\n\n" + Main.colorConvert(this.getWinningPiece().getColor()) + " wins!\n");
			System.exit(0);
		}
		gameBoard.get(yTo).get(xTo).add(temp);
		if (this.checkWin()) {
			System.out.println(printBoard());
			System.out.println("\n\n" + Main.colorConvert(this.getWinningPiece().getColor()) + " wins!\n");
			System.exit(0);
		}

	}

	public void gobblePiece(String fromLocation, String toLocation)
			throws IllegalArgumentException, IndexOutOfBoundsException, WrongPieceException {
		int xFrom = findX(fromLocation);
		int yFrom = findY(fromLocation);
		int xTo = findX(toLocation);
		int yTo = findY(toLocation);

		if (xFrom > 3 || xFrom < 0 || yFrom > 3 || yFrom < 0) {
			throw new IndexOutOfBoundsException(
					"The location for the piece you want to move is not valid, ensure it is in the following notation: A1, B2, C3, D4, etc.");
		}

		if (xTo > 3 || xTo < 0 || yTo > 3 || yTo < 0) {
			throw new IndexOutOfBoundsException(
					"The location for the space you want to move the piece to is not valid, ensure it is in the following notation: A1, B2, C3, D4, etc.");
		}

		switch (Main.getTurnCounter() % 2) {
			case 1:
				if (this.getPiece(fromLocation).getColor() != 0) {
					throw new WrongPieceException("You can not move someone else's piece!");
				}
				break;
			case 0:
				if (this.getPiece(fromLocation).getColor() == 0) {
					throw new WrongPieceException("You can not move someone else's piece!");
				}
				break;
		}

		if (gameBoard.get(yFrom).get(xFrom).isEmpty()) {
			throw new IllegalArgumentException("There is no piece at the location you entered.");
		}
		if (this.getPiece(fromLocation)
				.getSize() <= this.getPiece(toLocation).getSize()) {
			throw new IllegalArgumentException(
					"The selected piece can not Gobble the destination piece because it is not large enough.");
		} else {
			Piece temp = gameBoard.get(yFrom).get(xFrom).remove(gameBoard.get(yFrom).get(xFrom).size() - 1);
			if (this.checkWin()) {
				System.out.println(printBoard());
				System.out.println("\n\n" + Main.colorConvert(this.getWinningPiece().getColor()) + " wins!\n");
				System.exit(0);
			}
			gameBoard.get(yTo).get(xTo).add(temp);
		}
	}

	public int findX(String location) {
		location.trim();
		location = location.toUpperCase();
		switch (location.charAt(0)) {
			case 'A':
				return 0;
			case 'B':
				return 1;
			case 'C':
				return 2;
			case 'D':
				return 3;
			default:
				return -1;
		}
	}

	public int findY(String location) {
		location.trim();
		if (location.length() > 2) {
			throw new IllegalArgumentException(
					"The location you have entered is not valid, ensure it is formatted correctly & a valid space on the board (A1, B2, etc.)");
		}
		int tempY = 4 - Character.getNumericValue(location.charAt(1));
		return tempY;
	}

	public Piece getPiece(String location) {
		int x = findX(location);
		int y = findY(location);

		if (gameBoard.get(y).get(x).isEmpty()) {
			throw new IllegalArgumentException("This piece does not exist.");
		}
		return gameBoard.get(y).get(x).get(gameBoard.get(y).get(x).size() - 1);

	}

	public Piece getPiece(int x, int y) throws IllegalArgumentException {
		if (gameBoard.get(x).get(y).isEmpty()) {
			throw new IllegalArgumentException("This piece does not exist.");
		}
		return gameBoard.get(x).get(y).get(gameBoard.get(x).get(y).size() - 1);
	}

	public boolean checkWin() {
		boolean winning = false;
		// Horizontal
		for (int i = 0; i < 4; i++) {
			if (winning) {
				break;
			}
			for (int j = 0; j < 4; j++) {
				if (gameBoard.get(i).get(j).isEmpty()) {
					winning = false;
					break;
				}
				try {
					if (this.getPiece(i, j).getColor() == this.getPiece(i, 0).getColor()) {
						winning = true;
						this.winningPiece = this.getPiece(i, 0);
					} else {
						winning = false;
						break;
					}
				} catch (IllegalArgumentException argEx) {
					winning = false;
					break;
				}
			}
		}

		// Vertical
		for (int i = 0; i < 4; i++) {
			if (winning) {
				break;
			}
			for (int j = 0; j < 4; j++) {
				if (gameBoard.get(j).get(i).isEmpty()) {
					winning = false;
					break;
				}
				try {
					if (this.getPiece(j, i).getColor() == this.getPiece(0, i).getColor()) {
						winning = true;
						this.winningPiece = this.getPiece(0, i);
					} else {
						winning = false;
						break;
					}
				} catch (IllegalArgumentException argEx) {
					winning = false;
					break;
				}
			}
		}

		// R to L diag
		if (winning) {
			return winning;
		}
		for (int i = 0; i < 4; i++) {
			if (gameBoard.get(i).get(i).isEmpty()) {
				winning = false;
				break;
			}
			try {
				if (this.getPiece(i, i).getColor() == this.getPiece(0, 0).getColor()) {
					winning = true;
					this.winningPiece = this.getPiece(0, 0);
				} else {
					winning = false;
					break;
				}
			} catch (IllegalArgumentException argEx) {
				winning = false;
				break;
			}
		}
		if (winning) {
			return winning;
		}

		// L to R diag
		for (int i = 0; i < 4; i++) {
			if (gameBoard.get(i).get(3 - i).isEmpty()) {
				winning = false;
				break;
			}
			try {
				if (this.getPiece(i, 3 - i).getColor() == this.getPiece(0, 3).getColor()) {
					winning = true;
					this.winningPiece = this.getPiece(0, 3);
				} else {
					winning = false;
					break;
				}
			} catch (IllegalArgumentException argEx) {
				winning = false;
				break;
			}
		}
		return winning;
	}
}