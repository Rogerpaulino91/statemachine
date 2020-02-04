import java.io.IOException;
import java.io.OutputStream;


public abstract class AbstractAction<T extends Enum<?>> {

	/**
	 * Esta é a próxima ação a ser tomada.
	 */
	protected final AbstractAction<T> nextAction;

	/** fluxo de saída*/
	protected final OutputStream os;

	/** buffer de saída */
	protected final byte[] buff = new byte[1];

	public AbstractAction(OutputStream os) {
		this(null, os);
	}

	public AbstractAction(AbstractAction<T> nextAction, OutputStream os) {
		this.os = os;
		this.nextAction = nextAction;
	}

	/**
	 * chamado a próxima ação.
	 * 
	 * @param b
	 *            o byte a processar.
	 * @param state
	 *            o estado atual da máquina.
	 */
	protected void callNext(byte b, T state) throws Exception {

		if (nextAction != null) {
			nextAction.processByte(b, state);
		}
	}

	/**
	 * processe um byte com esta ação.
	 * 
	 * @param b
	 *            o byte a processar.
	 * @param currentState
	 *            O estado atual da máquina de estado
	 * 
	 * @return O próximo estado
	 */
	public abstract T processByte(byte b, T currentState) throws Exception;

	
	public void terminate(T currentState) throws Exception {
		// blank
	}

	protected void writeByte(byte b) throws IOException {
		buff[0] = b; // 
//Escrevendo os dados no diretório de saída
		os.write(buff);
	}

	protected void writeByte(char b) throws IOException {
		writeByte((byte) b);
	}

}