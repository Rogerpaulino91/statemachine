import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import statemachine.AbstractAction;

public class CheckHttpAction extends AbstractAction<TweetState> {

	private static final String CHECK = "ttp://";

	private int pos;

	private final ByteArrayOutputStream tagStream;

	private final byte[] buf;

	public CheckHttpAction(OutputStream os) {
		super(os);
		tagStream = new ByteArrayOutputStream();
		buf = new byte[1];
	}

	@param b
	@param currentState

	@Override
	public TweetState processByte(byte b, TweetState currentState) throws Exception {

		TweetState retVal = currentState;

		if (CHECK.charAt(pos++) != b) {
			retVal = returnToRunning(b);
			reset();
		} else if (pos == CHECK.length()) {
			retVal = TweetState.URL;
			reset(); // fix 1
		} else {
			buf[0] = b;
			tagStream.write(buf);
		}

		return retVal;
	}

	private TweetState returnToRunning(byte b) throws Exception {
		writeByte('h');
		os.write(tagStream.toByteArray());
		writeByte(b);
		return TweetState.RUNNING;
	}

	/**
	 * Reset the object ready for processing
	 */
	public void reset() {

		pos = 0; // fix 1
		tagStream.reset();
	}

	@Override
	public void terminate(TweetState state) throws Exception {
		returnToRunning((byte) ' ');
	}
}      