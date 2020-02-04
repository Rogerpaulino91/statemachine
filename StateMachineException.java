public class StateMachineException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StateMachineException() {
		super();
	}

	public StateMachineException(String s) {
		super(s);
	}

	public StateMachineException(String s, Throwable cause) {
		super(s, cause);
	}

}