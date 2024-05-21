public class EmptyHandException extends RuntimeException {
	public EmptyHandException() {
		super();
	}

	public EmptyHandException(String msg) {
		super(msg);
	}

	public EmptyHandException(String msg, Throwable t) {
		super(msg, t);
	}

	public EmptyHandException(Throwable t) {
		super(t);
	}
}