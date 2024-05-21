public class WrongPieceException extends RuntimeException {
	public WrongPieceException() {
		super();
	}

	public WrongPieceException(String msg) {
		super(msg);
	}

	public WrongPieceException(String msg, Throwable t) {
		super(msg, t);
	}

	public WrongPieceException(Throwable t) {
		super(t);
	}
}