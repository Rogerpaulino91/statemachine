import java.io.IOException;
import java.io.OutputStream;

import com.captaindebug.statemachine.AbstractAction;

/**
 * essa ação captura as informações da tag inicial - verificando o caractere da tag final

 */
public class CaptureStartTag extends AbstractAction<XMLState> {

	private static final String COMPRESSED_PART = "<CompressedPart";

	private static final int CHECKLENGTH = COMPRESSED_PART.length();

	private final byte[] tagBuffer = new byte[4096];

	private int pos = 1;

	private boolean firstChar = true;

	public CaptureStartTag(OutputStream os) {
		super(os);
	}

	public CaptureStartTag(AbstractAction<XMLState> nextAction, OutputStream os) {
		super(nextAction, os);
	}

	/**
	 *
	 * 
	 * @param b
	 *            
	 * @param currentState
	 *            
	 */
	@Override
	public XMLState processByte(byte b, XMLState currentState) throws Exception {

		XMLState retVal = currentState;

		// estado do computador
		switch (b) {
		case '/':
			if (firstChar) {
				retVal = XMLState.DEFAULT;
				writeByte('<');
				writeByte(b);
			} else {
				tagBuffer[pos++] = b;
			}
			firstChar = false; // redefinir tudo
			break;
		case '>':
			tagBuffer[pos++] = b;
			// processa a tag do buffer
			retVal = processTagBuffer();
			break;
		default:
			// Coloque o byte em um buffer de processamento
			tagBuffer[pos++] = b;
			firstChar = false; // reset once and for all
			break;
		}
		return retVal;
	}
	private XMLState processTagBuffer() throws IOException {

		XMLState retVal = XMLState.DEFAULT;
		if (pos >= CHECKLENGTH) {
			
			String tagName = new String(tagBuffer, 0, CHECKLENGTH);

			if (tagName.equals(COMPRESSED_PART)) {
				
				retVal = XMLState.UNCOMPRESS;
			} else {
				retVal = XMLState.DEFAULT;

				os.write(tagBuffer, 0, pos);
			}
		} else {
			os.write(tagBuffer, 0, pos);
		}

		reset();
		return retVal;
	}
	public void reset() {

		tagBuffer[0] = '<';
		firstChar = true;
		pos = 1;
	}
}