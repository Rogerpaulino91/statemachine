import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import statemachine.AbstractAction;

public class DefaultAction extends AbstractAction<TweetState> {

	public DefaultAction(OutputStream os) {
		super(os);
	}
@param b
@param currentState

@Override
	public TweetState processByte(byte b, TweetState currentState) throws Exception {

		TweetState retVal = TweetState.RUNNING;
if (isSpace(b)) {
			retVal = TweetState.READY;
			writeByte(b);
		} else if (isHashAtStart(b, currentState)) {
			retVal = TweetState.HASHTAG;
		} else if (isNameAtStart(b, currentState)) {
			retVal = TweetState.NAMETAG;
		} else if (isUrlAtStart(b, currentState)) {
			retVal = TweetState.HTTPCHECK;
		} else {
			writeByte(b);
		}

		return retVal;
	}

	private boolean isSpace(byte b) {
		return b == ' ';
	}

	private boolean isHashAtStart(byte b, TweetState currentState) {

		return (currentState == TweetState.OFF) && (b == '#');
	}

	private boolean isNameAtStart(byte b, TweetState currentState) {

		return (currentState == TweetState.OFF) && (b == '@');
	}

	private boolean isUrlAtStart(byte b, TweetState currentState) {

		return (currentState == TweetState.OFF) && (b == 'h');
	}

}