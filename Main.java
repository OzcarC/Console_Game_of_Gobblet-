import java.util.InputMismatchException;
import java.util.Scanner;

class Main {
	private static int turnCounter = 1;
	private static boolean won = false;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		rules();

		Board playingBoard = new Board();
		Hand player1 = new Hand(0);
		Hand player2 = new Hand(1);

		while (!won) {
			switch (turnCounter % 2) {
				case 1:
					turn(scan, playingBoard, player1, player2);
					break;
				case 0:
					turn(scan, playingBoard, player2, player1);
					break;
			}

		}
	}

	public static void rules() {
		System.out.println("Welcome To Gobblet!");
		System.out.println("________________________________\nHere are the rules: ");
		System.out.println(
				"- Connect 4 in a row to win\n- Larger sized pieces can cover smaller sized pieces\n- The piece sizes from smallest to largest are as follows: S, M, L, X");
		System.out.println(
				"- Each player starts with 3 stacks of 4 Pieces for a total of 12 pieces (3 small, 3 medium,... and so on)\n- You can place from your hand to an empty space on the board OR move from the board to an empty space on the board OR move from the board to a space occupied by a smaller piece (gobbling up).");

		System.out.println("\n\nNow let's begin!\n");
	}

	public static void turn(Scanner scan, Board playingBoard, Hand player, Hand otherPlayer) {
		System.out.println("\nTurn " + turnCounter + ":\n");
		System.out.println(playingBoard.printBoard() + "\n");

		switch (turnCounter % 2) {
			case 1:
				System.out.println("Player 1's Hand: " + player.toString());
				System.out.println("Player 2's Hand: " + otherPlayer.toString());
				break;
			case 0:
				System.out.println("Player 1's Hand: " + otherPlayer.toString());
				System.out.println("Player 2's Hand: " + player.toString());
				break;
		}

		System.out.println("\nWhat would you like to do player " + (-1 * ((turnCounter % 2) - 2)) + "?");

		System.out.println("[1]: Play from Hand");
		System.out.println("[2]: Play from Board");
		System.out.println("[3]: Gobble");
		try {
			int playType = scan.nextInt();
			String fromLocation;
			String toLocation;
			scan.nextLine();
			switch (playType) {
				case 1:
					if (player.isEmpty()) {
						throw new EmptyHandException("The hand is empty, play from the board or gobble.");
					}
					System.out.println("What size would you like to use? (S,M,L,or X)");
					String sizeStr = scan.nextLine();
					int size = 0;
					sizeStr.trim();
					sizeStr = sizeStr.toUpperCase();
					switch (sizeStr) {
						case "S":
							size = 0;
							break;
						case "M":
							size = 1;
							break;
						case "L":
							size = 2;
							break;
						case "X":
							size = 3;
							break;
						default:
							throw new IllegalArgumentException("The size entered is not valid.");
					}

					if (player.hasSize(size)) {
						System.out.println(
								"Where would you like to place it on the board? Please use the following notation: A1, C3, D4, etc.");
						String location = scan.nextLine();
						playingBoard.placePiece(new Piece((-1 * ((turnCounter % 2) - 1)), size), location);
						player.remove(size);
					} else {
						throw new IllegalArgumentException(
								"The size entered is not at the top of the hand stacks, ensure you choose a piece from the top of the stack.");
					}
					break;
				case 2:
					System.out.println("Where is the piece you want to move?");
					fromLocation = scan.nextLine();
					System.out.println("Where do you want to place this piece?");
					toLocation = scan.nextLine();
					playingBoard.movePiece(fromLocation, toLocation);
					break;
				case 3:
					System.out.println("Where is the piece you want to move?");
					fromLocation = scan.nextLine();
					System.out.println("Where is the piece you want to Gobble?");
					toLocation = scan.nextLine();
					playingBoard.gobblePiece(fromLocation, toLocation);
					break;
				default:
					throw new IllegalArgumentException("The value you entered is not a valid menu option.");
			}
			turnCounter++;
		} catch (InputMismatchException inEx) {
			System.out.println("The value you entered is not valid, try 1, 2, or 3.");
			scan.nextLine();
		} catch (EmptyHandException handEx) {
			handEx.printStackTrace();
		} catch (IllegalArgumentException argEx) {
			argEx.printStackTrace();
		} catch (IndexOutOfBoundsException indEx) {
			indEx.printStackTrace();
		} catch (WrongPieceException pieceEx) {
			pieceEx.printStackTrace();
		}

	}

	public static String colorConvert(int color) {
		if (color == 0) {
			return "Red";
		}
		return "Blue";
	}

	public static int getTurnCounter() {
		return turnCounter;
	}
}