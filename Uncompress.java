import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import statemachine.AbstractAction;
public class Uncompress extends AbstractAction<XMLState> {

	private final ByteArrayOutputStream base64TmpStream;

	public Uncompress(OutputStream os) {
		this(null, os);
	}

	public Uncompress(AbstractAction<XMLState> nextAction, OutputStream os) {
		super(nextAction, os);
		base64TmpStream = new ByteArrayOutputStream();
	}

	@Override
	public XMLState processByte(byte b, XMLState currentState) throws Exception {

		XMLState retVal = currentState;
		switch (b) {
		case '<':
			convertBase64Stream();
			writeByte(b);
			retVal = XMLState.DEFAULT;
			break;
		default:
			collectBase64Byte(b);
			break;
		}
		return retVal;
	}

	private void collectBase64Byte(byte b) throws IOException {
		buff[0] = b;
		base64TmpStream.write(buff);
	}
	private void convertBase64Stream() throws Exception {

		byte[] data = base64TmpStream.toByteArray();

		byte[] decodedBase64 = org.apache.commons.codec.binary.Base64.decodeBase64(data);
		os.write(decodedBase64);
		base64TmpStream.reset();
	}
}