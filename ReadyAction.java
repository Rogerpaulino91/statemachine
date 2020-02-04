import java.io.OutputStream;

import statemachine.AbstractAction;

*/
public class ReadyAction extends AbstractAction<TweetState> {

	public ReadyAction(OutputStream os) {
		super(os);
	}

@Override
	public TweetState processByte(byte b, TweetState currentState) throws Exception {

		TweetState retVal = TweetState.RUNNING;

		switch (b) {

		case '#':
			retVal = TweetState.HASHTAG;
			break;
		case '@':
			retVal = TweetState.NAMETAG;
			break;
		case 'h':
			retVal = TweetState.HTTPCHECK;
			break;
		default:
			super.writeByte(b);
			break;
		}
		return retVal;
	}
}